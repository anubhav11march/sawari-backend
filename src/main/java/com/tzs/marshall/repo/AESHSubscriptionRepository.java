package com.tzs.marshall.repo;

import com.tzs.marshall.bean.AESHServicePlan;
import com.tzs.marshall.bean.OrderDetails;
import com.tzs.marshall.bean.TransactionDetail;

import java.util.List;
import java.util.Map;

public interface AESHSubscriptionRepository {

    List<AESHServicePlan> fetchAllServicePlansDetails();

    Map<String, Object> saveOrUpdateOrderDetails(OrderDetails orderDetails);

    List<OrderDetails> getOrderDetailsByAuthorIdAndFileId(long authorId, long fileId);

    List<OrderDetails> getAllOrdersByOrderId(long orderId);

    List<OrderDetails> getAllOrdersByAuthorId(long authorId);

    //for admin
    List<OrderDetails> getOrderDetailsByFileIdAndReportId(long fileId, long reportId);

    List<OrderDetails> getAllOrdersByAdminId(long adminId);

    List<OrderDetails> getAllOrdersByReportId(long reportId);

    List<OrderDetails> getAllUnPaidOrdersByAuthorId(long authorId);

    List<OrderDetails> getAllPaidOrdersByAuthorId(long authorId);

    List<OrderDetails> getAllUnPaidOrders();

    List<OrderDetails> getAllPaidOrders();

    Map<String, Object> saveOrUpdateTransactionDetails(TransactionDetail transactionDetail);

    int updatePaymentStatus (TransactionDetail transactionDetail);

    List<TransactionDetail> getTransactionDetailsById (long id);

    int updateOrderStatus(OrderDetails order);

    List<OrderDetails> getPendingOrderByAuthorId(long authorId);
    List<OrderDetails> getAllPendingOrders();
    List<OrderDetails> getPendingOrderByAuthorIdAndFileId(long authorId, long fileId);

    void uploadQRCodeToDB(String qrCodeName, String qrCodePath);

    void updateQRCode(String qrCodeName, String path);

    int updateOrder(OrderDetails orderDetails);

    String fetchQrCodeByName(String qrCodeType);
}