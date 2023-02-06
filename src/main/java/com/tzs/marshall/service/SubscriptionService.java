package com.tzs.marshall.service;

import com.tzs.marshall.bean.AESHServicePlan;
import com.tzs.marshall.bean.OrderDetails;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.TransactionDetail;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SubscriptionService {

    List<AESHServicePlan> fetchAllServicePlans();

    List<OrderDetails> fetchOrderDetailsByAuthorIdAndFileId(long authorId, long fileId);

    OrderDetails createOrderDetailsHandler(OrderDetails orderDetails, PersistentUserDetails authorDetails);

    List<OrderDetails> fetchAllOrdersByAuthorId(long authorId);

    OrderDetails fetchOrdersByOrderId(long orderId);
    //for admin
    List<OrderDetails> fetchOrderDetailsByFileIdAndReportId(long fileId, long reportId);

    List<OrderDetails> fetchAllOrdersByAdminId(long adminId);

    List<OrderDetails> fetchAllOrdersByReportId(long reportId);

    List<OrderDetails> fetchAllUnPaidOrdersByAuthorId(long authorId);

    List<OrderDetails> fetchAllPaidOrdersByAuthorId(long authorId);

    List<OrderDetails> fetchAllUnPaidOrders();

    List<OrderDetails> fetchAllPaidOrders();

    TransactionDetail getTransactionDetailByOrderId(long id);

    TransactionDetail transactionDetailsHandler(TransactionDetail transactionDetail, PersistentUserDetails authorDetails) throws IOException;

    TransactionDetail updatePaymentStatusHandler (TransactionDetail transactionDetail);

    List<OrderDetails> getPendingOrderByAuthorId(long authorId);
    List<OrderDetails> getAllPendingOrders();
    List<OrderDetails> getPendingOrderByAuthorIdAndFileId(long authorId, long fileId);

    String qrCodeHelper(long id, String qrCodeName, MultipartFile qrCode);

    void uploadQR(String qrCodeName, String path);

    void updateQR(String qrCodeName, String path);

    OrderDetails updateOrderHandler(OrderDetails orderDetails);

    String fetchQrCode(String qrCodeType);
}
