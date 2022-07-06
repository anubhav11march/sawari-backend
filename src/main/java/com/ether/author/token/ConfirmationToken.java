package com.ether.author.token;

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
    private Timestamp createdAt;
    private Timestamp expiresAt;
    private Timestamp confirmsAt;

    public ConfirmationToken (String token, String reqType, String email, Timestamp createdAt, Timestamp expiresAt, Timestamp confirmsAt) {
        this.token = token;
        this.reqType = reqType;
        this.email = email;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.confirmsAt = confirmsAt;
    }

    public ConfirmationToken (String token, String reqType, String email, Timestamp createdAt, Timestamp expiresAt) {
        this.token = token;
        this.reqType = reqType;
        this.email = email;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }
}
