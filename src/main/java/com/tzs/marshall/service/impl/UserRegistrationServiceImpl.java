package com.tzs.marshall.service.impl;

import com.tzs.marshall.bean.ProfileDetails;
import com.tzs.marshall.bean.NewsLetterEmailSubs;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.constants.RequestTypeDictionary;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.filesystem.FileBean;
import com.tzs.marshall.filesystem.FileHelper;
import com.tzs.marshall.repo.UserRegistrationRepository;
import com.tzs.marshall.service.UserRegistrationService;
import com.tzs.marshall.token.ConfirmationToken;
import com.tzs.marshall.token.ConfirmationTokenService;
import com.tzs.marshall.validators.UserDetailsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    @Autowired
    private UserRegistrationRepository userRegistrationRepository;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoders;
    @Autowired
    private FileHelper fileHelper;

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationServiceImpl.class);

    @Override
    public PersistentUserDetails registerUser(PersistentUserDetails userDetails, String url) {
        log.info("Validating Email...");
        UserDetailsValidator.validateEmail(userDetails.getEmail());
        log.info("Email Validated\nValidating Password...");
        UserDetailsValidator.validatePassword(userDetails.getPassword());

        log.info("Checking Existing Users...");
        PersistentUserDetails existingButDisableUserDetails = checkExistingUsers(userDetails);

        userDetails.setPassword(bCryptPasswordEncoders.encode(userDetails.getPassword()));

        List<NewsLetterEmailSubs> newsLetterEmailSubs = checkAndSaveEmail(userDetails);

        //pass the user data to repo for saving
        try {
            if (existingButDisableUserDetails == null) {
                log.info("Saving User's details to db.");
                userRegistrationRepository.saveUserEssentialDetails(userDetails, newsLetterEmailSubs.stream().findFirst().get().getSubsId());
                userRegistrationRepository.insertIntoUserBridgeTable(userDetails.getUsername(), userDetails.getRoleName(), userDetails.getTypeName());
                log.info("User Record Inserted.\n" + userDetails);
            } else {
                userDetails = existingButDisableUserDetails;
            }
        } catch (Exception e) {
            log.warn(String.format("Unable to save user details, Rolling back the user details from db for [%s]", userDetails));
            userRegistrationRepository.rollbackRegistration(userDetails);
            throw new ApiException(e.getMessage());
        }
        log.info("Generating unique token...");
        try {
            confirmationTokenService.tokenHandler(userDetails.getEmail(), RequestTypeDictionary.ACCOUNT.getReqType(), userDetails.getRoleName(), url);
        } catch (Exception e) {
            log.warn(String.format("Unable to send mail, Rolling back the user details from db for [%s]", userDetails));
            userRegistrationRepository.rollbackRegistration(userDetails);
            throw new ApiException(e.getMessage());
        }
        log.info("User Registered!");
        return userDetails;
    }

    private List<NewsLetterEmailSubs> checkAndSaveEmail(PersistentUserDetails userDetails) {
        //find id of email to put in registration table subs_id then Insert into subscribe_by_email(email) values(?)
        log.info("Check if user has subscribed by email only.");
        List<NewsLetterEmailSubs> newsLetterEmailSubs = userRegistrationRepository.findSubsIdByEmail(userDetails.getEmail());
        if (newsLetterEmailSubs.size() == 0) {
            log.info("Saving User's Email to db.");
            newsLetterEmailSubs = userRegistrationRepository.saveNewsLetterSubsEmail(userDetails.getEmail());
        }
        return newsLetterEmailSubs;
    }

    @Override
    public ProfileDetails registerDriver(ProfileDetails userDetails) {
        UserDetailsValidator.validatePassword(userDetails.getPassword());
        log.info("Checking Existing Users...");
        PersistentUserDetails existingButDisableUserDetails = checkExistingUsers(userDetails);

        userDetails.setPassword(bCryptPasswordEncoders.encode(userDetails.getPassword()));
        //temp sol
        userDetails.setEmail(userDetails.getMobile());
        List<NewsLetterEmailSubs> newsLetterEmailSubs = checkAndSaveEmail(userDetails);

        PersistentUserDetails tempUserDetails = new PersistentUserDetails(null, userDetails.getEmail(), userDetails.getUserName(), userDetails.getMobile());
        //pass the user data to repo for saving
        try {
            if (existingButDisableUserDetails == null) {
                log.info("Saving User's details to db.");
                //temp solution
                userRegistrationRepository.saveUserEssentialDetails(userDetails, newsLetterEmailSubs.stream().findFirst().get().getSubsId());

                Long userId = userRegistrationRepository.findExistingUsers(tempUserDetails).stream().findFirst().get().getUserId();
                userDetails.setUserId(userId);
                fetchFilesInfo(userDetails);
                userRegistrationRepository.saveDriverImagesDetails(userDetails, userDetails.getRoleName());
                userRegistrationRepository.insertIntoUserBridgeTable(userDetails.getUsername(), userDetails.getRoleName(), userDetails.getTypeName());
                log.info("User Record Inserted.\n" + userDetails);
            } else {
                userDetails = (ProfileDetails) existingButDisableUserDetails;
            }
            log.info("Generating unique token...");
            confirmationTokenService.tokenHandler(userDetails.getMobile(), RequestTypeDictionary.ACCOUNT.getReqType(), userDetails.getRoleName(), null);
        } catch (Exception e) {
            log.warn(String.format("Unable to save user details, Rolling back the user details from db for [%s]", userDetails));
            userRegistrationRepository.rollbackRegistration(tempUserDetails);
            throw new ApiException(e.getMessage());
        }
        log.info("User Registered!");
        return userDetails;
    }

    private void fetchFilesInfo(ProfileDetails userDetails) {
        FileBean fileBean = fileHelper.uploadFileHelper(userDetails.getProfilePhoto(), userDetails.getUserId());
        userDetails.setProfilePhotoName(fileBean.getFileName());
        userDetails.setProfilePhotoPath(fileBean.getPath());
        userDetails.setProfilePhotoSize(fileBean.getSize());

        fileBean = fileHelper.uploadFileHelper(userDetails.getAadharBackPhoto(), userDetails.getUserId());
        userDetails.setAadharBackPhotoName(fileBean.getFileName());
        userDetails.setAadharBackPhotoPath(fileBean.getPath());
        userDetails.setAadharBackPhotoSize(fileBean.getSize());

        fileBean = fileHelper.uploadFileHelper(userDetails.getAadharFrontPhoto(), userDetails.getUserId());
        userDetails.setAadharFrontPhotoName(fileBean.getFileName());
        userDetails.setAadharFrontPhotoPath(fileBean.getPath());
        userDetails.setAadharFrontPhotoSize(fileBean.getSize());

        fileBean = fileHelper.uploadFileHelper(userDetails.getRickshawPhoto(), userDetails.getUserId());
        userDetails.setRickshawPhotoName(fileBean.getFileName());
        userDetails.setRickshawPhotoPath(fileBean.getPath());
        userDetails.setRickshawPhotoSize(fileBean.getSize());
    }

    private PersistentUserDetails checkExistingUsers(PersistentUserDetails authorDetails) {
        log.info("Verifying for existing user...");
        PersistentUserDetails existedUserDetails = userRegistrationRepository.findExistingUsers(authorDetails).stream().findFirst().orElseGet(null);
        if (existedUserDetails != null) {
            if (existedUserDetails.isEnabled()) {
                if (existedUserDetails.getEmail().equalsIgnoreCase(authorDetails.getEmail())) {
                    log.error("Email is already registered: " + authorDetails.getEmail());
                    throw new ApiException(MessageConstants.EMAIL_ALREADY_REGISTERED + authorDetails.getEmail());
                } else if (existedUserDetails.getUsername().equalsIgnoreCase(authorDetails.getUsername())) {
                    log.error("Username is already registered: " + authorDetails.getUsername());
                    throw new ApiException(MessageConstants.USERNAME_ALREADY_REGISTERED + authorDetails.getUsername());
                } else {
                    log.error("Mobile Number is already registered: " + authorDetails.getMobile());
                    throw new ApiException(MessageConstants.MOBILE_ALREADY_REGISTERED + authorDetails.getMobile());
                }
            }
        }
        return existedUserDetails;
    }

    @Override
    public String enableAccountTokenHandler(String token, String reqType) {
        log.info("Enabling User Account...");
        ConfirmationToken confirmationToken = confirmationTokenService.confirmToken(token, reqType);
        if (confirmationToken != null) {
            String email = confirmationToken.getEmail();
            userRegistrationRepository.enableUser(email, reqType);
            log.info("User: " + email + " is now enable.");
            return confirmationToken.getToken();
        }
        return "fail";
    }

}

