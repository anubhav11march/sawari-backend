package com.tzs.marshall.service.impl;

import com.tzs.marshall.bean.NewsLetterEmailSubs;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.ProfileDetails;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.constants.RequestTypeDictionary;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.filesystem.FileHelper;
import com.tzs.marshall.repo.RideRequestRepository;
import com.tzs.marshall.repo.UserRegistrationRepository;
import com.tzs.marshall.repo.impl.UserRegistrationRepositoryImpl;
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
import java.util.stream.Collectors;

import static com.tzs.marshall.constants.Constants.OFF_DUTY;

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
    @Autowired
    private RideRequestRepository rideRequestRepository;

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationServiceImpl.class);

    @Override
    public PersistentUserDetails registerUser(PersistentUserDetails userDetails, String url) {
        log.info("Validating Email...");
        UserDetailsValidator.validateEmail(userDetails.getEmail());
        log.info("Email Validated\nValidating Password...");
        UserDetailsValidator.validatePassword(userDetails.getPassword());

        log.info("Checking Existing Users...");
        boolean existedIfDisabledUser = checkExistedIfDisabledUser(userDetails);

        //pass the user data to repo for saving
        try {
            if (!existedIfDisabledUser) {
                userDetails.setPassword(bCryptPasswordEncoders.encode(userDetails.getPassword()));
                List<NewsLetterEmailSubs> newsLetterEmailSubs = checkAndSaveEmail(userDetails);
                log.info("Saving User's details to db.");
                userRegistrationRepository.saveUserEssentialDetails(userDetails, newsLetterEmailSubs.stream().findFirst().get().getSubsId());
                userRegistrationRepository.insertIntoUserBridgeTable(userDetails.getUsername(), userDetails.getRoleName(), userDetails.getTypeName());
                log.info("User Record Inserted.\n" + userDetails);
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
        boolean existedIfDisabledUser = checkExistedIfDisabledUser(userDetails);

        userDetails.setPassword(bCryptPasswordEncoders.encode(userDetails.getPassword()));
        //temp sol
        //change email to mobile if otp needs to be send on mobile
        userDetails.setEmail(userDetails.getEmail());
        List<NewsLetterEmailSubs> newsLetterEmailSubs = checkAndSaveEmail(userDetails);

        PersistentUserDetails tempUserDetails = new PersistentUserDetails(null, userDetails.getEmail(), userDetails.getUserName(), userDetails.getMobile(), userDetails.getPaytmNumber());
        //pass the user data to repo for saving
        try {
            if (!existedIfDisabledUser) {
                log.info("Saving User's details to db.");
                //temp solution
                userRegistrationRepository.saveUserEssentialDetails(userDetails, newsLetterEmailSubs.stream().findFirst().get().getSubsId());

                Long userId = userRegistrationRepository.findExistingUsers(tempUserDetails).stream().findFirst().get().getUserId();
                userDetails.setUserId(userId);
                fileHelper.fetchAndUploadProfileDetails(userDetails);
                userRegistrationRepository.saveDriverImagesDetails(userDetails, userDetails.getRoleName());
                userRegistrationRepository.insertIntoUserBridgeTable(userDetails.getUsername(), userDetails.getRoleName(), userDetails.getTypeName());
                log.info("User Record Inserted.\n" + userDetails);
                rideRequestRepository.updateDriverDutyStatusById(userId, OFF_DUTY);
            }
            log.info("Generating unique token...");
            //change email to mobile if otp needs to be send on mobile
            confirmationTokenService.tokenHandler(userDetails.getEmail(), RequestTypeDictionary.ACCOUNT.getReqType(), userDetails.getRoleName(), null);
        } catch (Exception e) {
            log.warn(String.format("Unable to save user details, Rolling back the user details from db for [%s]", userDetails));
            userRegistrationRepository.rollbackRegistration(tempUserDetails);
            throw new ApiException(e.toString());
        }
        log.info("User Registered!");
        return userDetails;
    }

    private boolean checkExistedIfDisabledUser(PersistentUserDetails authorDetails) {
        boolean existedIfDisabledUser = Boolean.FALSE;
        log.info("Verifying for existing user...");
        List<PersistentUserDetails> existingUsers = userRegistrationRepository.findExistingUsers(authorDetails);
        if (existingUsers.size() > 0) {
            if (existingUsers.stream().anyMatch(eu -> eu.getEmail().equalsIgnoreCase(authorDetails.getEmail())
                    && eu.isEnabled())) {
                log.error("Email is already registered: " + authorDetails.getEmail());
                throw new ApiException(MessageConstants.EMAIL_ALREADY_REGISTERED + authorDetails.getEmail());
            } else if (existingUsers.stream().anyMatch(eu -> eu.getUsername().equalsIgnoreCase(authorDetails.getUsername())
                    && eu.isEnabled())) {
                log.error("Username is already registered: " + authorDetails.getUsername());
                throw new ApiException(MessageConstants.USERNAME_ALREADY_REGISTERED + authorDetails.getUsername());
            } else if (existingUsers.stream().anyMatch(eu -> eu.getMobile().equalsIgnoreCase(authorDetails.getMobile())
                    && eu.isEnabled())) {
                log.error("Mobile Number is already registered: " + authorDetails.getMobile());
                throw new ApiException(MessageConstants.MOBILE_ALREADY_REGISTERED + authorDetails.getMobile());
            } else {
                log.warn("Existing but Disabled User Found.");
                existedIfDisabledUser = Boolean.TRUE;
            }
        }
        return existedIfDisabledUser;
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

    @Override
    public void validateUniqueUserMobileNumber(Long userId, String mobileNumber) {
        log.info("Verifying for existing mobile/paytm number...");
        List<UserRegistrationRepositoryImpl.CustomRowMapper> existingUserNumbers = userRegistrationRepository.findExistingUserWithMobileNumber(userId, mobileNumber);
        if (existingUserNumbers.size() == 0) {
            return;
        }
        List<UserRegistrationRepositoryImpl.CustomRowMapper> collect = existingUserNumbers.stream()
                .filter(r -> r.getUserId().equalsIgnoreCase(String.valueOf(userId)))
                .collect(Collectors.toList());
        if (collect.isEmpty()) {
            log.error("Mobile Number is already registered: " + mobileNumber);
            throw new ApiException(MessageConstants.MOBILE_ALREADY_REGISTERED + mobileNumber);
        }
    }

}

