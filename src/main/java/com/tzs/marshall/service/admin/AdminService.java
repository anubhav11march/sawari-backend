package com.tzs.marshall.service.admin;

import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.PersistentUserRights;
import com.tzs.marshall.bean.UserRideEarnings;

import java.util.List;
import java.util.Map;

public interface AdminService {
    List<PersistentUserDetails> getUsersRights();

    PersistentUserDetails getUserRightsById(Long userId);

    PersistentUserDetails updateUserRights(PersistentUserRights authorRights);

    List<PersistentUserDetails> getAllUsersByRole(String role, int after, int limit, Map filters);

    List<PersistentUserDetails> getAllIncompleteProfileUsersByRole(String role, int after, int limit);

    PersistentUserDetails getUserDetailsById(Long authorId);

    void checkAuthorizedAdmin(Long userId);

    List<UserRideEarnings> getAllUsersAndRidesByRole(String role, int after, int limit, Map filters);

    UserRideEarnings getUserAndRideById(String userId);
}
