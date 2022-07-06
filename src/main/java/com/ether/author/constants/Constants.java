package com.ether.author.constants;

import java.io.File;
import java.util.*;

public interface Constants {
    String ROLE_AUTHOR = "AUTHOR";
    String ROLE_ADMIN = "ADMIN";
    String ROLE_EDITOR = "EDITOR";
    String ROLE_PRE_EDITOR = "PRE-EDITOR";
    String TYPE_REGISTERED = "REGISTERED";
    List<String> ADMINS = Arrays.asList(ROLE_ADMIN, ROLE_EDITOR, ROLE_PRE_EDITOR);
    Boolean isDeleted = Boolean.FALSE;
    Boolean isEnable = Boolean.TRUE;
    Map<String, Integer> ORDER_STATUS = new HashMap<>();
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
    String QRCODE_DIR = "qrcode";
    String IMAGE_TYPE = "image/jpeg,image/jpg,image/png";
    String BASE_PATH = "src/main/resources/static";
}
