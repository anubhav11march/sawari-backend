package com.tzs.marshall.author.constants;

import com.tzs.marshall.author.bean.AESHProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RequestTypeDictionary {
    PASSWORD(AESHProperties.PASSWORD_REGEX, "resetPassword", AESHProperties.PASSWORD_LINK, AESHProperties.SUPPORT_EMAIL, AESHProperties.PASSWORD_SUBJECT, AESHProperties.PASSWORD_BODY, "Reset Password"),
    ACCOUNT(AESHProperties.EMAIL_REGEX, "enableAccount", AESHProperties.EMAIL_LINK, AESHProperties.SUPPORT_EMAIL, AESHProperties.EMAIL_SUBJECT, AESHProperties.EMAIL_BODY, "Activate Now");

    private String regex;
    private String reqType;
    private String link;
    private String mailFrom;
    private String mailSubject;
    private String mailBody;
    private String mailButton;

    public static RequestTypeDictionary getRequestTypeDictionary(String reqType){
        return Arrays.stream(RequestTypeDictionary.values()).filter(cd->cd.getReqType().equalsIgnoreCase(reqType)).findFirst().orElse(null);
    }
}
