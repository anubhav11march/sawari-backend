package com.ether.author.token;

public interface ConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken confirmationToken);

    void updateConfirmsTokenAt(ConfirmationToken confirmationToken);

    ConfirmationToken getConfirmationToken(String token, String reqType);

    ConfirmationToken confirmToken(String token, String reqType);

    String tokenHandler(String email, String reqType, String url);

    String resendTokenHandler(String token, String reqType, String url);
}
