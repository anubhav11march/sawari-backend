package com.tzs.marshall.controller.postlogin;

import com.tzs.marshall.InitProperties;
import com.tzs.marshall.bean.*;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.service.admin.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private InitProperties initProperties;

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
    public List<PersistentUserDetails> getAllUsers(@RequestParam Map<String, String> allRequestParams,
            @RequestBody(required = false) Map filters) {
        String role = allRequestParams.get("role");
        // int after = allRequestParams.get("after") != null ?
        // Integer.parseInt(allRequestParams.get("after")) : 0;
        // int limit = allRequestParams.get("limit") != null ?
        // Integer.parseInt(allRequestParams.get("limit")) : 10;
        return adminService.getAllUsersByRole(role, filters);
    }

    //Single User Profile Details
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public PersistentUserDetails getUserDetails(@RequestParam("userId") String userId) {
        return adminService.getUserDetailsById(Long.parseLong(userId));
    }

    //driver's details -> id, name, phone, vehicle no, today earning, today commission -> personal info, vehicle info, total earning
    //customer's details -> id, name, phone, email
    @RequestMapping(value = "/users-earnings", method = RequestMethod.POST)
    public List<UserRideEarnings> getAllUsersAndEarnings(@RequestParam Map<String, String> allRequestParams, @RequestBody(required = false) Map filters) {
        String role = allRequestParams.get("role");
        return adminService.getAllUsersAndEarningsByRole(role,  filters);
    }

    @RequestMapping(value = "/user-earning", method = RequestMethod.GET)
    public UserRideEarnings getUserAndEarningById(@RequestParam("userId") String userId) {
        return adminService.getUserAndEarningById(userId);
    }

    //rides -> id, customerName, driverName, vehicleNo, source, destination, fare, date, status
    @RequestMapping(value = "/users-rides", method = RequestMethod.POST)
    public List<RideRequest> getAllRides(@RequestParam Map<String, String> allRequestParams, @RequestBody(required = false) Map filters) {
//        String role = allRequestParams.get("role");
        int after = allRequestParams.get("after") != null ? Integer.parseInt(allRequestParams.get("after")) : 0;
        int limit = allRequestParams.get("limit") != null ? Integer.parseInt(allRequestParams.get("limit")) : 10;
        return adminService.getAllUsersAndRidesByRole(after, limit, filters);
    }

    @RequestMapping(value = "/user-ride", method = RequestMethod.GET)
    public List<RideRequest> getRideById(@RequestParam("userId") String userId) {
        return adminService.getUserAndRideById(userId);
    }

    //api to update properties
    @RequestMapping(value = "/properties", method = RequestMethod.GET)
    public Properties getDBProperties() {
        return DBProperties.properties;
    }

    @RequestMapping(value = "/properties", method = RequestMethod.POST)
    public Properties updateDBProperties(@RequestBody Map<String, String> properties) {
        adminService.updateDBProperties(properties);
        Properties dbProperties = initProperties.getDBProperties();
        new DBProperties(dbProperties);
        return dbProperties;
    }

    //price modification -> preview to calculate price and submit to update db properties
    @RequestMapping(value = "/price-preview", method = RequestMethod.POST)
    public Fare previewPriceChange(@RequestBody Map<String, String> priceProperties) {
        return adminService.calculateEstimatedPrice(priceProperties);
    }

    //discount modification -> update json file
    @RequestMapping(value = "/discount-config", method = RequestMethod.GET)
    public DiscountConfig[] getDiscountConfig() {
        return adminService.getDiscountConfig();
    }

    @RequestMapping(value = "/discount-config/update", method = RequestMethod.POST)
    public DiscountConfig[] updateDiscountConfig(@RequestBody DiscountConfig[] discountConfig) {
        return adminService.updateDiscountConfig(discountConfig);
    }

    @RequestMapping(value = "/qrcode/upload", method = RequestMethod.POST)
    public void uploadQRCode(@RequestParam("name") String qrCodeName, @RequestParam("qrCode") MultipartFile qrCode,
                                     @AuthenticationPrincipal PersistentUserDetails authorDetails) {
        if (!Constants.ADMIN.equals(authorDetails.getRoleName()))
            throw new ApiException(MessageConstants.NOT_AUTHORIZED);
        adminService.checkAuthorizedAdmin(authorDetails.getUserId());
        String path = adminService.qrCodeHelper(authorDetails.getUserId(), qrCodeName, qrCode);
        adminService.uploadQR(qrCodeName, path);
        successMessage = MessageConstants.QR_UPLOADED;
        new DBProperties(initProperties.getDBProperties());
    }

    @RequestMapping(value = "/qrcode/update", method = RequestMethod.POST)
    public void updateQRCode(@RequestParam("name") String qrCodeName, @RequestParam("qrCode") MultipartFile qrCode,
                                     @AuthenticationPrincipal PersistentUserDetails authorDetails) {
        if (!Constants.ADMIN.equals(authorDetails.getRoleName()))
            throw new ApiException(MessageConstants.NOT_AUTHORIZED);
        adminService.checkAuthorizedAdmin(authorDetails.getUserId());
        String path = adminService.qrCodeHelper(authorDetails.getUserId(), qrCodeName, qrCode);
        adminService.updateQR(qrCodeName, path);
        successMessage = MessageConstants.QR_UPDATED;
        new DBProperties(initProperties.getDBProperties());
    }
}
