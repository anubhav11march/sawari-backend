package com.tzs.marshall.author.token;

import com.tzs.marshall.author.constants.MessageConstants;
import com.tzs.marshall.author.constants.RequestTypeDictionary;
import com.tzs.marshall.author.error.ApiException;
import com.tzs.marshall.author.mailsender.EmailService;
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
        ConfirmationToken tkn = confirmationTokenRepository.findByToken(token, reqType);
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
    public String tokenHandler(String email, String reqType, String url) {
        String token = generateAndSaveToken(email, reqType);
        log.info("Token generated: " + token);
        RequestTypeDictionary requestTypeDictionary = RequestTypeDictionary.getRequestTypeDictionary(reqType);
        log.info("Sending confirmation link to the user...");
        emailService.sendConfirmationEmail(email, token, url, requestTypeDictionary);
        log.info(String.format("%s Request Email Send successfully.", requestTypeDictionary.getReqType()));
        return token;
    }

    @Override
    public String resendTokenHandler(String token, String reqType, String url) {
        log.info("Fetching Email from old token...");
        ConfirmationToken confirmationToken = getConfirmationToken(token, reqType);
        String email = null;
        if (confirmationToken != null) {
            email = confirmationToken.getEmail();
        }
        log.info("Email Found... {}", email);
        return tokenHandler(email, reqType, url);
    }

    private String generateAndSaveToken(String email, String reqType) {
        log.info("Generating Random Token String...");
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                reqType,
                email,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now().plusMinutes(15))
        );
        log.info("Saving New Token to DB...{}", token);
        saveConfirmationToken(confirmationToken);
        return token;
    }


}
