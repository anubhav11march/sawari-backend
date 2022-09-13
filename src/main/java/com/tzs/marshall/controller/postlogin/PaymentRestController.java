package com.tzs.marshall.controller.postlogin;

import com.tzs.marshall.InitProperties;
import com.tzs.marshall.bean.DBProperties;
import com.tzs.marshall.bean.OrderDetails;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.TransactionDetail;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.service.AESHSubscriptionService;
import com.tzs.marshall.service.admin.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = {"/author", "/admin", "/editor"})
public class PaymentRestController {

    @Autowired
    private AESHSubscriptionService aeshSubscriptionService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private InitProperties initProperties;

    String errorMessage = "";
    String successMessage = "";
    private static Logger log = LoggerFactory.getLogger(PaymentRestController.class);

    /*
        There would only one order be created for a File uploaded by author, if there is any change in the order respected to the file then the existed order will modify.
        A new order will only be created when the author select a new file.
    */
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public OrderDetails createCheckout(@RequestBody OrderDetails orderDetails,
                                       @AuthenticationPrincipal PersistentUserDetails authorDetails,
                                       HttpSession session) {
        if (Constants.ADMINS.contains(authorDetails.getRoleName())) {
            log.error("Admin can not place an order.");
            throw new ApiException(MessageConstants.ADMIN_ORDER);
        }
        session.removeAttribute("order");
        OrderDetails order = new OrderDetails();
        try {
            orderDetails.setAuthorId(authorDetails.getUserId());
            orderDetails.setStatus(Constants.INITIATED);
            order = aeshSubscriptionService.createOrderDetailsHandler(orderDetails, authorDetails);
            successMessage = MessageConstants.ORDER_CREATED;
            log.info(String.format("Order created successfully: Order for %s with order-details as [%s]", authorDetails.getUsername(), order));
        } catch (ApiException e) {
            errorMessage = MessageConstants.ORDER_CREATED_ERR;
            log.error(String.format("Error creating Order for %s with order-details as [%s]", authorDetails.getUsername(), orderDetails));
        }
        //set message
        session.setAttribute("errorMessage", errorMessage);
        session.setAttribute("successMessage", successMessage);
        session.setAttribute("orderId", order.getOrderId());
        return order;
    }


    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public ModelAndView checkoutOrder(@RequestParam("screenshot") MultipartFile file,
                                      @ModelAttribute TransactionDetail transactionDetail, @AuthenticationPrincipal PersistentUserDetails authorDetails,
                                      HttpSession session) {
        if (Constants.ADMINS.contains(authorDetails.getRoleName()))
            throw new ApiException(MessageConstants.ADMIN_ORDER_ERR);
        log.info("Preparing Transaction Details...");
        try {
            if (file == null)
                throw new ApiException(MessageConstants.SCREENSHOT_ERR);
            transactionDetail.setScreenshot(file);
            log.info("Payment Status is set to [INITIATED]");
            transactionDetail.setPaymentStatus("INITIATED");
            transactionDetail.setUserBillingId(authorDetails.getUserId());
            transactionDetail = aeshSubscriptionService.transactionDetailsHandler(transactionDetail, authorDetails);
            successMessage = MessageConstants.PAYMENT_SUCCESS;
        } catch (ApiException | IOException e) {
            log.error(e.getLocalizedMessage() + e.getCause());
            transactionDetail.setScreenshot(null);
            errorMessage = String.format(MessageConstants.PAYMENT_ERR + "\n[%s]", e.getLocalizedMessage());
        }
        //set message
        session.setAttribute("errorMessage", errorMessage);
        session.setAttribute("successMessage", successMessage);
        session.setAttribute("transactionDetail", transactionDetail);
        //redirect
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/" + authorDetails.getRoleName().toLowerCase() + "/payment");
        return modelAndView;
    }

    @RequestMapping(value = "/payment/update", method = RequestMethod.POST)
    public ModelAndView updateOrderPayment(@ModelAttribute TransactionDetail transactionDetail, @AuthenticationPrincipal PersistentUserDetails authorDetails,
                                           HttpSession session) {
        if (Constants.USER.equals(authorDetails.getRoleName()))
            throw new ApiException(MessageConstants.AUTHOR_STATUS_ERR);
        log.info("Updating Payment Status...");
        try {
            transactionDetail = aeshSubscriptionService.updatePaymentStatusHandler(transactionDetail);
            successMessage = MessageConstants.PAYMENT_UPDATED;
        } catch (ApiException e) {
            log.error(e.getLocalizedMessage() + e.getCause());
            errorMessage = e.getMessage();
        }
        //set message
        session.setAttribute("errorMessage", errorMessage);
        session.setAttribute("successMessage", successMessage);
        session.setAttribute("transactionDetail", transactionDetail);
        //redirect
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/" + authorDetails.getRoleName().toLowerCase() + "/payment");
        return modelAndView;
    }

    @RequestMapping(value = "/file-order", method = RequestMethod.GET)
    public OrderDetails getFileOrder(@RequestParam("fileId") String fileId,
                                     @AuthenticationPrincipal PersistentUserDetails authorDetails) {
        return aeshSubscriptionService.fetchOrderDetailsByAuthorIdAndFileId(authorDetails.getUserId(),
                Long.parseLong(fileId)).stream().findFirst().get();
    }

    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
    public OrderDetails getOrder(@PathVariable String orderId) {
        return aeshSubscriptionService.fetchOrdersByOrderId(Long.parseLong(orderId));
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public List<OrderDetails> getPaidOrders(@AuthenticationPrincipal PersistentUserDetails authorDetails) {
        return aeshSubscriptionService.fetchAllPaidOrdersByAuthorId(authorDetails.getUserId());
    }

    @RequestMapping(value = "/transaction-detail", method = RequestMethod.GET)
    public List<TransactionDetail> getTransactionDetail(@RequestParam("id") String id) {
        return aeshSubscriptionService.getTransactionDetailById(Long.parseLong(id));
    }

    @RequestMapping(value = "/paid-orders", method = RequestMethod.GET)
    public List<OrderDetails> getAllPaidOrders() {
        return aeshSubscriptionService.fetchAllPaidOrders();
    }

    @RequestMapping(value = "/pending-orders", method = RequestMethod.GET)
    public List<OrderDetails> getAllPendingOrders() {
        return aeshSubscriptionService.getAllPendingOrders();
    }

    @RequestMapping(value = "/qrcode/upload", method = RequestMethod.POST)
    public ModelAndView uploadQRCode(@RequestParam("name") String qrCodeName, @RequestParam("qrCode") MultipartFile qrCode,
                                     @AuthenticationPrincipal PersistentUserDetails authorDetails, HttpSession session) {
        if (Constants.USER.equals(authorDetails.getRoleName()))
            throw new ApiException(MessageConstants.NOT_AUTHORIZED);
        adminService.checkAuthorizedAdmin(authorDetails.getUserId());
        try {
            String path = aeshSubscriptionService.qrCodeHelper(authorDetails.getUserId(), qrCodeName, qrCode);
            aeshSubscriptionService.uploadQR(qrCodeName, path);
            successMessage = MessageConstants.QR_UPLOADED;
        } catch (ApiException | IOException e) {
            log.error(e.getLocalizedMessage() + e.getCause());
            errorMessage = e.getMessage();
        }
        //set message
        session.setAttribute("errorMessage", errorMessage);
        session.setAttribute("successMessage", successMessage);
        ModelAndView modelAndView = new ModelAndView();
        new DBProperties(initProperties.getDBProperties());
        modelAndView.setViewName("redirect:/" + authorDetails.getRoleName().toLowerCase() + "/updateqrcode");
        return modelAndView;
    }

    @RequestMapping(value = "/qrcode/update", method = RequestMethod.POST)
    public ModelAndView updateQRCode(@RequestParam("name") String qrCodeName, @RequestParam("qrCode") MultipartFile qrCode,
                                     @AuthenticationPrincipal PersistentUserDetails authorDetails, HttpSession session) {
        if (Constants.USER.equals(authorDetails.getRoleName()))
            throw new ApiException(MessageConstants.NOT_AUTHORIZED);
        adminService.checkAuthorizedAdmin(authorDetails.getUserId());
        try {
            String path = aeshSubscriptionService.qrCodeHelper(authorDetails.getUserId(), qrCodeName, qrCode);
            aeshSubscriptionService.updateQR(qrCodeName, path);
            successMessage = MessageConstants.QR_UPDATED;
        } catch (ApiException | IOException e) {
            log.error(e.getLocalizedMessage());
            errorMessage = e.getMessage();
        }
        //set message
        session.setAttribute("errorMessage", errorMessage);
        session.setAttribute("successMessage", successMessage);
        ModelAndView modelAndView = new ModelAndView();
        new DBProperties(initProperties.getDBProperties());
        modelAndView.setViewName("redirect:/" + authorDetails.getRoleName().toLowerCase() + "/updateqrcode");
        return modelAndView;
    }

    @RequestMapping(value = "/order", method = RequestMethod.PUT)
    public OrderDetails updateOrder(@RequestBody OrderDetails orderDetails, HttpSession session) {
        orderDetails = aeshSubscriptionService.updateOrderHandler(orderDetails);
        return orderDetails;
    }
}
