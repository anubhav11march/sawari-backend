package com.tzs.marshall.service.impl;

import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.repo.UserPostLoginRepository;
import com.tzs.marshall.service.UserPostLoginService;
import com.tzs.marshall.validators.UserDetailsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPostLoginServiceImpl implements UserPostLoginService {

    @Autowired
    private UserPostLoginRepository userPostLoginRepository;

    private static final Logger log = LoggerFactory.getLogger(UserPostLoginServiceImpl.class);

    @Override
    public PersistentUserDetails handleFetchedFullAuthorDetails(PersistentUserDetails authorDetails) {
        log.info("available user details: " + authorDetails);
        if (authorDetails.getUmappingId() == null) {
            log.info("fetching complete profile details from db...");
            List<PersistentUserDetails> userDetailsById = userPostLoginRepository.getUserDetailsById(authorDetails.getUserId());
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
        UserDetailsValidator.validateEmail(authorDetails.getEmail());
        log.info("Updating details in DB...");
        userPostLoginRepository.saveOrUpdateUserDetails(authorDetails);
        log.info("Details Updated Successfully...{}", authorDetails);
        return userPostLoginRepository.getUserDetailsById(authorDetails.getUserId());
    }
}

