package com.tzs.marshall.author.repo;

import com.tzs.marshall.author.bean.NewsLetterEmailSubs;
import com.tzs.marshall.author.bean.PersistentAuthorDetails;

import java.util.List;

public interface AuthorRegistrationRepository {

    List<NewsLetterEmailSubs> findSubsIdByEmail(String email);

    List<NewsLetterEmailSubs> saveNewsLetterSubsEmail(String email);

    int saveUserEssentialDetails(PersistentAuthorDetails userDetails, Long subsId);

    void insertIntoUserBridgeTable(String userName, String roleName, String typeName);

    List<PersistentAuthorDetails> findExistingUsers(PersistentAuthorDetails authorDetails);

    int enableUser(String authorEmail);

    void rollbackRegistration(PersistentAuthorDetails authorDetails);
}



