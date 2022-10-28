package com.tzs.marshall.controller.postlogin;

import com.tzs.marshall.bean.AESHServicePlan;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.filesystem.FileHelper;
import com.tzs.marshall.service.AESHSubscriptionService;
import com.tzs.marshall.service.UserPostLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping({"/user", "/admin", "driver"})
public class PostLoginRestController {

    @Autowired
    private UserPostLoginService userPostLoginService;
    @Autowired
    private AESHSubscriptionService aeshSubscriptionService;
    @Autowired
    private FileHelper fileHelper;

    private static final Logger log = LoggerFactory.getLogger(PostLoginRestController.class);

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    PersistentUserDetails getProfile(@AuthenticationPrincipal PersistentUserDetails userDetails) {
        userDetails = userPostLoginService.handleFetchedFullUserDetails(userDetails);
        log.info("Updating complete profile details in Security context...{}", userDetails);
        Authentication authentication = new PreAuthenticatedAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return userDetails;
    }

    @RequestMapping(value = "/profile/image", method = RequestMethod.GET)
    public void getImagePath(@AuthenticationPrincipal PersistentUserDetails userDetails,
                             HttpServletResponse response) {
        userPostLoginService.fetchProfileImageById(userDetails.getUserId(), response);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.PUT, consumes = {"multipart/form-data"})
    PersistentUserDetails driverProfileUpdate(@ModelAttribute PersistentUserDetails userDetails,
                                              @AuthenticationPrincipal PersistentUserDetails driver) {
        userDetails.setUserId(driver.getUserId());
        return userPostLoginService.updateDriverDetails(userDetails);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.PUT, consumes = {"application/json"})
    PersistentUserDetails userProfileUpdate(@RequestBody PersistentUserDetails userDetails) {
        return userPostLoginService.updateUserDetails(userDetails);
    }

    @RequestMapping(value = "/profile/image", method = RequestMethod.PUT, consumes = "multipart/form-data")
    public void updateProfileImage(@RequestPart MultipartFile profilePhoto,
                                   @AuthenticationPrincipal PersistentUserDetails userDetails,
                                   HttpServletResponse response) {
        userPostLoginService.updateProfileImage(userDetails.getUserId(), profilePhoto);
        userPostLoginService.fetchProfileImageById(userDetails.getUserId(), response);
    }

    @RequestMapping(value = "/plans", method = RequestMethod.GET)
    List<AESHServicePlan> getAllServicePlans() {
        return aeshSubscriptionService.fetchAllServicePlans();
    }

    @RequestMapping(value = "/image/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> getImageAsResource(@RequestParam("path") String path) {
        Resource resource = fileHelper.loadFileAsResourceHelper(path);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @RequestMapping(value = "/qrCode", method = RequestMethod.GET)
    public String getImagePath(@RequestParam("qrCodeType") String qrCodeType, HttpServletResponse response) {
        String path = aeshSubscriptionService.fetchQrCode(qrCodeType);
        path = path.replaceAll("\\\\", "/").replaceAll(Constants.BASE_PATH, "");
        return Paths.get(path).toString();
    }
}
