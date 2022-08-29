package com.tzs.marshall.validators;

import com.tzs.marshall.bean.DBProperties;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;

import static com.tzs.marshall.constants.Constants.*;

public class AuthorDetailsValidator {

    public static void validateEmail(String email) {
        if (!email.equalsIgnoreCase("") && email.matches(DBProperties.properties.getProperty(EMAIL_REGEX))) {
            return;
        }
        throw new ApiException(MessageConstants.INVALID_EMAIL);
    }

    public static void validatePassword(String password) {
        if (!password.equalsIgnoreCase("") && password.matches(DBProperties.properties.getProperty(PASSWORD_REGEX))) {
            return;
        }
        throw new ApiException(MessageConstants.INVALID_PASSWORD);
    }
}
