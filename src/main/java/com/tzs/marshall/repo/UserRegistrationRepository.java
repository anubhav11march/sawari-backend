package com.tzs.marshall.repo;

import com.tzs.marshall.bean.NewsLetterEmailSubs;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.ProfileDetails;
import com.tzs.marshall.repo.impl.UserRegistrationRepositoryImpl;

import java.util.List;

public interface UserRegistrationRepository {

    List<NewsLetterEmailSubs> findSubsIdByEmail(String email);

    List<NewsLetterEmailSubs> saveNewsLetterSubsEmail(String email);

    int saveUserEssentialDetails(PersistentUserDetails userDetails, Long subsId);

    void insertIntoUserBridgeTable(String userName, String roleName, String typeName);

    List<PersistentUserDetails> findExistingUsers(PersistentUserDetails authorDetails);

    int enableUser(String authorEmail, String reqType);

    void rollbackRegistration(PersistentUserDetails authorDetails);

    int saveDriverImagesDetails(ProfileDetails userDetails, String roleName);

    List<UserRegistrationRepositoryImpl.CustomRowMapper> findExistingUserWithMobileNumber(Long userId, String mobileNumber);
}



