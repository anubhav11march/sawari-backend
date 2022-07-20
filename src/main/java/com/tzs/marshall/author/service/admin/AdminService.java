package com.tzs.marshall.author.service.admin;

import com.tzs.marshall.author.bean.PersistentAuthorDetails;
import com.tzs.marshall.author.bean.PersistentAuthorRights;

import java.util.List;

public interface AdminService {
    List<PersistentAuthorDetails> getAuthorsRights();

    PersistentAuthorDetails getAuthorRightsById(Long userId);

    PersistentAuthorDetails updateAuthorRights(PersistentAuthorRights authorRights);

    List<PersistentAuthorDetails> getAllCompleteProfileAuthors();

    List<PersistentAuthorDetails> getAllIncompleteProfileAuthors();

    PersistentAuthorDetails getAuthorDetailsById(Long authorId);

    void checkAuthorizedAdmin(Long userId);
}
