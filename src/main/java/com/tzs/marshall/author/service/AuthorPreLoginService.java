package com.tzs.marshall.author.service;

import com.tzs.marshall.author.bean.PersistentAuthorDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthorPreLoginService extends UserDetailsService {

    PersistentAuthorDetails handleFetchedValidUser(String userName);


    String handleOtpGeneration(String email);

    String handleOtpVerification(String otp);

    String resetPasswordHandler(String token, String reqType, String password);
}
