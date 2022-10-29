package com.tzs.marshall.service;

import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.ProfileDetails;

public interface UserRegistrationService {

    PersistentUserDetails registerUser(PersistentUserDetails userDetails, String url);

    ProfileDetails registerDriver(ProfileDetails userDetails);

    String enableAccountTokenHandler(String token, String reqType);

    void validateUniqueUserMobileNumber(Long userId, String mobileNumber);
}
