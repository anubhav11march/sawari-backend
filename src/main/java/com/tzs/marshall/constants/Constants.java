package com.tzs.marshall.constants;

import java.io.File;
import java.util.*;

public interface Constants {
    String USER = "USER";
    String ADMIN = "ADMIN";
    String DRIVER = "DRIVER";
    String ROLE_PRE_EDITOR = "PRE-EDITOR";
    String TYPE_REGISTERED = "REGISTERED";
    List<String> ADMINS = Arrays.asList(ADMIN, DRIVER, ROLE_PRE_EDITOR);
    List<String> ALLOWED_ROLES = Arrays.asList(USER, DRIVER, ROLE_PRE_EDITOR);
    String SCHEMA = "marshall_service";
    Boolean isDeleted = Boolean.FALSE;
    Boolean isEnable = Boolean.TRUE;
    Map<String, Integer> ORDER_STATUS = new HashMap<>();
    String OPEN = "OPEN";
    String CANCEL = "CANCEL";
    String BOOK = "BOOK";
    String START = "START";
    String REJECT = "REJECT";
    String ACCEPT = "ACCEPT";
    String CLOSE = "CLOSE";
    String AVAILABLE = "AVAILABLE";
    String ON_DUTY = "ON_DUTY";
    String OFF_DUTY = "OFF_DUTY";
    String NOT_SERVED = "NOT_SERVED";
    String INITIATED = "INITIATED";
    String CREATED = "CREATED";
    String PENDING = "PENDING";
    String PLACED = "PLACED";
    String PAID = "PAID";
    String APPROVED = "APPROVED";
    String REJECTED = "REJECTED";
    String CANCELLED = "CANCELLED";
    String EXPIRED = "EXPIRED";
    Integer ORDER_PRIORITY = 5;
    String TRANSACTION_DIR = "transactions" + File.separator + "screenshot";
    String QRCODE_DIR = "images/qrcodes";
    String IMAGE_TYPE = "image/jpeg,image/jpg,image/png";
    String BASE_PATH = "/var/marshall/docs/";
    String CONFIG_FILE = "config/";
    String DISCOUNT_CONFIG_FILE = "discount_config.json";
    String EDITOR_QUERY = " FROM\n" +
            "    (SELECT user_id, first_name, middle_name, last_name, phone, mobile, email, alternate_email, role_name, type_name, permission_name, is_deleted, is_enable FROM marshall_service.view_user_details \n" +
            "\tunion \n" +
            "\tSELECT user_id, first_name, middle_name, last_name, phone, mobile, email, alternate_email, role_name, type_name, permission_name, is_deleted, is_enable from marshall_service.view_incomplete_user_details) ur,\n" +
            "    marshall_service.content_report cr,\n" +
            "    marshall_service.content c\n" +
            "WHERE\n" +
            "        ur.user_id = cr.author_user_id\n" +
            "        AND c.file_user_id = ur.user_id\n" +
            "        AND c.author_file_id = cr.author_file_id ";
    String PASSWORD_REGEX = "PASSWORD_REGEX";
    String EMAIL_REGEX = "EMAIL_REGEX";
    String PASSWORD_LINK = "PASSWORD_RESET_LINK";
    String EMAIL_LINK = "EMAIL_CONFIRMATION_LINK";
    String SUPPORT_EMAIL = "SUPPORT_EMAIL";
    String PASSWORD_SUBJECT = "PASSWORD_SUBJECT";
    String EMAIL_SUBJECT = "EMAIL_SUBJECT";
    String PASSWORD_BODY = "PASSWORD_MAIL_BODY";
    String EMAIL_BODY = "EMAIL_MAIL_BODY";
    String ADMIN_EMAIL = "ADMIN_EMAIL";
    String ADMIN_EMAIL_PASSWORD = "ADMIN_EMAIL_PASSWORD";
    String CONTACT_US_EMAIL = "CONTACT_US_EMAIL";
    String QRCODE_UPLOADER = "QRCODE_UPLOADER";
    String UPLOAD_DIR = "UPLOAD_DIRECTORY";
    String FILE_FORMATS = "ALLOWED_FILE_FORMATS";
    String TWILIO_ACCOUNT_SID = "TWILIO_ACCOUNT_SID";
    String TWILIO_AUTH_TOKEN = "TWILIO_AUTH_TOKEN";
    String SUPPORT_MOBILE_NUMBER = "SUPPORT_MOBILE_NUMBER";
}
