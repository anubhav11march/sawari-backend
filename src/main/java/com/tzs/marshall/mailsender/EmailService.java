package com.tzs.marshall.mailsender;

import com.tzs.marshall.constants.RequestTypeDictionary;

public interface EmailService {

    void send(EmailBean emailBean);

    String buildEmail(RequestTypeDictionary requestTypeDictionary, String token, String url);

    void sendConfirmationEmail(String email, String token, String url, RequestTypeDictionary requestTypeDictionary);

    void sendOTPToMobile(String mobileNumber, String otp);

    void sendOTPToEmail(String mobileNumber, String otp, RequestTypeDictionary requestTypeDictionary);

}
