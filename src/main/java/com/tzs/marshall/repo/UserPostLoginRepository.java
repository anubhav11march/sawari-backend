package com.tzs.marshall.repo;

import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.ProfileDetails;

import java.util.List;
import java.util.Map;

public interface UserPostLoginRepository {

    List<PersistentUserDetails> getUserDetailsById(Long userId);

    Map<String, Object> saveOrUpdateUserDetails(PersistentUserDetails authorDetails);

    List<PersistentUserDetails> getUserProfileAndEssentialDetailsById(Long userId);

    int updateProfileDetails(PersistentUserDetails userDetails);

    int updateProfilePhoto(ProfileDetails profileDetails);

    void updateEssentialDetails(PersistentUserDetails userDetails);

    int updateDriverPaytmNumber(Long userId, String paytmNumber);
}



