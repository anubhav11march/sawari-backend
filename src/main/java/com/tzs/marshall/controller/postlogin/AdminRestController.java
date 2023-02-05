package com.tzs.marshall.controller.postlogin;

import com.tzs.marshall.InitProperties;
import com.tzs.marshall.bean.DBProperties;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.PersistentUserRights;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.filesystem.FileBean;
import com.tzs.marshall.filesystem.FileService;
import com.tzs.marshall.service.SubscriptionService;
import com.tzs.marshall.service.UserPostLoginService;
import com.tzs.marshall.service.admin.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/admin")
public class AdminRestController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UserPostLoginService userPostLoginService;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private InitProperties initProperties;
    @Autowired
    private FileService fileService;

    private static final Logger log = LoggerFactory.getLogger(AdminRestController.class);
    String errorMessage = "";
    String successMessage = "";

    //Author's Rights
    @RequestMapping(value = "/users/rights", method = RequestMethod.GET)
    public List<PersistentUserDetails> getAuthorsRights() {
        return adminService.getAuthorsRights();
    }

    @RequestMapping(value = "/user/rights", method = RequestMethod.GET)
    public PersistentUserDetails getAuthorRights(@RequestParam("userId") String userId) {
        return adminService.getAuthorRightsById(Long.parseLong(userId));
    }

    @RequestMapping(value = "/user/rights", method = RequestMethod.PUT)
    public PersistentUserDetails updateAuthorRights(@RequestBody PersistentUserRights userRights,
                                                      @AuthenticationPrincipal PersistentUserDetails adminDetails) {
        adminService.checkAuthorizedAdmin(adminDetails.getUserId());
        if (!Constants.ALLOWED_ROLES.contains(userRights.getRoleName())) {
            log.error("Unauthorized Operation to create a new Admin.");
            throw new ApiException(MessageConstants.NOT_AUTHORIZED + " You can not create a new Admin.");
        }
        if ("TRUE".equalsIgnoreCase(userRights.getIs_enable()))
            userRights.setEnable(Constants.isEnable);
        else userRights.setEnable(!Constants.isEnable);
        if ("TRUE".equalsIgnoreCase(userRights.getIs_deleted()))
            userRights.setDeleted(!Constants.isDeleted);
        else userRights.setDeleted(Constants.isDeleted);

        return adminService.updateAuthorRights(userRights);
    }

    //Authors' Profile
    //complete
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<PersistentUserDetails> getAllUsers(@RequestParam("role") String role) {
        return adminService.getAllCompleteProfileUsersByRole(role);
    }

    //Single Author Profile Details
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public PersistentUserDetails getUserDetails(@RequestParam("userId") String userId) {
        return adminService.getAuthorDetailsById(Long.parseLong(userId));
    }

    //Incomplete
    @RequestMapping(value = "/incomplete/users", method = RequestMethod.GET)
    public List<PersistentUserDetails> getUsers(@RequestParam("role") String role) {
        return adminService.getAllIncompleteProfileUsersByRole(role);
    }

    //Authors' Files
    @RequestMapping(value = "/users/files", method = RequestMethod.GET)
    public List<FileBean> getAllFiles(@RequestParam("role") String role) {
        return fileService.fetchAllFilesByUser(role);
    }

    @RequestMapping(value = "/user/files", method = RequestMethod.GET)
    public List<FileBean> getAllFilesByUser(@RequestParam("userId") String userId,
                                            @RequestParam("role") String role) {
        return fileService.findFilesHandler(Long.parseLong(userId), role);
    }

    @RequestMapping(value = "/user/file", method = RequestMethod.GET)
    public FileBean getAuthorFile(@RequestParam Map<String, String> allRequestParams) {
        return fileService.findFileByIdAndRole(Long.parseLong(allRequestParams.get("userId")), Long.parseLong(allRequestParams.get("fileId")), allRequestParams.get("role"));
    }

    //Admin-Report for Authors
    @RequestMapping(value = "/user/file/reports", method = RequestMethod.GET)
    public List<FileBean> getEditorReportsForFile(@RequestParam Map<String, String> allRequestParams) {
        return fileService.findReportsForAuthorFile(Long.parseLong(allRequestParams.get("userId")), Long.parseLong(allRequestParams.get("fileId")));
    }

    @RequestMapping(value = "/user/file/report", method = RequestMethod.GET)
    public FileBean getEditorReportForFile(@RequestParam Map<String, String> allRequestParams) {
        return fileService.findReportForAuthorFile(Long.parseLong(allRequestParams.get("userId")),
                Long.parseLong(allRequestParams.get("fileId")), Long.parseLong(allRequestParams.get("reportId")));
    }

    @RequestMapping(value = "/user/reports", method = RequestMethod.GET)
    public List<FileBean> getEditorReportsForAuthor(@RequestParam Map<String, String> allRequestParams) {
        return fileService.findAllReportsForAuthor(Long.parseLong(allRequestParams.get("userId")));
    }

    @RequestMapping(value = "/qrcode/upload", method = RequestMethod.POST)
    public void uploadQRCode(@RequestParam("name") String qrCodeName, @RequestParam("qrCode") MultipartFile qrCode,
                                     @AuthenticationPrincipal PersistentUserDetails authorDetails) {
        if (!Constants.ADMIN.equals(authorDetails.getRoleName()))
            throw new ApiException(MessageConstants.NOT_AUTHORIZED);
        adminService.checkAuthorizedAdmin(authorDetails.getUserId());
        try {
            String path = subscriptionService.qrCodeHelper(authorDetails.getUserId(), qrCodeName, qrCode);
            subscriptionService.uploadQR(qrCodeName, path);
            successMessage = MessageConstants.QR_UPLOADED;
        } catch (ApiException | IOException e) {
            log.error(e.getLocalizedMessage() + e.getCause());
            errorMessage = e.getMessage();
        }
        new DBProperties(initProperties.getDBProperties());
    }

    @RequestMapping(value = "/qrcode/update", method = RequestMethod.POST)
    public void updateQRCode(@RequestParam("name") String qrCodeName, @RequestParam("qrCode") MultipartFile qrCode,
                                     @AuthenticationPrincipal PersistentUserDetails authorDetails) {
        if (!Constants.ADMIN.equals(authorDetails.getRoleName()))
            throw new ApiException(MessageConstants.NOT_AUTHORIZED);
        adminService.checkAuthorizedAdmin(authorDetails.getUserId());
        try {
            String path = subscriptionService.qrCodeHelper(authorDetails.getUserId(), qrCodeName, qrCode);
            subscriptionService.updateQR(qrCodeName, path);
            successMessage = MessageConstants.QR_UPDATED;
        } catch (ApiException | IOException e) {
            log.error(e.getLocalizedMessage());
            errorMessage = e.getMessage();
        }
        new DBProperties(initProperties.getDBProperties());
    }
}
