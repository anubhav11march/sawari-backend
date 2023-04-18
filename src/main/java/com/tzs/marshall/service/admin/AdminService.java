package com.tzs.marshall.service.admin;

import com.tzs.marshall.bean.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface AdminService {
    List<PersistentUserDetails> getUsersRights();

    PersistentUserDetails getUserRightsById(Long userId);

    PersistentUserDetails updateUserRights(PersistentUserRights authorRights);

    List<PersistentUserDetails> getAllUsersByRole(String role, int after, int limit, Map filters);

    List<PersistentUserDetails> getAllIncompleteProfileUsersByRole(String role, int after, int limit);

    PersistentUserDetails getUserDetailsById(Long authorId);

    void checkAuthorizedAdmin(Long userId);

    List<UserRideEarnings> getAllUsersAndEarningsByRole(String role, int after, int limit, Map filters);

    UserRideEarnings getUserAndEarningById(String userId);

    List<RideRequest> getAllUsersAndRidesByRole(int after, int limit, Map filters);

    List<RideRequest> getUserAndRideById(String userId);

    Map<String, String> updateDBProperties(Map<String, String> properties);

    Fare calculateEstimatedPrice(Map<String, String> priceProperties);

    DiscountConfig[] getDiscountConfig();

    DiscountConfig[] updateDiscountConfig(DiscountConfig[] discountConfig);

    String qrCodeHelper(long id, String qrCodeName, MultipartFile qrCode);

    void uploadQR(String qrCodeName, String path);

    void updateQR(String qrCodeName, String path);
}
