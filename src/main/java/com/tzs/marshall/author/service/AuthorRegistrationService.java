package com.tzs.marshall.author.service;

import com.tzs.marshall.author.bean.PersistentAuthorDetails;

public interface AuthorRegistrationService{

    PersistentAuthorDetails registerNewUser(PersistentAuthorDetails userDetails, String url);

    String enableAccountTokenHandler(String token, String reqType);
}
