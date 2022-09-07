package com.tzs.marshall.repo;

import com.tzs.marshall.bean.NewsLetterEmailSubs;
import com.tzs.marshall.bean.PersistentUserDetails;

import java.util.List;

public interface AuthorRegistrationRepository {

    List<NewsLetterEmailSubs> findSubsIdByEmail(String email);

    List<NewsLetterEmailSubs> saveNewsLetterSubsEmail(String email);

    int saveUserEssentialDetails(PersistentUserDetails userDetails, Long subsId);

    void insertIntoUserBridgeTable(String userName, String roleName, String typeName);

    List<PersistentUserDetails> findExistingUsers(PersistentUserDetails authorDetails);

    int enableUser(String authorEmail);

    void rollbackRegistration(PersistentUserDetails authorDetails);
}



