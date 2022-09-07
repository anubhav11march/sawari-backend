package com.tzs.marshall.service;

import com.tzs.marshall.bean.PersistentUserDetails;

public interface AuthorRegistrationService{

    PersistentUserDetails registerNewUser(PersistentUserDetails userDetails, String url);

    String enableAccountTokenHandler(String token, String reqType);
}
