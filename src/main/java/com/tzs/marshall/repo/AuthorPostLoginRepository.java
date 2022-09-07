package com.tzs.marshall.repo;

import com.tzs.marshall.bean.PersistentUserDetails;

import java.util.List;
import java.util.Map;

public interface AuthorPostLoginRepository {

    List<PersistentUserDetails> getUserDetailsById(Long userId);

    Map<String, Object> saveOrUpdateUserDetails(PersistentUserDetails authorDetails);
}



