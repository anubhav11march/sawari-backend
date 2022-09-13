package com.tzs.marshall.token;

import com.tzs.marshall.bean.DBProperties;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.constants.RequestTypeDictionary;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.mailsender.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private EmailService emailService;

    private static final Logger log = LoggerFactory.getLogger(ConfirmationTokenServiceImpl.class);

    @Override
    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.saveToken(confirmationToken);
    }

    @Override
    public void updateConfirmsTokenAt(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.updateConfirmedAt(confirmationToken);
    }

    @Override
    public ConfirmationToken getConfirmationToken(String token, String reqType) {
        ConfirmationToken tkn = confirmationTokenRepository.findByToken(token);
        if (tkn == null) {
            log.error("No Token found: " + token + " for ReqType: " + reqType);
            throw new ApiException(MessageConstants.NO_TOKEN);
        }
        return tkn;
    }

    @Override
    public ConfirmationToken confirmToken(String token, String reqType) {
        Timestamp confirmsAt = Timestamp.valueOf(LocalDateTime.now());
        log.info("Verifying whether Token is Expired or Already Confirmed...");
        ConfirmationToken confirmationToken;
        try {
            confirmationToken = getConfirmationToken(token, reqType);
            if (confirmationToken == null) {
                log.error("No Token Found in DB. [Token]:" + token + "[ReqType]:" + reqType);
                throw new ApiException(MessageConstants.NO_TOKEN);
            }
            if (confirmationToken.getConfirmsAt() != null) {
                log.error("Token already confirmed. [Token]: " + token + " [ReqType]:" + reqType);
                throw new ApiException(MessageConstants.TOKEN_VERIFIED_ALREADY);
            } else {
                if (confirmationToken.getExpiresAt().before(confirmsAt)) {
                    log.error("Token has been expired.");
                    throw new ApiException(MessageConstants.TOKEN_EXPIRED);
                }
            }
            log.info("Updating Confirmation of Token in DB at: {}", confirmsAt);
            confirmationToken.setConfirmsAt(confirmsAt);
            updateConfirmsTokenAt(confirmationToken);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
        log.info("Updated in DB.");
        return confirmationToken;
    }

    @Override
    public String tokenHandler(String email, String reqType, String userType, String url) {
        String flag = DBProperties.properties.getProperty("GENERATE_OTP", "Y");
        RequestTypeDictionary requestTypeDictionary = RequestTypeDictionary.getRequestTypeDictionary(reqType);
        String token = generateAndSaveToken(email, reqType, userType, flag);
        log.info("Token generated: " + token);
        if ("Y".equalsIgnoreCase(flag)) {
            if (Constants.DRIVER.equalsIgnoreCase(userType)) {
                log.info("Sending 6 digit OTP to the driver...");
                emailService.sendOTPToMobile(email, token);
                log.info("6 digit OTP Send successfully.");
            } else if (Constants.USER.equalsIgnoreCase(userType)) {
                log.info("Sending 6 digit OTP to the user...");
                emailService.sendOTPToEmail(email, token, requestTypeDictionary);
                log.info("6 digit OTP Send successfully.");
            } else {
                log.info("Sending confirmation link to the admin...");
                emailService.sendConfirmationEmail(email, token, url, requestTypeDictionary);
                log.info(String.format("%s Request Email Send successfully.", requestTypeDictionary.getReqType()));
            }
        } else {
            log.info("Sending confirmation link to the admin...");
            emailService.sendConfirmationEmail(email, token, url, requestTypeDictionary);
            log.info(String.format("%s Request Email Send successfully.", requestTypeDictionary.getReqType()));
        }
        return token;
    }

    @Override
    public String resendTokenHandler(String token, String reqType, String url) {
        log.info("Fetching Email from old token...");
        ConfirmationToken confirmationToken = getConfirmationToken(token, reqType);
        String newToken = null;
        if (confirmationToken != null) {
            String contact = confirmationToken.getEmail();
            log.info("Email Found... {}", contact);
            newToken = tokenHandler(contact, reqType, confirmationToken.getUserType(), url);
        }
        return newToken;
    }

    private String generateAndSaveToken(String email, String reqType, String userType, String flag) {
        log.info("Generating Random Token String...");
        String token = "";
        if ("Y".equalsIgnoreCase(flag)) {
            int randomPin = (int) (Math.random() * 900000) + 100000;
            token = String.valueOf(randomPin);
        } else {
            token = UUID.randomUUID().toString();
        }

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                reqType,
                userType,
                email,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now().plusMinutes(15))
        );
        log.info("Saving New Token to DB...{}", token);
        saveConfirmationToken(confirmationToken);
        return token;
    }


}
