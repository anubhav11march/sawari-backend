package com.tzs.marshall.repo.admin;

import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.PersistentUserRights;

import java.util.List;
import java.util.Map;

public interface AdminRepository {
    List<PersistentUserDetails> getAllUserRights();

    List<PersistentUserDetails> getUserRightsById(Long userId);

    Map<String, Object> updateUserRights(PersistentUserRights authorRights);

    List<PersistentUserDetails> getAllUsers(String role, int after, int limit);

    List<PersistentUserDetails> getAllIncompleteProfileUsersDetails(String role, int after, int limit);

    List<PersistentUserDetails> getCompleteUserProfileDetailsById(Long userId);

    List<PersistentUserDetails> getUserProfileDetailsById(Long userId);

    List<PersistentUserDetails> getAllUsersProfile(String role, int after, int limit, Map filters);
}
