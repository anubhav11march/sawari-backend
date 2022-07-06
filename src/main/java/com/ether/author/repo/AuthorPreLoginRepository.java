package com.ether.author.repo;

import com.ether.author.bean.NewsLetterEmailSubs;
import com.ether.author.bean.PersistentAuthorDetails;

import java.util.List;
import java.util.Map;

public interface AuthorPreLoginRepository {

    Map<String, Object> getValidUser(String userName);

    int updatePassword(String email, String password);

    int saveOtp(String email, String otp);

    Map<String, String> getValidOtpAndEmail(String otp);
}



