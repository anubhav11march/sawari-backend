package com.tzs.marshall.service;

import com.tzs.marshall.bean.PersistentUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserPreLoginService extends UserDetailsService {

    PersistentUserDetails handleFetchedValidUser(String userName);


    String generateAndSaveOTP(String email);

    String handleOtpVerification(String otp);

    String resetPasswordHandler(String token, String reqType, String password);

    String getUsernameBySessionId(String sessionId, long creationTime);
}
