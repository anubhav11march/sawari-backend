package com.tzs.marshall.service.impl;

import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.ProfileDetails;
import com.tzs.marshall.filesystem.FileHelper;
import com.tzs.marshall.repo.UserPostLoginRepository;
import com.tzs.marshall.service.UserPostLoginService;
import com.tzs.marshall.service.UserRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class UserPostLoginServiceImpl implements UserPostLoginService {

    @Autowired
    private UserPostLoginRepository userPostLoginRepository;
    @Autowired
    private UserRegistrationService userRegistrationService;
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
    public PersistentUserDetails updateUserDetails(PersistentUserDetails userDetails) {
        userRegistrationService.validateUniqueUserMobileNumber(userDetails.getUserId(), userDetails.getMobile());
        log.info("Updating details in DB...");
        userPostLoginRepository.updateEssentialDetails(userDetails);
        log.info("Details Updated Successfully...{}", userDetails);
        return handleFetchedFullUserDetails(userDetails);
    }

    @Override
    public PersistentUserDetails updateDriverDetails(PersistentUserDetails userDetails) {
        userRegistrationService.validateUniqueUserMobileNumber(userDetails.getUserId(), userDetails.getPaytmNumber());
        log.info("Updating details in DB...");
        ProfileDetails profileDetails =
                new ProfileDetails(userDetails, userDetails.getPaytmNumber(), userDetails.getRickshawNumber(), userDetails.getRickshawFrontPhoto(),
                        userDetails.getRickshawBackPhoto(), userDetails.getRickshawSidePhoto());
        fileHelper.fetchAndUploadProfileDetails(profileDetails);
        userPostLoginRepository.updateProfileDetails(profileDetails);
        log.info("Details Updated Successfully...{}", userDetails);
        return handleFetchedFullUserDetails(userDetails);
    }

    @Override
    public void fetchProfileImageById(Long userId, HttpServletResponse response) {
        List<PersistentUserDetails> userProfile = userPostLoginRepository.getUserProfileAndEssentialDetailsById(userId);
        if (userProfile.size() != 0) {
            PersistentUserDetails profileDetails = userProfile.stream().findFirst().get();
            String profileName = profileDetails.getProfilePhotoName();
            String profilePath = profileDetails.getProfilePhotoPath();
            if (profileName != null && profilePath != null) {
                fileHelper.serveImageInResponse(profileName, response, profilePath);
            }
        } else {
            log.warn("No Profile Image Found");
        }
    }

    @Override
    public void updateProfileImage(Long userId, MultipartFile profilePhoto) {
        ProfileDetails profileDetails = new ProfileDetails();
        profileDetails.setUserId(userId);
        profileDetails.setProfilePhoto(profilePhoto);
        fileHelper.fetchAndUploadProfileDetails(profileDetails);
        userPostLoginRepository.updateProfilePhoto(profileDetails);
    }

}

