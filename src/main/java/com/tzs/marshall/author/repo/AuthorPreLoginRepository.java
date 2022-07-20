package com.tzs.marshall.author.repo;

import java.util.Map;

public interface AuthorPreLoginRepository {

    Map<String, Object> getValidUser(String userName);

    int updatePassword(String email, String password);

    int saveOtp(String email, String otp);

    Map<String, String> getValidOtpAndEmail(String otp);
}



