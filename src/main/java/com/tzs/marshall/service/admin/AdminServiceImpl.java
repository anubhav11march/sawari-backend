package com.tzs.marshall.service.admin;

import com.tzs.marshall.bean.*;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.repo.admin.AdminRepository;
import com.tzs.marshall.service.FareCalculationService;
import com.tzs.marshall.service.RideRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.tzs.marshall.constants.Constants.*;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private RideRequestService rideRequestService;
    @Autowired
    private FareCalculationService fareCalculationService;

    private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Override
    public List<PersistentUserDetails> getUsersRights() {
        log.info("Fetching Rights and Permissions for All Users...");
        List<PersistentUserDetails> allUserRights = adminRepository.getAllUserRights();
        if (allUserRights.size() == 0) {
            log.error("No users' right found in DB");
            throw new ApiException(MessageConstants.NO_AUTHOR_RIGHT);
        }
        return allUserRights;
    }

    @Override
    public PersistentUserDetails getUserRightsById(Long userId) {
        log.info("Fetching All the Rights and Permission for UserId: {}", userId);
        List<PersistentUserDetails> userRightsById = adminRepository.getUserRightsById(userId);
        if (userRightsById.size() == 0) {
            log.error("No User found with this id: {}", userId);
            throw new ApiException(MessageConstants.NO_USER);
        }
        log.info("Details Found...{}", userRightsById);
        return userRightsById.stream().findFirst().get();
    }

    @Override
    public PersistentUserDetails updateUserRights(PersistentUserRights userRights) {
        log.info("Updating User's Rights... {}", userRights);
        Map<String, Object> stringObjectMap = adminRepository.updateUserRights(userRights);
        if (stringObjectMap.isEmpty()) {
            log.error("No User Found. {}", stringObjectMap);
            throw new ApiException(MessageConstants.RIGHTS_UPDATE_ERR);
        }
        log.info("All Rights updated successfully.");
        return getUserRightsById(userRights.getUserId());
    }

    @Override
    public List<PersistentUserDetails> getAllUsersByRole(String role, Map filters) {
        log.info("Fetching All Completed Profiles Users Details with filters {}", filters);
        List<PersistentUserDetails> allUsers = adminRepository.getAllUsersProfile(role.toUpperCase(), filters);
        log.info("Records Found: {}", allUsers);
        return allUsers;
    }

    @Override
    public List<PersistentUserDetails> getAllIncompleteProfileUsersByRole(String role, int after, int limit) {
        log.info("Fetching All Incomplete Profile Users Details...");
        List<PersistentUserDetails> allIncompleteProfileUsersDetails = adminRepository
                .getAllIncompleteProfileUsersDetails(role.toUpperCase(), after, limit);
        if (allIncompleteProfileUsersDetails.size() == 0) {
            log.error("No User Found...{}", allIncompleteProfileUsersDetails);
            throw new ApiException(MessageConstants.NO_USER_REGISTER);
        }
        log.info("Records Found: {}", allIncompleteProfileUsersDetails);
        return allIncompleteProfileUsersDetails;
    }

    @Override
    public PersistentUserDetails getUserDetailsById(Long userId) {
        log.info("Fetching users Details...");
        List<PersistentUserDetails> userDetails = adminRepository.getCompleteUserProfileDetailsById(userId);
        if (userDetails.size() != 0) {
            log.info("Complete Profile found of this user: {}", userId);
            return userDetails.stream().findAny().get();
        }
        log.info("Complete profile details is not available, fetching rest of the details: {}", userId);
        userDetails = adminRepository.getUserProfileDetailsById(userId);
        if (userDetails.size() == 0) {
            log.error("No User Found...{}", userId);
            throw new ApiException(MessageConstants.NO_USER_REGISTER + userId);
        }
        return userDetails.stream().findAny().get();
    }

    @Override
    public void checkAuthorizedAdmin(Long userId) {
        if (!DBProperties.properties.getProperty(QRCODE_UPLOADER).contains(String.valueOf(userId))) {
            log.error("You are not authorized.");
            throw new ApiException(MessageConstants.NOT_AUTHORIZED);
        }
    }

    @Override
    public List<UserRideEarnings> getAllUsersAndEarningsByRole(String role, Map filters) {
        List<PersistentUserDetails> allUsersByRole = getAllUsersByRole(role, filters);
        List<UserRideEarnings> userRideEarningsList = new ArrayList<>();
        allUsersByRole.forEach(user -> {
            Map<String, Object> totalEarningById = rideRequestService.getTotalEarningByDriver(user.getUserId());
            UserRideEarnings userRideEarnings = new UserRideEarnings(user, totalEarningById);
            userRideEarningsList.add(userRideEarnings);
        });
        return userRideEarningsList;
    }

    @Override
    public UserRideEarnings getUserAndEarningById(String userId) {
        PersistentUserDetails user = getUserDetailsById(Long.valueOf(userId));
        Map<String, Object> totalEarningById = rideRequestService.getTotalEarningByDriver(user.getUserId());
        UserRideEarnings userRideEarnings = new UserRideEarnings(user, totalEarningById);
        return userRideEarnings;
    }

    @Override
    public List<RideRequest> getAllUsersAndRidesByRole(int after, int limit, Map filters) {
        return adminRepository.getAllUsersRides(after, limit, filters);
    }

    @Override
    public List<RideRequest> getUserAndRideById(String userId) {
        return adminRepository.getAllUsersRidesById(Long.valueOf(userId));
    }

    @Override
    public Map<String, String> updateDBProperties(Map<String, String> properties) {
        adminRepository.updateDBProperties(properties);
        return null;
    }

    @Override
    public Fare calculateEstimatedPrice(Map<String, String> priceProperties) {
        return fareCalculationService.getEstimatedFareForPreview(priceProperties);
    }

    @Override
    public DiscountConfig[] getDiscountConfig() {
        return fareCalculationService.getDiscountConfig();
    }

    @Override
    public DiscountConfig[] updateDiscountConfig(DiscountConfig[] discountConfig) {
        fareCalculationService.updateDiscountConfig(discountConfig);
        return new DiscountConfig[0];
    }

    @Override
    public String qrCodeHelper(long id, String qrCodeName, MultipartFile qrCode) {
        log.info("Uploading QR Code to server for: " + qrCodeName);
        checkContentType(qrCode.getContentType());
        try {
            String fileName = Objects.requireNonNull(qrCode.getOriginalFilename()).trim().replaceAll(" ", "_");
            String pathString = Constants.BASE_PATH + File.separator + Constants.QRCODE_DIR + File.separator
                    + qrCodeName;
            Path qrPath = Paths.get(pathString);
            if (new File(String.valueOf(qrPath)).exists()
                    && Files.deleteIfExists(Objects.requireNonNull(Files.list(qrPath).findAny().orElse(null)))) {
                log.warn(String.format("Old QR-Code deleted from: %s", qrPath));
            }
            Path path = Paths.get(pathString + File.separator + fileName);
            log.info(String.format("Path: %s", path));
            File contentSaveDir = new File(String.valueOf(path));
            if (!contentSaveDir.exists()) {
                log.info("Creating Directories...");
                boolean mkdirs = contentSaveDir.mkdirs();
                if (!mkdirs)
                    log.error("Cannot Create Directories. Please Check.");
            }
            Files.copy(qrCode.getInputStream(),
                    path,
                    StandardCopyOption.REPLACE_EXISTING);
            return path.toString();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.UNABLE_TO_SAVE_FILE);
        }
    }

    @Override
    public void uploadQR(String qrCodeName, String path) {
        log.info("Uploading QR Code to DB...");
        adminRepository.uploadQRCodeToDB(qrCodeName, path);
    }

    @Override
    public void updateQR(String qrCodeName, String path) {
        log.info("Updating QR Code...");
        adminRepository.updateQRCode(qrCodeName, path);
        log.info("QR Code updated successfully.");
    }

    private void checkContentType(String contentType) {
        if (!Constants.IMAGE_TYPE.contains(Objects.requireNonNull(contentType))) {
            log.info(contentType + " is not valid");
            throw new ApiException(MessageConstants.IMAGE_UPLOAD);
        }
    }
}
