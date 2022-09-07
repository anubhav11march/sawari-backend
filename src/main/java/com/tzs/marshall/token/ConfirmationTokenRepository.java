package com.tzs.marshall.token;


public interface ConfirmationTokenRepository {

    ConfirmationToken findByToken(String token, String reqType);

    int updateConfirmedAt(ConfirmationToken confirmationToken);

    int saveToken(ConfirmationToken confirmationToken);
}
