package com.tzs.marshall.repo;

import java.util.Map;

public interface UserPreLoginRepository {

    Map<String, Object> getValidUser(String userName);

    int updatePassword(String email, String password);

    int saveOtp(String email, String otp);

    Map<String, String> getValidOtpAndEmail(String otp);
}



