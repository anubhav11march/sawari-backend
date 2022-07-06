package com.ether.author.service;

import com.ether.author.bean.PersistentAuthorDetails;

import java.util.List;

public interface AuthorPostLoginService {

    PersistentAuthorDetails handleFetchedFullAuthorDetails(PersistentAuthorDetails authorDetails);

    List<PersistentAuthorDetails> updateAuthorDetails(PersistentAuthorDetails authorDetails);
}
