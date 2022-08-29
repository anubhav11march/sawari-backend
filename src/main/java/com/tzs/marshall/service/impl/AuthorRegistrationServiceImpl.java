package com.tzs.marshall.service.impl;

import com.tzs.marshall.bean.NewsLetterEmailSubs;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.constants.RequestTypeDictionary;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.repo.AuthorRegistrationRepository;
import com.tzs.marshall.service.AuthorRegistrationService;
import com.tzs.marshall.token.ConfirmationToken;
import com.tzs.marshall.token.ConfirmationTokenService;
import com.tzs.marshall.validators.AuthorDetailsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorRegistrationServiceImpl implements AuthorRegistrationService {

    @Autowired
    private AuthorRegistrationRepository authorRegistrationRepository;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoders;

    private static final Logger log = LoggerFactory.getLogger(AuthorRegistrationServiceImpl.class);

    @Override
    public PersistentUserDetails registerNewUser(PersistentUserDetails authorDetails, String url) {
        log.info("Validating Email...");
        AuthorDetailsValidator.validateEmail(authorDetails.getEmail());
        log.info("Email Validated\nValidating Password...");
        AuthorDetailsValidator.validatePassword(authorDetails.getPassword());

        log.info("Checking Existing Users...");
        checkExistingUsers(authorDetails);

        authorDetails.setPassword(bCryptPasswordEncoders.encode(authorDetails.getPassword()));

        //find id of email to put in registration table subs_id then Insert into subscribe_by_email(email) values(?)
        log.info("Check if user has subscribed by email only.");
        List<NewsLetterEmailSubs> newsLetterEmailSubs = authorRegistrationRepository.findSubsIdByEmail(authorDetails.getEmail());
        if (newsLetterEmailSubs.size() == 0) {
            log.info("Saving User's Email to db.");
            newsLetterEmailSubs = authorRegistrationRepository.saveNewsLetterSubsEmail(authorDetails.getEmail());
        }

        //pass the user data to repo for saving
        try {
            log.info("Saving User's details to db.");
            authorRegistrationRepository.saveUserEssentialDetails(authorDetails, newsLetterEmailSubs.stream().findFirst().get().getSubsId());
            authorRegistrationRepository.insertIntoUserBridgeTable(authorDetails.getUsername(), authorDetails.getRoleName(), authorDetails.getTypeName());
            log.info("User Record Inserted.\n" + authorDetails);
        } catch (Exception e) {
            log.warn(String.format("Unable to save user details, Rolling back the user details from db for [%s]", authorDetails));
            authorRegistrationRepository.rollbackRegistration(authorDetails);
            throw new ApiException(e.getMessage());
        }

        log.info("Generating unique token...");
        try {
            confirmationTokenService.tokenHandler(authorDetails.getEmail(), RequestTypeDictionary.ACCOUNT.getReqType(), url);
        } catch (Exception e) {
            log.warn(String.format("Unable to send mail, Rolling back the user details from db for [%s]", authorDetails));
            authorRegistrationRepository.rollbackRegistration(authorDetails);
            throw new ApiException(e.getMessage());
        }
        log.info("User Registered!");
        return authorDetails;
    }

    private void checkExistingUsers(PersistentUserDetails authorDetails) {
        log.info("Verifying for existing user...");
        List<PersistentUserDetails> existingUsers = authorRegistrationRepository.findExistingUsers(authorDetails);
        if (existingUsers.size() > 0) {
            if (existingUsers.stream().anyMatch(eu -> eu.getEmail().equalsIgnoreCase(authorDetails.getEmail()))) {
                log.error("Email is already registered: " + authorDetails.getEmail());
                throw new ApiException(MessageConstants.EMAIL_ALREADY_REGISTERED + authorDetails.getEmail());
            } else if (existingUsers.stream().anyMatch(eu -> eu.getUsername().equalsIgnoreCase(authorDetails.getUsername()))) {
                log.error("Username is already registered: " + authorDetails.getUsername());
                throw new ApiException(MessageConstants.USERNAME_ALREADY_REGISTERED + authorDetails.getUsername());
            } else {
                log.error("Mobile Number is already registered: " + authorDetails.getMobile());
                throw new ApiException(MessageConstants.MOBILE_ALREADY_REGISTERED + authorDetails.getMobile());
            }
        }
    }

    @Override
    public String enableAccountTokenHandler(String token, String reqType) {
        log.info("Enabling User Account...");
        ConfirmationToken confirmationToken = confirmationTokenService.confirmToken(token, reqType);
        if (confirmationToken != null) {
            String email = confirmationToken.getEmail();
            authorRegistrationRepository.enableUser(email);
            log.info("User: " + email + " is now enable.");
            return "success";
        }
        return "fail";
    }

}

