package com.tzs.marshall.service;

import com.tzs.marshall.bean.PersistentUserDetails;

import java.util.List;

public interface UserPostLoginService {

    PersistentUserDetails handleFetchedFullUserDetails(PersistentUserDetails authorDetails);

    List<PersistentUserDetails> updateUserDetails(PersistentUserDetails authorDetails);
}
