package com.ether.author.controller.postlogin;

import com.ether.author.bean.AESHServicePlan;
import com.ether.author.bean.PersistentAuthorDetails;
import com.ether.author.constants.Constants;
import com.ether.author.filesystem.FileHelper;
import com.ether.author.service.AESHSubscriptionService;
import com.ether.author.service.AuthorPostLoginService;
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

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping({"/author", "/admin"})
public class PostLoginRestController {

    @Autowired
    private AuthorPostLoginService authorPostLoginService;
    @Autowired
    private AESHSubscriptionService aeshSubscriptionService;
    @Autowired
    private FileHelper fileHelper;

    private static final Logger log = LoggerFactory.getLogger(PostLoginRestController.class);

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    PersistentAuthorDetails getProfile(@AuthenticationPrincipal PersistentAuthorDetails authorDetails) {
        authorDetails = authorPostLoginService.handleFetchedFullAuthorDetails(authorDetails);
        log.info("Updating complete profile details in Security context...{}", authorDetails);
        Authentication authentication = new PreAuthenticatedAuthenticationToken(authorDetails, authorDetails.getPassword(), authorDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authorDetails;
    }

    @RequestMapping(value = "/profile/update", method = RequestMethod.PUT)
    List<PersistentAuthorDetails> userRegistration(@RequestBody PersistentAuthorDetails authorDetails) {
        return authorPostLoginService.updateAuthorDetails(authorDetails);
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
