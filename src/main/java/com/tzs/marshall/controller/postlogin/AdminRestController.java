package com.tzs.marshall.controller.postlogin;

import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.PersistentUserRights;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.filesystem.FileBean;
import com.tzs.marshall.filesystem.FileService;
import com.tzs.marshall.service.UserPostLoginService;
import com.tzs.marshall.service.admin.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    private FileService fileService;

    private static final Logger log = LoggerFactory.getLogger(AdminRestController.class);

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
    public PersistentUserDetails updateAuthorRights(@RequestBody PersistentUserRights authorRights,
                                                      @AuthenticationPrincipal PersistentUserDetails adminDetails) {
        adminService.checkAuthorizedAdmin(adminDetails.getUserId());
        if (!Constants.ALLOWED_ROLES.contains(authorRights.getRoleName())) {
            log.error("Unauthorized Operation to create a new Admin.");
            throw new ApiException(MessageConstants.NOT_AUTHORIZED + " You can not create a new Admin.");
        }
        if ("TRUE".equalsIgnoreCase(authorRights.getIs_enable()))
            authorRights.setEnable(Constants.isEnable);
        else authorRights.setEnable(!Constants.isEnable);
        if ("TRUE".equalsIgnoreCase(authorRights.getIs_deleted()))
            authorRights.setDeleted(!Constants.isDeleted);
        else authorRights.setDeleted(Constants.isDeleted);

        return adminService.updateAuthorRights(authorRights);
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
}
