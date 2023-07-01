package com.tzs.marshall.token;


public interface ConfirmationTokenRepository {

    ConfirmationToken findByToken(String token);

    int updateConfirmedAt(ConfirmationToken confirmationToken);

    int saveToken(ConfirmationToken confirmationToken);
}
