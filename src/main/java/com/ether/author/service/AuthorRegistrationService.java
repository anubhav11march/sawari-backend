package com.ether.author.service;

import com.ether.author.bean.PersistentAuthorDetails;

public interface AuthorRegistrationService{

    PersistentAuthorDetails registerNewUser(PersistentAuthorDetails userDetails, String url);

    String enableAccountTokenHandler(String token, String reqType);
}
