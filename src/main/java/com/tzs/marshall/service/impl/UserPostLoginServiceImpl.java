package com.tzs.marshall.service.impl;

import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.ProfileDetails;
import com.tzs.marshall.error.ApiException;
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
    public PersistentUserDetails updateDriverDetails(PersistentUserDetails driverNewDetails) {
        userRegistrationService.validateUniqueUserMobileNumber(driverNewDetails.getUserId(), driverNewDetails.getPaytmNumber());
        PersistentUserDetails driverOldDetails = userPostLoginRepository.getUserProfileAndEssentialDetailsById(driverNewDetails.getUserId()).stream().findFirst().get();
        log.info("Updating details in DB...");
        boolean skipRickshawDetailsUpdate = checkToUpdateRickshawDetails(driverNewDetails, driverOldDetails);
        if (skipRickshawDetailsUpdate) {
            log.info("skipping rickshaw details..");
            if (driverOldDetails.getPaytmNumber().equalsIgnoreCase(driverNewDetails.getPaytmNumber())) {
                log.info("Nothing to update.");
                return handleFetchedFullUserDetails(driverNewDetails);
            } else {
                log.info("updating paytm number");
                userPostLoginRepository.updateDriverPaytmNumber(driverNewDetails.getUserId(), driverNewDetails.getPaytmNumber());
            }
        } else {
            log.info("Updating all driver details...");
            ProfileDetails profileDetails =
                    new ProfileDetails(driverNewDetails, driverNewDetails.getPaytmNumber(), driverNewDetails.getRickshawNumber(), driverNewDetails.getRickshawFrontPhoto(),
                            driverNewDetails.getRickshawBackPhoto(), driverNewDetails.getRickshawSidePhoto());
            fileHelper.fetchAndUploadProfileDetails(profileDetails);
            userPostLoginRepository.updateProfileDetails(profileDetails);
        }
        log.info("Details Updated Successfully...{}", driverNewDetails);
        return handleFetchedFullUserDetails(driverNewDetails);
    }

    private boolean checkToUpdateRickshawDetails(PersistentUserDetails driverNewDetails, PersistentUserDetails driverOldDetails) {
        boolean shouldSkip = driverNewDetails.getRickshawFrontPhoto() == null && driverNewDetails.getRickshawBackPhoto() == null
                && driverNewDetails.getRickshawSidePhoto() == null;
        boolean shouldUpdate = driverNewDetails.getRickshawFrontPhoto() != null && driverNewDetails.getRickshawBackPhoto() != null
                && driverNewDetails.getRickshawSidePhoto() != null;
        if (driverNewDetails.getRickshawNumber().equalsIgnoreCase(driverOldDetails.getRickshawNumber())) {
            if (shouldSkip) {
                return Boolean.TRUE;
            } else if (shouldUpdate) {
                return Boolean.FALSE;
            } else {
                log.error("either one or all rickshaw photo is not available to update");
                throw new ApiException("Please update all 3 rickshaw photos");
            }
        } else {
            if (shouldUpdate) {
                return Boolean.FALSE;
            } else {
                log.error("Driver trying to update the number but either one or all rickshaw photo is not available to update");
                throw new ApiException("Please update all 3 rickshaw photos to update your rickshaw number");
            }
        }
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

