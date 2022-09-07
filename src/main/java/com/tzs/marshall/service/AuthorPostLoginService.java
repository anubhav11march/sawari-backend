package com.tzs.marshall.service;

import com.tzs.marshall.bean.PersistentUserDetails;

import java.util.List;

public interface AuthorPostLoginService {

    PersistentUserDetails handleFetchedFullAuthorDetails(PersistentUserDetails authorDetails);

    List<PersistentUserDetails> updateAuthorDetails(PersistentUserDetails authorDetails);
}
