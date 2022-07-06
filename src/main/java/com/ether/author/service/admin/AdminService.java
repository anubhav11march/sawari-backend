package com.ether.author.service.admin;

import com.ether.author.bean.PersistentAuthorDetails;
import com.ether.author.bean.PersistentAuthorRights;

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
