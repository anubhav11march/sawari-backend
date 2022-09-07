package com.tzs.marshall.constants;

import com.tzs.marshall.bean.DBProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

import static com.tzs.marshall.constants.Constants.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RequestTypeDictionary {
    PASSWORD(DBProperties.properties.getProperty(PASSWORD_REGEX), "resetPassword", DBProperties.properties.getProperty(PASSWORD_LINK), DBProperties.properties.getProperty(SUPPORT_EMAIL), DBProperties.properties.getProperty(PASSWORD_SUBJECT), DBProperties.properties.getProperty(PASSWORD_BODY), "Reset Password"),
    ACCOUNT(DBProperties.properties.getProperty(EMAIL_REGEX), "enableAccount", DBProperties.properties.getProperty(EMAIL_LINK), DBProperties.properties.getProperty(SUPPORT_EMAIL), DBProperties.properties.getProperty(EMAIL_SUBJECT), DBProperties.properties.getProperty(EMAIL_BODY), "Activate Now");

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
