package com.tzs.marshall.repo.admin;

import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.PersistentUserRights;

import java.util.List;
import java.util.Map;

public interface AdminRepository {
    List<PersistentUserDetails> getAllAuthorRights();

    List<PersistentUserDetails> getAuthorRightsById(Long userId);

    Map<String, Object> updateAuthorRights(PersistentUserRights authorRights);

    List<PersistentUserDetails> getAllAuthors(String role);

    List<PersistentUserDetails> getAllIncompleteProfileAuthorsDetails(String role);

    List<PersistentUserDetails> getCompleteAuthorProfileDetailsById(Long userId);

    List<PersistentUserDetails> getAuthorProfileDetailsById(Long userId);
}
