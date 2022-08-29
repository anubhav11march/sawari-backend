package com.tzs.marshall.service.admin;

import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.PersistentUserRights;

import java.util.List;

public interface AdminService {
    List<PersistentUserDetails> getAuthorsRights();

    PersistentUserDetails getAuthorRightsById(Long userId);

    PersistentUserDetails updateAuthorRights(PersistentUserRights authorRights);

    List<PersistentUserDetails> getAllCompleteProfileUsersByRole(String role);

    List<PersistentUserDetails> getAllIncompleteProfileUsersByRole(String role);

    PersistentUserDetails getAuthorDetailsById(Long authorId);

    void checkAuthorizedAdmin(Long userId);
}
