package com.tzs.marshall.service;

import com.sun.istack.NotNull;
import com.tzs.marshall.bean.PersistentUserDetails;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserPostLoginService {

    PersistentUserDetails handleFetchedFullUserDetails(PersistentUserDetails authorDetails);

    List<PersistentUserDetails> updateUserDetails(PersistentUserDetails authorDetails);

    void fetchProfileImageById(Long userId, HttpServletResponse response);

    void updateProfileImage(Long userId, @NotNull MultipartFile profilePhoto);
}
