package com.ether.author.repo.admin;

import com.ether.author.bean.PersistentAuthorDetails;
import com.ether.author.bean.PersistentAuthorRights;

import java.util.List;
import java.util.Map;

public interface AdminRepository {
    List<PersistentAuthorDetails> getAllAuthorRights();

    List<PersistentAuthorDetails> getAuthorRightsById(Long userId);

    Map<String, Object> updateAuthorRights(PersistentAuthorRights authorRights);

    List<PersistentAuthorDetails> getAllAuthors();

    List<PersistentAuthorDetails> getAllIncompleteProfileAuthorsDetails();

    List<PersistentAuthorDetails> getCompleteAuthorProfileDetailsById(Long authorId);

    List<PersistentAuthorDetails> getAuthorProfileDetailsById(Long authorId);
}
