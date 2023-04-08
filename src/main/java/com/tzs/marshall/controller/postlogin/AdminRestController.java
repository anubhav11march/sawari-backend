package com.tzs.marshall.controller.postlogin;

import com.tzs.marshall.InitProperties;
import com.tzs.marshall.bean.DBProperties;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.PersistentUserRights;
import com.tzs.marshall.bean.UserRideEarnings;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
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
    public List<PersistentUserDetails> getUsersRights() {
        return adminService.getUsersRights();
    }

    @RequestMapping(value = "/user/rights", method = RequestMethod.GET)
    public PersistentUserDetails getUserRights(@RequestParam("userId") String userId) {
        return adminService.getUserRightsById(Long.parseLong(userId));
    }

    @RequestMapping(value = "/user/rights", method = RequestMethod.PUT)
    public PersistentUserDetails updateUserRights(@RequestBody PersistentUserRights userRights,
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

        return adminService.updateUserRights(userRights);
    }

    //All Users' Profile
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public List<PersistentUserDetails> getAllUsers(@RequestParam Map<String, String> allRequestParams, @RequestBody(required = false) Map filters) {
        String role = allRequestParams.get("role");
        int after = allRequestParams.get("after") != null ? Integer.parseInt(allRequestParams.get("after")) : 0;
        int limit = allRequestParams.get("limit") != null ? Integer.parseInt(allRequestParams.get("limit")) : 10;
        return adminService.getAllUsersByRole(role, after, limit, filters);
    }

    //Single User Profile Details
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public PersistentUserDetails getUserDetails(@RequestParam("userId") String userId) {
        return adminService.getUserDetailsById(Long.parseLong(userId));
    }

    //driver's details -> id, name, phone, vehicle no, today earning, today commission -> personal info, vehicle info, total earning
    //customer's details -> id, name, phone, email
    @RequestMapping(value = "/users-rides", method = RequestMethod.POST)
    public List<UserRideEarnings> getAllUsersAndRides(@RequestParam Map<String, String> allRequestParams, @RequestBody(required = false) Map filters) {
        String role = allRequestParams.get("role");
        int after = allRequestParams.get("after") != null ? Integer.parseInt(allRequestParams.get("after")) : 0;
        int limit = allRequestParams.get("limit") != null ? Integer.parseInt(allRequestParams.get("limit")) : 10;
        return adminService.getAllUsersAndRidesByRole(role, after, limit, filters);
    }

    @RequestMapping(value = "/user-ride", method = RequestMethod.GET)
    public UserRideEarnings getUserAndRideById(@RequestParam("userId") String userId) {
        return adminService.getUserAndRideById(userId);
    }

    @RequestMapping(value = "/qrcode/upload", method = RequestMethod.POST)
    public void uploadQRCode(@RequestParam("name") String qrCodeName, @RequestParam("qrCode") MultipartFile qrCode,
                                     @AuthenticationPrincipal PersistentUserDetails authorDetails) {
        if (!Constants.ADMIN.equals(authorDetails.getRoleName()))
            throw new ApiException(MessageConstants.NOT_AUTHORIZED);
        adminService.checkAuthorizedAdmin(authorDetails.getUserId());
        String path = subscriptionService.qrCodeHelper(authorDetails.getUserId(), qrCodeName, qrCode);
        subscriptionService.uploadQR(qrCodeName, path);
        successMessage = MessageConstants.QR_UPLOADED;
        new DBProperties(initProperties.getDBProperties());
    }

    @RequestMapping(value = "/qrcode/update", method = RequestMethod.POST)
    public void updateQRCode(@RequestParam("name") String qrCodeName, @RequestParam("qrCode") MultipartFile qrCode,
                                     @AuthenticationPrincipal PersistentUserDetails authorDetails) {
        if (!Constants.ADMIN.equals(authorDetails.getRoleName()))
            throw new ApiException(MessageConstants.NOT_AUTHORIZED);
        adminService.checkAuthorizedAdmin(authorDetails.getUserId());
        String path = subscriptionService.qrCodeHelper(authorDetails.getUserId(), qrCodeName, qrCode);
        subscriptionService.updateQR(qrCodeName, path);
        successMessage = MessageConstants.QR_UPDATED;
        new DBProperties(initProperties.getDBProperties());
    }
}
