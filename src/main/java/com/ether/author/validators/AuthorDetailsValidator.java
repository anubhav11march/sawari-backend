package com.ether.author.validators;

import com.ether.author.bean.AESHProperties;
import com.ether.author.constants.MessageConstants;
import com.ether.author.constants.RequestTypeDictionary;
import com.ether.author.error.ApiException;

public class AuthorDetailsValidator {

    public static void validateEmail(String email) {
        if (!email.equalsIgnoreCase("") && email.matches(AESHProperties.EMAIL_REGEX)) {
            return;
        }
        throw new ApiException(MessageConstants.INVALID_EMAIL);
    }

    public static void validatePassword(String password) {
        if (!password.equalsIgnoreCase("") && password.matches(AESHProperties.PASSWORD_REGEX)) {
            return;
        }
        throw new ApiException(MessageConstants.INVALID_PASSWORD);
    }
}
