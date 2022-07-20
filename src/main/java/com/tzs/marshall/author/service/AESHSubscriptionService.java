package com.tzs.marshall.author.service;

import com.tzs.marshall.author.bean.AESHServicePlan;
import com.tzs.marshall.author.bean.OrderDetails;
import com.tzs.marshall.author.bean.PersistentAuthorDetails;
import com.tzs.marshall.author.bean.TransactionDetail;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AESHSubscriptionService {

    List<AESHServicePlan> fetchAllServicePlans();

    List<OrderDetails> fetchOrderDetailsByAuthorIdAndFileId(long authorId, long fileId);

    OrderDetails createOrderDetailsHandler(OrderDetails orderDetails, PersistentAuthorDetails authorDetails);

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

    List<TransactionDetail> getTransactionDetailById(long id);

    TransactionDetail transactionDetailsHandler(TransactionDetail transactionDetail, PersistentAuthorDetails authorDetails) throws IOException;

    TransactionDetail updatePaymentStatusHandler (TransactionDetail transactionDetail);

    List<OrderDetails> getPendingOrderByAuthorId(long authorId);
    List<OrderDetails> getAllPendingOrders();
    List<OrderDetails> getPendingOrderByAuthorIdAndFileId(long authorId, long fileId);

    String qrCodeHelper(long id, String qrCodeName, MultipartFile qrCode) throws IOException;

    void uploadQR(String qrCodeName, String path);

    void updateQR(String qrCodeName, String path);

    OrderDetails updateOrderHandler(OrderDetails orderDetails);

    String fetchQrCode(String qrCodeType);
}
