package com.tzs.marshall.token;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class ConfirmationToken {

    private Long id;
    private String token;
    private String email;
    private String reqType;
    private String userType;
    private Timestamp createdAt;
    private Timestamp expiresAt;
    private Timestamp confirmsAt;

    public ConfirmationToken (String token, String reqType, String userType, String email, Timestamp createdAt, Timestamp expiresAt, Timestamp confirmsAt) {
        this.token = token;
        this.reqType = reqType;
        this.userType = userType;
        this.email = email;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.confirmsAt = confirmsAt;
    }

    public ConfirmationToken (String token, String reqType, String userType, String email, Timestamp createdAt, Timestamp expiresAt) {
        this.token = token;
        this.reqType = reqType;
        this.userType = userType;
        this.email = email;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }
}
