package com.ether.author.mailsender;

import com.ether.author.constants.RequestTypeDictionary;

public interface EmailService {

    void send(EmailBean emailBean);

    String buildEmail(RequestTypeDictionary requestTypeDictionary, String token, String url);

    void sendConfirmationEmail(String email, String token, String url, RequestTypeDictionary requestTypeDictionary);

}
