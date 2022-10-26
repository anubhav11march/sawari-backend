package com.tzs.marshall.service.impl;

import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.ProfileDetails;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.filesystem.FileHelper;
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
    @Autowired
    private FileHelper fileHelper;

    private static final Logger log = LoggerFactory.getLogger(UserPostLoginServiceImpl.class);

    @Override
    public PersistentUserDetails handleFetchedFullUserDetails(PersistentUserDetails userDetails) {
        log.info("available user details: " + userDetails);
        if (userDetails.getUmappingId() == null) {
            log.info("fetching complete profile details from db...");
            List<PersistentUserDetails> userDetailsById = userPostLoginRepository.getUserDetailsById(userDetails.getUserId());
            if (userDetailsById.size() == 0) {
                log.info("complete profile details not yet updated, fetching essential and profile details...{}", userDetails);
                userDetailsById = userPostLoginRepository.getUserProfileAndEssentialDetailsById(userDetails.getUserId());
                return userDetailsById.stream().findFirst().orElse(userDetails);
            }
            log.info("complete profile details found...");
            userDetails = userDetailsById.stream().findFirst().get();
        }
        return userDetails;
    }

    @Override
    public List<PersistentUserDetails> updateUserDetails(PersistentUserDetails userDetails) {
        if (Constants.USER.equalsIgnoreCase(userDetails.getRoleName())) {
            log.info("validating email...{}", userDetails.getEmail());
            UserDetailsValidator.validateEmail(userDetails.getEmail());
        }
        log.info("Updating details in DB...");
        userPostLoginRepository.saveOrUpdateUserDetails(userDetails);
        ProfileDetails profileDetails = new ProfileDetails(userDetails, userDetails.getRickshawNumber(), userDetails.getProfilePhoto(), userDetails.getRickshawPhoto());
        fileHelper.fetchAndUploadProfileDetails(profileDetails);
        userPostLoginRepository.updateProfileDetails(profileDetails);
        log.info("Details Updated Successfully...{}", userDetails);
        return List.of(handleFetchedFullUserDetails(userDetails));
    }
}

