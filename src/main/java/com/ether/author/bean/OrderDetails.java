package com.ether.author.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDetails {

    private Long orderId;
    private Long authorId;
    private Long fileId;
    private Long adminId;
    private Long reportId;
    private String selectedServices;
    private Double estimatedAmount;
    private  String currency;
    private Timestamp orderDate;
    private Timestamp expiryDate;
    private Timestamp warningDate;
    private Timestamp modifyDate;
    private String remark;
    private String status;
    private Boolean isActive;
    private Boolean isPaid;

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }
}
