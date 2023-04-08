package com.tzs.marshall.service.admin;

import com.tzs.marshall.bean.DBProperties;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.PersistentUserRights;
import com.tzs.marshall.bean.UserRideEarnings;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.repo.admin.AdminRepository;
import com.tzs.marshall.service.RideRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.tzs.marshall.constants.Constants.*;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private RideRequestService rideRequestService;

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
    public List<PersistentUserDetails> getAllUsersByRole(String role, int after, int limit, Map filters) {
        log.info("Fetching All Completed Profiles Users Details with filters {}", filters);
        List<PersistentUserDetails> allUsers = adminRepository.getAllUsersProfile(role.toUpperCase(), after, limit, filters);
        log.info("Records Found: {}", allUsers);
        return allUsers;
    }

    @Override
    public List<PersistentUserDetails> getAllIncompleteProfileUsersByRole(String role, int after, int limit) {
        log.info("Fetching All Incomplete Profile Users Details...");
        List<PersistentUserDetails> allIncompleteProfileUsersDetails = adminRepository.getAllIncompleteProfileUsersDetails(role.toUpperCase(), after, limit);
        if (allIncompleteProfileUsersDetails.size() == 0) {
            log.error("No User Found...{}", allIncompleteProfileUsersDetails);
            throw new ApiException(MessageConstants.NO_AUTHOR_REGISTER);
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
            throw new ApiException(MessageConstants.NO_AUTHOR_REGISTER + userId);
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
    public List<UserRideEarnings> getAllUsersAndRidesByRole(String role, int after, int limit, Map filters) {
        List<PersistentUserDetails> allUsersByRole = getAllUsersByRole(role, after, limit, filters);
        List<UserRideEarnings> userRideEarningsList = new ArrayList<>();
        allUsersByRole.forEach(user -> {
            Map<String, Object> totalEarningById = rideRequestService.getTotalEarningByDriver(user.getUserId());
            UserRideEarnings userRideEarnings = new UserRideEarnings(user, totalEarningById);
            userRideEarningsList.add(userRideEarnings);
        });
        return userRideEarningsList;
    }

    @Override
    public UserRideEarnings getUserAndRideById(String userId) {
        PersistentUserDetails user = getUserDetailsById(Long.valueOf(userId));
        Map<String, Object> totalEarningById = rideRequestService.getTotalEarningByDriver(user.getUserId());
        UserRideEarnings userRideEarnings = new UserRideEarnings(user, totalEarningById);
        return userRideEarnings;
    }
}
