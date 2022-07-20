package com.tzs.marshall.author.repo.impl;

import com.tzs.marshall.author.bean.AESHServicePlan;
import com.tzs.marshall.author.bean.OrderDetails;
import com.tzs.marshall.author.bean.TransactionDetail;
import com.tzs.marshall.author.constants.Constants;
import com.tzs.marshall.author.constants.MessageConstants;
import com.tzs.marshall.author.error.ApiException;
import com.tzs.marshall.author.repo.AESHSubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class AESHSubscriptionRepositoryImpl implements AESHSubscriptionRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(AESHSubscriptionRepositoryImpl.class);

    public AESHSubscriptionRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AESHServicePlan> fetchAllServicePlansDetails() {
        try {
            String query = "SELECT plan_id, name, description, price, currency, validity FROM ether_service.service_plans";
            return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(AESHServicePlan.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public Map<String, Object> saveOrUpdateOrderDetails(OrderDetails orderDetails) {
        try {
            if (Constants.CREATED.equalsIgnoreCase(orderDetails.getStatus()))
                orderDetails.setStatus(Constants.PLACED);
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource
                    .addValue("authorId", orderDetails.getAuthorId())
                    .addValue("fileId", orderDetails.getFileId())
                    .addValue("adminId", orderDetails.getAdminId())
                    .addValue("reportId", orderDetails.getReportId())
                    .addValue("selectedServices", orderDetails.getSelectedServices())
                    .addValue("estimatedAmount", orderDetails.getEstimatedAmount())
                    .addValue("currency", orderDetails.getCurrency())
                    .addValue("orderDate", orderDetails.getOrderDate())
                    .addValue("expiryDate", orderDetails.getExpiryDate())
                    .addValue("warningDate", orderDetails.getWarningDate())
                    .addValue("modifyDate", orderDetails.getModifyDate())
                    .addValue("remark", orderDetails.getRemark())
                    .addValue("status", orderDetails.getStatus())
                    .addValue("isActive", orderDetails.getIsActive())
                    .addValue("isPaid", orderDetails.getIsPaid());
            return new SimpleJdbcCall(jdbcTemplate.getJdbcTemplate().getDataSource())
                    .withSchemaName("ether_service")
                    .withProcedureName("spInsertOrUpdateOrderDetails")
                    .returningResultSet("orderDetails", BeanPropertyRowMapper.newInstance(OrderDetails.class))
                    .execute(mapSqlParameterSource);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<OrderDetails> getOrderDetailsByAuthorIdAndFileId(long authorId, long fileId) {
        try {
            String query = "SELECT * FROM ether_service.order_details WHERE author_id=:authorId AND file_id=:fileId ORDER BY modify_date";
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("authorId", authorId).addValue("fileId", fileId),
                    BeanPropertyRowMapper.newInstance(OrderDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<OrderDetails> getAllOrdersByOrderId(long orderId) {
        try {
            String query = "SELECT * FROM ether_service.order_details WHERE order_id=:orderId ORDER BY modify_date";
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("orderId", orderId),
                    BeanPropertyRowMapper.newInstance(OrderDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<OrderDetails> getAllOrdersByAuthorId(long authorId) {
        try {
            String query = "SELECT * FROM ether_service.order_details WHERE author_id=:authorId ORDER BY modify_date";
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("authorId", authorId),
                    BeanPropertyRowMapper.newInstance(OrderDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<OrderDetails> getOrderDetailsByFileIdAndReportId(long fileId, long reportId) {
        try {
            String query = "SELECT * FROM ether_service.order_details WHERE file_id=:fileId AND report_id=:reportId ORDER BY modify_date";
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("fileId", fileId).addValue("reportId", reportId),
                    BeanPropertyRowMapper.newInstance(OrderDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<OrderDetails> getAllOrdersByAdminId(long adminId) {
        try {
            String query = "SELECT * FROM ether_service.order_details WHERE admin_id=:adminId ORDER BY modify_date";
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("adminId", adminId),
                    BeanPropertyRowMapper.newInstance(OrderDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<OrderDetails> getAllOrdersByReportId(long reportId) {
        try {
            String query = "SELECT * FROM ether_service.order_details WHERE report_id=:reportId ORDER BY modify_date";
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("reportId", reportId),
                    BeanPropertyRowMapper.newInstance(OrderDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<OrderDetails> getAllUnPaidOrdersByAuthorId(long authorId) {
        try {
            String query = "SELECT * FROM ether_service.order_details WHERE author_id=:authorId AND is_paid=:isPaid ORDER BY modify_date";
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("authorId", authorId).addValue("isPaid", Boolean.FALSE),
                    BeanPropertyRowMapper.newInstance(OrderDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<OrderDetails> getAllPaidOrdersByAuthorId(long authorId) {
        try {
            String query = "SELECT * FROM ether_service.order_details WHERE author_id=:authorId AND is_paid=:isPaid ORDER BY modify_date";
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("authorId", authorId).addValue("isPaid", Boolean.TRUE),
                    BeanPropertyRowMapper.newInstance(OrderDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<OrderDetails> getAllUnPaidOrders() {
        try {
            String query = "SELECT * FROM ether_service.order_details WHERE is_paid=:isPaid ORDER BY modify_date";
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("isPaid", Boolean.FALSE),
                    BeanPropertyRowMapper.newInstance(OrderDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<OrderDetails> getAllPaidOrders() {
        try {
            String query = "SELECT * FROM ether_service.order_details WHERE is_paid=:isPaid ORDER BY modify_date";
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("isPaid", Boolean.TRUE),
                    BeanPropertyRowMapper.newInstance(OrderDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public Map<String, Object> saveOrUpdateTransactionDetails(TransactionDetail transactionDetail) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource
                    .addValue("userBillingId", transactionDetail.getUserBillingId())
                    .addValue("orderId", transactionDetail.getOrderId())
                    .addValue("transactionId", transactionDetail.getTransactionId())
                    .addValue("amount", transactionDetail.getAmount())
                    .addValue("currency", transactionDetail.getCurrency())
                    .addValue("screenshotPath", transactionDetail.getScreenshotPath())
                    .addValue("paymentMode", transactionDetail.getPaymentMode())
                    .addValue("paymentDate", transactionDetail.getPaymentDate())
                    .addValue("modifyDate", transactionDetail.getModifyDate())
                    .addValue("paymentStatus", transactionDetail.getPaymentStatus());
            return new SimpleJdbcCall(jdbcTemplate.getJdbcTemplate().getDataSource())
                    .withSchemaName("ether_service")
                    .withProcedureName("spInsertOrUpdateTransactionDetails")
                    .returningResultSet("transactionDetails", BeanPropertyRowMapper.newInstance(TransactionDetail.class))
                    .execute(mapSqlParameterSource);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public int updatePaymentStatus(TransactionDetail transactionDetail) {
        try {
            String paymentStatus = transactionDetail.getPaymentStatus();
            long billingId = transactionDetail.getBillingId();
            String query = "UPDATE ether_service.transaction_details SET payment_status=:paymentStatus WHERE billing_id=:billingId";
            return jdbcTemplate.update(query, new MapSqlParameterSource().addValue("paymentStatus", paymentStatus).addValue("billingId", billingId));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<TransactionDetail> getTransactionDetailsById(long id) {
        try {
            String query = "SELECT * FROM ether_service.transaction_details WHERE billing_id=:id OR user_billing_id=:id OR order_id=:id OR transaction_id=:id";
            return jdbcTemplate.query(query, new MapSqlParameterSource().addValue("id", id), BeanPropertyRowMapper.newInstance(TransactionDetail.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public int updateOrderStatus(OrderDetails order) {
        try {
            String query = "UPDATE ether_service.order_details SET status=:status, warning_date=:warningDate, expiry_date=:expiryDate, modify_date=:modifyDate, is_paid=:isPaid" +
                    " WHERE order_id=:orderId";
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("status", order.getStatus())
                    .addValue("isPaid", order.getIsPaid())
                    .addValue("warningDate", order.getWarningDate())
                    .addValue("expiryDate", order.getExpiryDate())
                    .addValue("modifyDate", order.getModifyDate())
                    .addValue("orderId", order.getOrderId());
            return jdbcTemplate.update(query, mapSqlParameterSource);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<OrderDetails> getPendingOrderByAuthorId(long authorId) {
        try {
            String query = "SELECT * FROM ether_service.order_details WHERE status=:status AND author_id=:authorId ORDER BY modify_date";
            return jdbcTemplate.query(query, new MapSqlParameterSource().addValue("status", Constants.PENDING).addValue("authorId", authorId),
                    BeanPropertyRowMapper.newInstance(OrderDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<OrderDetails> getAllPendingOrders() {
        try {
            String query = "SELECT * FROM ether_service.order_details WHERE status=:status ORDER BY modify_date";
            return jdbcTemplate.query(query, new MapSqlParameterSource().addValue("status", Constants.PENDING),
                    BeanPropertyRowMapper.newInstance(OrderDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<OrderDetails> getPendingOrderByAuthorIdAndFileId(long authorId, long fileId) {
        try {
            String query = "SELECT * FROM ether_service.order_details WHERE status=:status AND author_id=:authorId AND file_id=:fileId ORDER BY modify_date";
            return jdbcTemplate.query(query, new MapSqlParameterSource().addValue("status", Constants.PENDING)
                            .addValue("authorId", authorId).addValue("fileId", fileId),
                    BeanPropertyRowMapper.newInstance(OrderDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public void uploadQRCodeToDB(String qrCodeName, String qrCodePath) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("name", qrCodeName).addValue("value", qrCodePath);
            new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getJdbcTemplate().getDataSource()))
                    .withTableName("properties")
                    .usingColumns("name", "value")
                    .execute(mapSqlParameterSource);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public void updateQRCode(String qrCodeName, String path) {
        try {
            String query = "UPDATE ether_service.properties SET value=:path WHERE name=:qrCodeName";
            jdbcTemplate.update(query, new MapSqlParameterSource().addValue("path", path).addValue("qrCodeName", qrCodeName));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public int updateOrder(OrderDetails orderDetails) {
        try {
            String query = "UPDATE ether_service.order_details SET selected_services=:selectedServices, estimated_amount=:estimatedAmount, " +
                    "currency=:currency, modify_date=:modifyDate, remark=:remark, status=:status " +
                    "WHERE order_id=:orderId";
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("selectedServices", orderDetails.getSelectedServices())
                    .addValue("estimatedAmount", orderDetails.getEstimatedAmount())
                    .addValue("currency", orderDetails.getCurrency())
                    .addValue("modifyDate", Timestamp.valueOf(LocalDateTime.now()))
                    .addValue("remark", orderDetails.getRemark())
                    .addValue("status", orderDetails.getStatus())
                    .addValue("orderId", orderDetails.getOrderId());
            return jdbcTemplate.update(query, mapSqlParameterSource);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException("Something went wrong");
        }
    }

    @Override
    public String fetchQrCodeByName(String qrCodeType) {
        try {
            String query = "SELECT value FROM ether_service.properties WHERE name=:qrCodeType";
            return jdbcTemplate.queryForObject(query, new MapSqlParameterSource().addValue("qrCodeType", qrCodeType), String.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }
}
