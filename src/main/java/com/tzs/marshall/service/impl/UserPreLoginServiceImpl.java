package com.tzs.marshall.service.impl;

import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.repo.UserPreLoginRepository;
import com.tzs.marshall.service.UserPreLoginService;
import com.tzs.marshall.token.ConfirmationToken;
import com.tzs.marshall.token.ConfirmationTokenService;
import com.tzs.marshall.validators.UserDetailsValidator;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserPreLoginServiceImpl implements UserPreLoginService {

    @Autowired
    private UserPreLoginRepository userPreLoginRepository;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoders;

    private static final Logger log = LoggerFactory.getLogger(UserPreLoginServiceImpl.class);


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("loading valid user details by Username... {}", userName);
        PersistentUserDetails user = handleFetchedValidUser(userName);
        log.info("Valid User found: {}", user);
        return user;
    }

    @Override
    public PersistentUserDetails handleFetchedValidUser(String userName) {
        PersistentUserDetails userDetails;
        try {
            log.info("Fetching User Details from DB...");
            Map<String, Object> validUser = userPreLoginRepository.getValidUser(userName);
            List<?> userDetailsOut = (List<?>) validUser.get("validUser");
            if(userDetailsOut.size()==0){
                throw new UsernameNotFoundException(MessageConstants.NO_USER + userName);
            }
            log.info("User found, Parsing into Json to Bean...");
            userDetails = getPersistentUserDetails(userDetailsOut);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(e.getMessage());
        }
        return userDetails;
    }

    private PersistentUserDetails getPersistentUserDetails(List<?> userDetailsOut) {
        PersistentUserDetails userDetails;
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(userDetailsOut.stream().findFirst().get());
            userDetails = gson.fromJson(jsonElement, PersistentUserDetails.class);
        } catch (Exception e) {
            log.error("Parsing Error: {}", e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
        return userDetails;
    }

    @Override
    public String generateAndSaveOTP(String mobileNumber) {
        int randomPin = (int) (Math.random() * 900000) + 100000;
        String otp = String.valueOf(randomPin);
        log.info("Sending otp email...");
        userPreLoginRepository.saveOtp(mobileNumber, otp);
        log.info("OTP: " + otp + " saved to db for mobile number: " + mobileNumber);
        return otp;
    }

    @Override
    public String handleOtpVerification(String otp) {
        Map<String, String> validOtpAndEmail = userPreLoginRepository.getValidOtpAndEmail(otp);
        log.info(validOtpAndEmail.toString());
        return null;
    }

    @Override
    public String resetPasswordHandler(String token, String reqType, String password) {
        ConfirmationToken confirmationToken = confirmationTokenService.getConfirmationToken(token, reqType);
        if (confirmationToken != null) {
            UserDetailsValidator.validatePassword(password);
            password = bCryptPasswordEncoders.encode(password);
            int i = userPreLoginRepository.updatePassword(confirmationToken.getEmail(), password);
            if (i > 0) return "success";
        }
        return "fail";
    }

}

