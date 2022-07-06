package com.ether.author.token;


import java.sql.Timestamp;
import java.util.List;

public interface ConfirmationTokenRepository {

    ConfirmationToken findByToken(String token, String reqType);

    int updateConfirmedAt(ConfirmationToken confirmationToken);

    int saveToken(ConfirmationToken confirmationToken);
}
