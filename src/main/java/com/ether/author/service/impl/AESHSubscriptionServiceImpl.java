package com.ether.author.service.impl;

import com.ether.author.bean.*;
import com.ether.author.constants.Constants;
import com.ether.author.constants.MessageConstants;
import com.ether.author.error.ApiException;
import com.ether.author.mailsender.EmailBean;
import com.ether.author.mailsender.EmailService;
import com.ether.author.repo.AESHSubscriptionRepository;
import com.ether.author.service.AESHSubscriptionService;
import com.ether.author.service.admin.AdminService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class AESHSubscriptionServiceImpl implements AESHSubscriptionService {

    @Autowired
    private AESHSubscriptionRepository aeshSubscriptionRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AdminService adminService;

    private static final Logger log = LoggerFactory.getLogger(AESHSubscriptionServiceImpl.class);

    @Override
    public List<AESHServicePlan> fetchAllServicePlans() {
        log.info("Fetching All Available Service Plans Details for Author...");
        List<AESHServicePlan> aeshServicePlans = aeshSubscriptionRepository.fetchAllServicePlansDetails();
        if (aeshServicePlans.size() == 0) {
            log.error("No Service Plans Found.");
            throw new ApiException(MessageConstants.NO_PLANS);
        }
        return aeshServicePlans;
    }

    @Override
    public List<OrderDetails> fetchOrderDetailsByAuthorIdAndFileId(long authorId, long fileId) {
        log.info(String.format("Fetching Existing Order Details from DB for authorId: %s and fileId: %s", authorId, fileId));
        List<OrderDetails> orders = aeshSubscriptionRepository.getOrderDetailsByAuthorIdAndFileId(authorId, fileId);
        orders = orders.stream().filter(od -> od.getIsActive() && od.getExpiryDate().after(Timestamp.valueOf(LocalDateTime.now()))).collect(Collectors.toList());
        if (orders.size() == 0) {
            log.warn(String.format(String.format("No order details found for author: %s and fileId: %s", authorId, fileId)));
//            throw new ApiException("No order details found.");
        }
        return orders;
    }

    @Override
    public OrderDetails createOrderDetailsHandler(OrderDetails orderDetails, PersistentAuthorDetails authorDetails) {
        log.info("Handling Order Details...");
        List<OrderDetails> orders = fetchOrderDetailsByAuthorIdAndFileId(authorDetails.getUserId(), orderDetails.getFileId());
        OrderDetails od = orders.stream().findAny().orElse(null);
        if (od != null && Constants.ORDER_STATUS.get(od.getStatus()) < Constants.ORDER_PRIORITY
                && od.getExpiryDate().after(Timestamp.valueOf(LocalDateTime.now().plusDays(1L)))) {
            orderDetails.setOrderDate(od.getOrderDate());
            orderDetails.setWarningDate(od.getWarningDate());
            orderDetails.setExpiryDate(od.getExpiryDate());
        } else if (od != null && Constants.ORDER_STATUS.get(od.getStatus()) >= Constants.ORDER_PRIORITY) {
            log.error(String.format("Order status is: %s. Unable to place Order: %s.", od.getStatus(), od));
            throw new ApiException(String.format(MessageConstants.ORDER_STATUS, od.getStatus()));
        } else {
            log.info("Creating new order for author: " + authorDetails.getUsername());
            orderDetails.setOrderDate(Timestamp.valueOf(LocalDateTime.now()));
            orderDetails.setWarningDate(Timestamp.valueOf(LocalDateTime.now().plusDays(10L)));
            orderDetails.setExpiryDate(Timestamp.valueOf(LocalDateTime.now().plusDays(15L)));
        }
        orderDetails.setModifyDate(Timestamp.valueOf(LocalDateTime.now()));
        orderDetails.setActive(Boolean.TRUE);
        orderDetails.setPaid(Boolean.FALSE);
        if (orderDetails.getCurrency() == null)
            orderDetails.setCurrency("USD");
        orderDetails.setEstimatedAmount(calculateEstimatedAmount(orderDetails.getSelectedServices(), orderDetails.getCurrency()));
        orderDetails.setStatus(Constants.CREATED);

        if (checkAndSendMailForAcademicIllustrationService(orderDetails, authorDetails))
            orderDetails.setStatus(Constants.PENDING);

        log.info("Saving Order Details in DB...");
        Map<String, Object> orderMap = aeshSubscriptionRepository.saveOrUpdateOrderDetails(orderDetails);
        log.info("Order Saved to DB.");
        List<?> orderList = (List<?>) orderMap.get("orderDetails");
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(orderList.stream().findFirst().get());
        OrderDetails order = gson.fromJson(jsonElement, OrderDetails.class);
        if (order == null) {
            log.error("Unable to parse gson to bean.");
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
        return order;
    }

    @Override
    public List<OrderDetails> fetchAllOrdersByAuthorId(long authorId) {
        List<OrderDetails> orders = aeshSubscriptionRepository.getAllOrdersByAuthorId(authorId);
        if (orders.size() == 0) {
            log.warn(String.format("No order Found for authorId: %s", authorId));
            throw new ApiException(MessageConstants.NO_ORDER);
        }
        return orders;
    }

    @Override
    public OrderDetails fetchOrdersByOrderId(long orderId) {
        List<OrderDetails> orders = aeshSubscriptionRepository.getAllOrdersByOrderId(orderId);
        if (orders.size() == 0) {
            throw new ApiException(MessageConstants.NO_ORDER_BY_ID + orderId);
        }
        return orders.stream().findFirst().get();
    }

    @Override
    public List<OrderDetails> fetchOrderDetailsByFileIdAndReportId(long fileId, long reportId) {
        List<OrderDetails> orders = aeshSubscriptionRepository.getOrderDetailsByFileIdAndReportId(fileId, reportId);
        if (orders.size() == 0) {
            throw new ApiException(String.format(MessageConstants.NO_ORDER_BY_FILE_AND_REPORT, fileId, reportId));
        }
        return orders;
    }

    @Override
    public List<OrderDetails> fetchAllOrdersByAdminId(long adminId) {
        List<OrderDetails> orders = aeshSubscriptionRepository.getAllOrdersByAdminId(adminId);
        if (orders.size() == 0) {
            throw new ApiException(MessageConstants.NO_ORDER_BY_ADMIN_ID + adminId);
        }
        return orders;
    }

    @Override
    public List<OrderDetails> fetchAllOrdersByReportId(long reportId) {
        List<OrderDetails> orders = aeshSubscriptionRepository.getAllOrdersByReportId(reportId);
        if (orders.size() == 0) {
            throw new ApiException(MessageConstants.NO_ORDER_BY_REPORT_ID + reportId);
        }
        return orders;
    }

    @Override
    public List<OrderDetails> fetchAllUnPaidOrdersByAuthorId(long authorId) {
        List<OrderDetails> orders = aeshSubscriptionRepository.getAllUnPaidOrdersByAuthorId(authorId);
        if (orders.size() == 0) {
            log.warn(MessageConstants.NO_ORDER_BY_AUTHOR_ID + authorId);
            throw new ApiException(MessageConstants.NO_ORDER_BY_AUTHOR_ID);
        }
        return orders;
    }

    @Override
    public List<OrderDetails> fetchAllPaidOrdersByAuthorId(long authorId) {
        List<OrderDetails> orders = aeshSubscriptionRepository.getAllPaidOrdersByAuthorId(authorId);
        if (orders.size() == 0) {
            log.warn(MessageConstants.NO_ORDER_BY_AUTHOR_ID + authorId);
            throw new ApiException(MessageConstants.NO_ORDER_BY_AUTHOR_ID);
        }
        return orders;
    }

    @Override
    public List<OrderDetails> fetchAllUnPaidOrders() {
        List<OrderDetails> orders = aeshSubscriptionRepository.getAllUnPaidOrders();
        if (orders.size() == 0) {
            throw new ApiException(MessageConstants.NO_ORDER);
        }
        return orders;
    }

    @Override
    public List<OrderDetails> fetchAllPaidOrders() {
        List<OrderDetails> orders = aeshSubscriptionRepository.getAllPaidOrders();
        if (orders.size() == 0) {
            throw new ApiException(MessageConstants.NO_ORDER);
        }
        return orders;
    }

    @Override
    public List<TransactionDetail> getTransactionDetailById(long id) {
        log.info("fetching transaction details by id: " + id);
        List<TransactionDetail> transactionDetailsById = aeshSubscriptionRepository.getTransactionDetailsById(id);
        if (transactionDetailsById.size() == 0) {
            log.error("No transaction details found.");
            throw new ApiException(MessageConstants.NO_TRANSACTION);
        }
        return transactionDetailsById;
    }

    @Override
    public TransactionDetail transactionDetailsHandler(TransactionDetail transactionDetail, PersistentAuthorDetails authorDetails) throws IOException {
        log.info(String.format("Preparing to check transaction details for author: [%s]", authorDetails.getUsername()));
        if (checkTransactionDetails(transactionDetail, authorDetails))
            transactionDetail.setPaymentStatus(Constants.PAID);
        log.info("Checking content type...");
        checkContentType(transactionDetail.getScreenshot().getContentType());
        log.info("Uploading Screenshot at server...");
        String fileName = Objects.requireNonNull(transactionDetail.getScreenshot().getOriginalFilename()).trim();
        Path path = Paths.get(AESHProperties.UPLOAD_DIR + authorDetails.getUserId() + File.separator + Constants.TRANSACTION_DIR
                + File.separator + fileName.substring(fileName.indexOf(".")) + File.separator + fileName);
        log.info(String.format("Path: %s", path));
        File contentSaveDir = new File(String.valueOf(path));
        if (!contentSaveDir.exists()) {
            log.info("Creating Directories...");
            boolean mkdirs = contentSaveDir.mkdirs();
            if (!mkdirs) log.error("Cannot Create Directories. Please Check.");
        }
        transactionDetail.setScreenshotPath(path.toString());
        Files.copy(transactionDetail.getScreenshot().getInputStream(),
                path,
                StandardCopyOption.REPLACE_EXISTING);
        transactionDetail.setModifyDate(Timestamp.valueOf(LocalDateTime.now()));
        if (transactionDetail.getCurrency() == null)
            transactionDetail.setCurrency("USD");
        log.info("Saving Transaction Details to DB...");
        Map<String, Object> transactionMap = aeshSubscriptionRepository.saveOrUpdateTransactionDetails(transactionDetail);
        log.info("Transaction Details Saved to DB.");
        updateOrderDetailsAfterPayment(transactionDetail.getOrderId());
        List<?> transaction = (List<?>) transactionMap.get("transactionDetails");
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(transaction.stream().findFirst().get());
        transactionDetail = gson.fromJson(jsonElement, TransactionDetail.class);
        if (transactionDetail == null) {
            log.info("Unable to fetch or update transaction details in DB");
            throw new ApiException(MessageConstants.TRANSACTION_UPDATE);
        }
        sendMailForTransactionDetails(transactionDetail, authorDetails);
        return transactionDetail;
    }

    private void checkContentType(String contentType) {
        if (!Constants.IMAGE_TYPE.contains(Objects.requireNonNull(contentType))) {
            log.info(contentType + " is not valid");
            throw new ApiException(MessageConstants.IMAGE_UPLOAD);
        }
    }

    private boolean sendMailForTransactionDetails(TransactionDetail transactionDetail, PersistentAuthorDetails authorDetails) {
        log.info("Preparing to send an email to the Admin...");
        Boolean status = Boolean.FALSE;
        OrderDetails order = fetchOrdersByOrderId(transactionDetail.getOrderId());
        EmailBean emailBean = new EmailBean();
        String subject = "[PAYMENT ALERT]: " + authorDetails.getFirstName() + " " + authorDetails.getLastName();
        String message = "Dear Admin,\n\n" + authorDetails.getFirstName() + " " + authorDetails.getLastName() + " has done its PAYMENT for " + order.getSelectedServices() + " and expecting an Order Approval from you." +
                "The Estimated Amount for Selected-Services was: " + order.getEstimatedAmount() +
                "The Actual Amount Paid for Selected-Services is: " + transactionDetail.getAmount() +
                "Author Email-Address: " + authorDetails.getEmail() +
                "\nPFB the complete Order-Transaction-Details:\n[ORDER-DETAILS]" + order + "\n\n[TRANSACTION-DETAILS]" + transactionDetail +
                "\nPlease APPROVE above ORDER-TRANSACTION asap.\n" +
                "\n\nReagrds,\nAESH Support Team";
        emailBean.setFrom(AESHProperties.SUPPORT_EMAIL);
        //emailBean.setTo(AESHProperties.ADMIN_EMAIL);
        emailBean.setTo("zafaralways@gmail.com");
        emailBean.setSubject(subject);
        emailBean.setMessage(message);
        emailService.send(emailBean);
        status = Boolean.TRUE;
        return status;
    }

    private void updateOrderDetailsAfterPayment(Long orderId) {
        log.info("Updating Order Status...");
        OrderDetails order = fetchOrdersByOrderId(orderId);
        order.setWarningDate(Timestamp.valueOf(LocalDateTime.now().plusDays(25L)));
        order.setExpiryDate(Timestamp.valueOf(LocalDateTime.now().plusDays(30L)));
        order.setModifyDate(Timestamp.valueOf(LocalDateTime.now()));
        order.setStatus(Constants.PAID);
        order.setPaid(Boolean.TRUE);

        aeshSubscriptionRepository.updateOrderStatus(order);
        log.info("Status Updated");
    }

    @Override
    public TransactionDetail updatePaymentStatusHandler(TransactionDetail transactionDetail) {
        int i = aeshSubscriptionRepository.updatePaymentStatus(transactionDetail);
        TransactionDetail transaction = aeshSubscriptionRepository.getTransactionDetailsById(transactionDetail.getBillingId()).stream().findAny().orElse(null);
        if (transaction == null) {
            log.error("No Transaction Details found after update.");
            throw new ApiException(MessageConstants.NO_TRANSACTION);
        }
        sendPaymentConfirmationMailForAuthor(transaction);
        return transaction;
    }

    @Override
    public List<OrderDetails> getPendingOrderByAuthorId(long authorId) {
        log.info("fetching pending order for authorId: " + authorId);
        List<OrderDetails> pendingOrderByAuthorId = aeshSubscriptionRepository.getPendingOrderByAuthorId(authorId);
        if (pendingOrderByAuthorId.size() == 0) {
            log.warn("No Pending order found for authorId: " + authorId);
            throw new ApiException(MessageConstants.NO_PENDING_ORDER);
        }
        return pendingOrderByAuthorId;
    }

    @Override
    public List<OrderDetails> getAllPendingOrders() {
        log.info("Fetching all pending orders");
        List<OrderDetails> allPendingOrders = aeshSubscriptionRepository.getAllPendingOrders();
        if (allPendingOrders.size() == 0) {
            log.warn("No Pending Order Found.");
            throw new ApiException(MessageConstants.NO_PENDING_ORDER);
        }
        return allPendingOrders;
    }

    @Override
    public List<OrderDetails> getPendingOrderByAuthorIdAndFileId(long authorId, long fileId) {
        log.info(String.format("fetching pending order by fileId: %s and authordId: %s"));
        List<OrderDetails> pendingOrderByAuthorIdAndFileId = aeshSubscriptionRepository.getPendingOrderByAuthorIdAndFileId(authorId, fileId);
        if (pendingOrderByAuthorIdAndFileId.size() == 0) {
            log.warn(String.format("No Pending Order found for authorId %s and fileId %s", authorId, fileId));
            throw new ApiException(MessageConstants.NO_PENDING_ORDER);
        }
        return pendingOrderByAuthorIdAndFileId;
    }

    @Override
    public String qrCodeHelper(long id, String qrCodeName, MultipartFile qrCode) throws IOException {
        log.info("Uploading QR Code to server for: " + qrCodeName);
        checkContentType(qrCode.getContentType());
        try {
            String fileName = Objects.requireNonNull(qrCode.getOriginalFilename()).trim().replaceAll(" ","_");
            Path qrPath = Paths.get(Constants.BASE_PATH + File.separator + "images" + File.separator + Constants.QRCODE_DIR + File.separator
                    + qrCodeName);
            if (new File(String.valueOf(qrPath)).exists() && Files.deleteIfExists(Objects.requireNonNull(Files.list(qrPath).findAny().orElse(null)))) {
                log.warn(String.format("Old QR-Code deleted from: %s", qrPath));
            }
            Path path = Paths.get(Constants.BASE_PATH + File.separator + "images" + File.separator + Constants.QRCODE_DIR + File.separator
                    + qrCodeName + File.separator + fileName);
            log.info(String.format("Path: %s", path));
            File contentSaveDir = new File(String.valueOf(path));
            if (!contentSaveDir.exists()) {
                log.info("Creating Directories...");
                boolean mkdirs = contentSaveDir.mkdirs();
                if (!mkdirs) log.error("Cannot Create Directories. Please Check.");
            }
            Files.copy(qrCode.getInputStream(),
                    path,
                    StandardCopyOption.REPLACE_EXISTING);
            return path.toString();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.UNABLE_TO_SAVE_FILE);
        }
    }

    @Override
    public void uploadQR(String qrCodeName, String path) {
        log.info("Uploading QR Code to DB...");
        aeshSubscriptionRepository.uploadQRCodeToDB(qrCodeName, path);
    }

    @Override
    public void updateQR(String qrCodeName, String path) {
        log.info("Updating QR Code...");
        aeshSubscriptionRepository.updateQRCode(qrCodeName, path);
        log.info("QR Code updated successfully.");
    }

    public Double calculateEstimatedAmount(String services, String currency) {
        List<AESHServicePlan> aeshServicePlans = fetchAllServicePlans();
        AtomicReference<Double> estimatedAmount = new AtomicReference<>(0.0);
        Arrays.stream(services.split(",")).forEach(
                ss -> {
                    aeshServicePlans.stream().filter(asp -> asp.getName().equals(ss)).forEach(
                            asp -> estimatedAmount.set(asp.getPrice() + estimatedAmount.get())
                    );
                }
        );
        return estimatedAmount.get();
    }

    private boolean checkAndSendMailForAcademicIllustrationService(OrderDetails orderDetails, PersistentAuthorDetails authorDetails) {
        if (orderDetails.getSelectedServices().contains("Academic Illustration")) {
            log.info("Author selected Academic Illustration Service");
            log.info("Preparing to send an email to the Admin...");
            EmailBean emailBean = new EmailBean();
            String subject = "[ORDER ALERT]: " + authorDetails.getFirstName() + " " + authorDetails.getLastName();
            String message = "Dear Admin,\n\n" + authorDetails.getFirstName() + " " + authorDetails.getLastName() + " has opted for " + orderDetails.getSelectedServices() + " and expecting an Order Quote from you." +
                    "The estimated amount for the selected services is: " + orderDetails.getEstimatedAmount() +
                    "\nPFB the complete Order-Details:\n" + orderDetails +
                    "\nPlease reply to the below email address with required details asap.\n" + authorDetails.getEmail() +
                    "\n\nReagrds,\nAESH Support Team";
            emailBean.setFrom(AESHProperties.SUPPORT_EMAIL);
            //emailBean.setTo(AESHProperties.ADMIN_EMAIL);
            emailBean.setTo("zafaralways@gmail.com");
            emailBean.setSubject(subject);
            emailBean.setMessage(message);
            emailService.send(emailBean);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private boolean checkTransactionDetails(TransactionDetail transactionDetail, PersistentAuthorDetails authorDetails) {
        Boolean status = Boolean.TRUE;
        log.info("Fetching Order Details from DB...");
        OrderDetails order = fetchOrdersByOrderId(transactionDetail.getOrderId());
        log.info(String.format("Order Found: [%s]", order));
        log.info("Performing Checks...");
        //check for order expiry date
        if (order.getExpiryDate().before(Timestamp.valueOf(LocalDateTime.now())) || order.getExpiryDate().equals(Timestamp.valueOf(LocalDateTime.now()))) {
            log.error(String.format("Check Failed: [Order Expired]: [%s]", order.getExpiryDate()));
            throw new ApiException(MessageConstants.ORDER_EXPIRED);
        }
        //check if the order amount is already paid
        else if (Constants.PAID.equalsIgnoreCase(order.getStatus())) {
            log.error(String.format("Check Failed: [Order %s]", order.getStatus()));
            throw new ApiException(String.format(MessageConstants.ORDER_STATUS, order.getStatus()));
        }
        //check if the order is already approved, rejected, expired or cancelled
        else if (Constants.ORDER_STATUS.get(order.getStatus()) > Constants.ORDER_PRIORITY) {
            log.error(String.format("Check Failed: [Order %s]", order.getStatus()));
            throw new ApiException(String.format(MessageConstants.ORDER_STATUS, order.getStatus()));
        }
        //check for actual amount payment
        else if (!Objects.equals(order.getEstimatedAmount(), transactionDetail.getAmount())) {
            log.error(String.format("Check Failed: [Less Amount]: [Actual Amount: %s]-[Expected Amount: %s]", transactionDetail.getAmount(), order.getEstimatedAmount()));
            throw new ApiException(String.format(MessageConstants.ORDER_AMOUNT, order.getEstimatedAmount(), order.getCurrency()));
        } else if (LocalDate.parse(transactionDetail.getPaymentDate()).isAfter(LocalDate.now())) {
            log.error("Payment Date cannot be in future.");
            throw new ApiException(MessageConstants.PAYMENT_DATE);
        }
        return status;
    }

    private boolean sendPaymentConfirmationMailForAuthor(TransactionDetail transaction) {
        PersistentAuthorDetails authorDetails = adminService.getAuthorDetailsById(transaction.getUserBillingId());
        log.info("Sending Confirmation Email to Author...");
        log.info("Preparing to send an email to the Admin...");
        EmailBean emailBean = new EmailBean();
        String subject = "[Payment Confirm]: " + authorDetails.getFirstName() + " " + authorDetails.getLastName();
        String message = "Dear " + authorDetails.getFirstName() + " " + authorDetails.getLastName() +
                "\n\nWe are pleased to confirm you that your Payment of " + transaction.getAmount() + " " + transaction.getCurrency() + " has been approved successfully." +
                "\n\nOur team is working on your Final Report and will be delivered to you soon." +
                "\n\nWe Congratulate your for your successful order." +
                "\n\nPlease reach us at " + AESHProperties.SUPPORT_EMAIL + " for any query." +
                "\n\nCheers,\nAESH Support Team\n\nBrings the moon to your Academic Work";
        emailBean.setFrom(AESHProperties.SUPPORT_EMAIL);
        //emailBean.setTo(AESHProperties.ADMIN_EMAIL);
        emailBean.setTo(authorDetails.getEmail());
        emailBean.setSubject(subject);
        emailBean.setMessage(message);
        emailService.send(emailBean);
        return Boolean.TRUE;
    }

    @Override
    public OrderDetails updateOrderHandler(OrderDetails orderDetails) {
        log.info(String.format("Updating OrderDetails [%s]...", orderDetails));
        if (orderDetails != null && Constants.ORDER_STATUS.get(orderDetails.getStatus()) >= (Constants.ORDER_PRIORITY-1)
                && orderDetails.getExpiryDate().after(Timestamp.valueOf(LocalDateTime.now().plusDays(1L)))) {
            int i = aeshSubscriptionRepository.updateOrder(orderDetails);
            log.info(String.format("Order Updated [%s]", i));
            if (i >0) {
                return fetchOrdersByOrderId(orderDetails.getOrderId());
            } else
                throw new ApiException(MessageConstants.UPDATE_ORDER_ERR);
        } else
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
    }

    @Override
    public String fetchQrCode(String qrCodeType) {
        log.info(String.format("Fetching %s QR-Code", qrCodeType));
        String path = aeshSubscriptionRepository.fetchQrCodeByName(qrCodeType);
        if (path == null) {
            log.error(String.format("No %s code present in db", qrCodeType));
            throw new ApiException(MessageConstants.NO_QR_CODE_FOUND);
        }
        return path;
    }
}
