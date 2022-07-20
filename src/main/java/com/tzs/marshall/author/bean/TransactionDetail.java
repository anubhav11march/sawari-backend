package com.tzs.marshall.author.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionDetail {
    private Long billingId;
    private Long userBillingId;
    private Long orderId;
    private String transactionId;
    private Double amount;
    private String currency;
    private MultipartFile screenshot;
    private String screenshotPath;
    private String paymentMode;
    private String paymentDate;
    private Timestamp modifyDate;
    private String paymentStatus;
}
