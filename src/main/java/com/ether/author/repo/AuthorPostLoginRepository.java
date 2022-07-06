package com.ether.author.repo;

import com.ether.author.bean.PersistentAuthorDetails;

import java.util.List;
import java.util.Map;

public interface AuthorPostLoginRepository {

    List<PersistentAuthorDetails> getUserDetailsById(Long userId);

    Map<String, Object> saveOrUpdateUserDetails(PersistentAuthorDetails authorDetails);
}



