package com.tzs.marshall.author.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class AESHProperties {
    public static List<String> CATEGORY;
    public static List<String> FILE_FORMATS;
    public static List<String> LANGUAGE;
    public static List<String> NAME_PREFIX;
    public static List<String> QRCODE_UPLOADER;
    public static List<String> STATUS;
    public static List<String> TOPICS;
    public static String ADMIN_EMAIL;
    public static String ALIPAY_QR;
    public static String EMAIL_BODY;
    public static String EMAIL_LINK;
    public static String EMAIL_REGEX;
    public static String EMAIL_SUBJECT;
    public static String PASSWORD_BODY;
    public static String PASSWORD_LINK;
    public static String PASSWORD_REGEX;
    public static String PASSWORD_SUBJECT;
    public static String SUPPORT_EMAIL;
    public static String SUPPORT_EMAIL_PASSWORD;
    public static String UPLOAD_DIR;
    public static String WECHAT_QR;

    private static Map<String, List<String>> propertyList = new HashMap<>();

    private final static Logger log = LoggerFactory.getLogger(AESHProperties.class);

    public AESHProperties(Properties properties) {
        log.info("Persisting AESH Properties...");
        CATEGORY = splitString(properties.getProperty("category"));
        FILE_FORMATS = splitString(properties.getProperty("allowed_file_formats"));
        LANGUAGE = splitString(properties.getProperty("language"));
        NAME_PREFIX = splitString(properties.getProperty("name_prefix"));
        QRCODE_UPLOADER = splitString(properties.getProperty("qrcode_uploader"));
        STATUS = splitString(properties.getProperty("status"));
        TOPICS = splitString(properties.getProperty("topics"));

        ADMIN_EMAIL = properties.getProperty("admin_email");
        ALIPAY_QR = properties.getProperty("alipay_qr");
        EMAIL_BODY = properties.getProperty("email_mail_body");
        EMAIL_LINK = properties.getProperty("email_confirmation_link");
        EMAIL_REGEX = properties.getProperty("email_regex");
        EMAIL_SUBJECT = properties.getProperty("email_subject");
        PASSWORD_BODY = properties.getProperty("password_mail_body");
        PASSWORD_LINK = properties.getProperty("password_reset_link");
        PASSWORD_REGEX = properties.getProperty("password_regex");
        PASSWORD_SUBJECT = properties.getProperty("password_subject");
        SUPPORT_EMAIL = properties.getProperty("support_email");
        SUPPORT_EMAIL_PASSWORD = properties.getProperty("support_email_password");
        UPLOAD_DIR = properties.getProperty("upload_directory");
        WECHAT_QR = properties.getProperty("wechat_qr");

        propertyList.put("CATEGORY", CATEGORY);
        propertyList.put("FORMATS", FILE_FORMATS);
        propertyList.put("LANGUAGE", LANGUAGE);
        propertyList.put("PREFIX", NAME_PREFIX);
        propertyList.put("STATUS", STATUS);
        propertyList.put("TOPICS", TOPICS);
        propertyList.put("ALIPAY_QR", Collections.singletonList(ALIPAY_QR));
        propertyList.put("WECHAT_QR", Collections.singletonList(WECHAT_QR));
        log.info("Persist Successfully. {}", propertyList);
    }

    public static Object getAESHProperty(String property) {
        return propertyList.get(property.toUpperCase());
    }

    public List<String> splitString(String str) {
        return Arrays.stream(str.split(",")).collect(Collectors.toList());
    }
}
