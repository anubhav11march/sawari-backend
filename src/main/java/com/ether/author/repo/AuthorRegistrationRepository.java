package com.ether.author.repo;

import com.ether.author.bean.NewsLetterEmailSubs;
import com.ether.author.bean.PersistentAuthorDetails;

import java.util.List;
import java.util.Map;

public interface AuthorRegistrationRepository {

    List<NewsLetterEmailSubs> findSubsIdByEmail(String email);

    List<NewsLetterEmailSubs> saveNewsLetterSubsEmail(String email);

    int saveUserEssentialDetails(PersistentAuthorDetails userDetails, Long subsId);

    void insertIntoUserBridgeTable(String userName, String roleName, String typeName);

    List<PersistentAuthorDetails> findExistingUsers(PersistentAuthorDetails authorDetails);

    int enableUser(String authorEmail);

    void rollbackRegistration(PersistentAuthorDetails authorDetails);
}



