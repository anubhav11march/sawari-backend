package com.tzs.marshall.author.service;

import com.tzs.marshall.author.bean.PersistentAuthorDetails;

import java.util.List;

public interface AuthorPostLoginService {

    PersistentAuthorDetails handleFetchedFullAuthorDetails(PersistentAuthorDetails authorDetails);

    List<PersistentAuthorDetails> updateAuthorDetails(PersistentAuthorDetails authorDetails);
}
