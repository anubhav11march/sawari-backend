package com.tzs.marshall.service;

import com.sun.istack.NotNull;
import com.tzs.marshall.bean.PersistentUserDetails;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface UserPostLoginService {

    PersistentUserDetails handleFetchedFullUserDetails(PersistentUserDetails authorDetails);

    PersistentUserDetails updateUserDetails(PersistentUserDetails authorDetails);

    void fetchProfileImageById(Long userId, HttpServletResponse response);

    void updateProfileImage(Long userId, @NotNull MultipartFile profilePhoto);

    PersistentUserDetails updateDriverDetails(PersistentUserDetails userDetails);

    Map<String, String> getImageByTypeNameAndId(String imageType, String option, Long userId);
}
