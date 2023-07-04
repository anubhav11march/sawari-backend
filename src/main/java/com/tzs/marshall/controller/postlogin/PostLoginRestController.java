package com.tzs.marshall.controller.postlogin;

import com.tzs.marshall.bean.DBProperties;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.filesystem.FileHelper;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping({"/user", "/admin", "driver"})
public class PostLoginRestController {

    @Autowired
    private UserPostLoginService userPostLoginService;
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
    PersistentUserDetails driverProfileUpdate(@RequestParam Map<String, MultipartFile> allMultipartParam, @ModelAttribute PersistentUserDetails userDetails,
                                              @AuthenticationPrincipal PersistentUserDetails driver, HttpServletRequest request) {
        userDetails.setUserId(driver.getUserId());
        log.info("Request Received: "+ request.toString());
        log.info("allRequestParam: " + allMultipartParam);
        userDetails.setRickshawFrontPhoto(allMultipartParam.get("rickshawFrontPhoto"));
        userDetails.setRickshawBackPhoto(allMultipartParam.get("rickshawBackPhoto"));
        userDetails.setRickshawSidePhoto(allMultipartParam.get("rickshawSidePhoto"));
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

    @RequestMapping(value = "/image/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> getImageAsResource(@RequestParam("path") String path) {
        Resource resource = fileHelper.loadFileAsResourceHelper(path);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public void getImagePath(@RequestParam Map<String, String> allRequestParams,
                             HttpServletResponse response,
                             @AuthenticationPrincipal PersistentUserDetails userDetails) {
        String dirPathI;
        String imageName;
        String imageType = allRequestParams.get("imageType");
        String option = allRequestParams.get("option");
        if ("qrcode".equalsIgnoreCase(imageType)) {
            imageName = option.toUpperCase();
            dirPathI = DBProperties.properties.getProperty(imageName);
        } else {
            Map<String, String> imageByTypeNameAndId = userPostLoginService.getImageByTypeNameAndId(imageType, option, userDetails.getUserId());
            imageName = imageByTypeNameAndId.get("name");
            dirPathI = imageByTypeNameAndId.get("path");
        }
        log.info("Serving image to client");
        fileHelper.serveImageInResponse(imageName, response, dirPathI);
    }
}
