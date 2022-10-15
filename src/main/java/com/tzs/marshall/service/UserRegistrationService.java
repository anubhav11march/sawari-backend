package com.tzs.marshall.service;

import com.tzs.marshall.bean.ProfileDetails;
import com.tzs.marshall.bean.PersistentUserDetails;

public interface UserRegistrationService {

    PersistentUserDetails registerUser(PersistentUserDetails userDetails, String url);

    ProfileDetails registerDriver(ProfileDetails userDetails);

    String enableAccountTokenHandler(String token, String reqType);
}
