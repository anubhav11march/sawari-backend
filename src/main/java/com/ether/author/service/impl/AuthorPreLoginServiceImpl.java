package com.ether.author.service.impl;

import com.ether.author.bean.PersistentAuthorDetails;
import com.ether.author.constants.MessageConstants;
import com.ether.author.error.ApiException;
import com.ether.author.mailsender.EmailBean;
import com.ether.author.mailsender.EmailService;
import com.ether.author.repo.AuthorPreLoginRepository;
import com.ether.author.service.AuthorPreLoginService;
import com.ether.author.token.ConfirmationToken;
import com.ether.author.token.ConfirmationTokenService;
import com.ether.author.validators.AuthorDetailsValidator;
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
public class AuthorPreLoginServiceImpl implements AuthorPreLoginService {

    @Autowired
    private AuthorPreLoginRepository authorPreLoginRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoders;

    private static final Logger log = LoggerFactory.getLogger(AuthorPreLoginServiceImpl.class);


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("loading valid user details by Username... {}", userName);
        PersistentAuthorDetails user = handleFetchedValidUser(userName);
        log.info("Valid User found: {}", user);
        return user;
    }

    @Override
    public PersistentAuthorDetails handleFetchedValidUser(String userName) {
        PersistentAuthorDetails userDetails;
        try {
            log.info("Fetching User Details from DB...");
            Map<String, Object> validUser = authorPreLoginRepository.getValidUser(userName);
            List<?> userDetailsOut = (List<?>) validUser.get("validUser");
            if(userDetailsOut.size()==0){
                throw new UsernameNotFoundException(MessageConstants.NO_USER + userName);
            }
            log.info("User found, Parsing into Json to Bean...");
            userDetails = getPersistentAuthorDetails(userDetailsOut);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(e.getMessage());
        }
        return userDetails;
    }

    private PersistentAuthorDetails getPersistentAuthorDetails(List<?> userDetailsOut) {
        PersistentAuthorDetails userDetails;
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(userDetailsOut.stream().findFirst().get());
            userDetails = gson.fromJson(jsonElement, PersistentAuthorDetails.class);
        } catch (Exception e) {
            log.error("Parsing Error: {}", e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
        return userDetails;
    }

    @Override
    public String handleOtpGeneration(String email) {
        int randomPin = (int) (Math.random() * 900000) + 100000;
        String otp = String.valueOf(randomPin);
        log.info("Sending otp email...");
        emailService.send(new EmailBean(email, "OTP Verfication", "Your 6 digits OTP to reset password: " + otp));
        authorPreLoginRepository.saveOtp(email, otp);
        log.info("OTP: " + otp + " sent and saved to db with mailId: " + email);
        return otp;
    }

    @Override
    public String handleOtpVerification(String otp) {
        Map<String, String> validOtpAndEmail = authorPreLoginRepository.getValidOtpAndEmail(otp);
        log.info(validOtpAndEmail.toString());
        return null;
    }

    @Override
    public String resetPasswordHandler(String token, String reqType, String password) {
        ConfirmationToken confirmationToken = confirmationTokenService.getConfirmationToken(token, reqType);
        if (confirmationToken != null) {
            AuthorDetailsValidator.validatePassword(password);
            password = bCryptPasswordEncoders.encode(password);
            int i = authorPreLoginRepository.updatePassword(confirmationToken.getEmail(), password);
            if (i > 0) return "success";
        }
        return "fail";
    }

}

