package com.tzs.marshall.service.impl;

import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.repo.AuthorPostLoginRepository;
import com.tzs.marshall.service.AuthorPostLoginService;
import com.tzs.marshall.validators.AuthorDetailsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorPostLoginServiceImpl implements AuthorPostLoginService {

    @Autowired
    private AuthorPostLoginRepository authorPostLoginRepository;

    private static final Logger log = LoggerFactory.getLogger(AuthorPostLoginServiceImpl.class);

    @Override
    public PersistentUserDetails handleFetchedFullAuthorDetails(PersistentUserDetails authorDetails) {
        log.info("available user details: " + authorDetails);
        if (authorDetails.getUmappingId() == null) {
            log.info("fetching complete profile details from db...");
            List<PersistentUserDetails> userDetailsById = authorPostLoginRepository.getUserDetailsById(authorDetails.getUserId());
            if (userDetailsById.size() == 0) {
                log.info("complete profile details not yet updated, returning essential details...{}", authorDetails);
                return authorDetails;
            }
            log.info("complete profile details found...");
            authorDetails = userDetailsById.stream().findFirst().get();
        }
        return authorDetails;
    }

    @Override
    public List<PersistentUserDetails> updateAuthorDetails(PersistentUserDetails authorDetails) {
        log.info("validating email...{}", authorDetails.getEmail());
        AuthorDetailsValidator.validateEmail(authorDetails.getEmail());
        log.info("Updating details in DB...");
        authorPostLoginRepository.saveOrUpdateUserDetails(authorDetails);
        log.info("Details Updated Successfully...{}", authorDetails);
        return authorPostLoginRepository.getUserDetailsById(authorDetails.getUserId());
    }
}

