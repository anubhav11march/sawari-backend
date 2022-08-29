package com.tzs.marshall.service.admin;

import com.tzs.marshall.bean.DBProperties;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.PersistentUserRights;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.repo.admin.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.tzs.marshall.constants.Constants.*;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Override
    public List<PersistentUserDetails> getAuthorsRights() {
        log.info("Fetching Rights and Permissions for All Authors...");
        List<PersistentUserDetails> allAuthorRights = adminRepository.getAllAuthorRights();
        if (allAuthorRights.size() == 0) {
            log.error("No authors' right found in DB");
            throw new ApiException(MessageConstants.NO_AUTHOR_RIGHT);
        }
        return allAuthorRights;
    }

    @Override
    public PersistentUserDetails getAuthorRightsById(Long userId) {
        log.info("Fetching All the Rights and Permission for AuthorId: {}", userId);
        List<PersistentUserDetails> authorRightsById = adminRepository.getAuthorRightsById(userId);
        if (authorRightsById.size() == 0) {
            log.error("No User found with this id: {}", userId);
            throw new ApiException(MessageConstants.NO_USER);
        }
        log.info("Details Found...{}", authorRightsById);
        return authorRightsById.stream().findFirst().get();
    }

    @Override
    public PersistentUserDetails updateAuthorRights(PersistentUserRights authorRights) {
        log.info("Updating Author's Rights... {}", authorRights);
        Map<String, Object> stringObjectMap = adminRepository.updateAuthorRights(authorRights);
        if (stringObjectMap.isEmpty()) {
            log.error("No User Found. {}", stringObjectMap);
            throw new ApiException(MessageConstants.RIGHTS_UPDATE_ERR);
        }
        log.info("All Rights updated successfully.");
        return getAuthorRightsById(authorRights.getUserId());
    }

    @Override
    public List<PersistentUserDetails> getAllCompleteProfileUsersByRole(String role) {
        log.info("Fetching All Completed Profiles Authors Details...");
        List<PersistentUserDetails> allUsers = adminRepository.getAllAuthors(role.toUpperCase());
        if (allUsers.size() == 0) {
            log.error("No User Found...{}", allUsers);
            throw new ApiException(MessageConstants.COMPLETE_PROFILE_ERR);
        }
        log.info("Records Found: {}", allUsers);
        return allUsers;
    }

    @Override
    public List<PersistentUserDetails> getAllIncompleteProfileUsersByRole(String role) {
        log.info("Fetching All Incomplete Profile Authors Details...");
        List<PersistentUserDetails> allIncompleteProfileAuthorsDetails = adminRepository.getAllIncompleteProfileAuthorsDetails(role.toUpperCase());
        if (allIncompleteProfileAuthorsDetails.size() == 0) {
            log.error("No User Found...{}", allIncompleteProfileAuthorsDetails);
            throw new ApiException(MessageConstants.NO_AUTHOR_REGISTER);
        }
        log.info("Records Found: {}", allIncompleteProfileAuthorsDetails);
        return allIncompleteProfileAuthorsDetails;
    }

    @Override
    public PersistentUserDetails getAuthorDetailsById(Long authorId) {
        log.info("Fetching Authors Details...");
        List<PersistentUserDetails> authorDetails = adminRepository.getCompleteAuthorProfileDetailsById(authorId);
        if (authorDetails.size() != 0) {
            log.info("Complete Profile found of this author: {}", authorId);
            return authorDetails.stream().findAny().get();
        }
        log.info("Complete profile details is not available, fetching rest of the details: {}", authorId);
        authorDetails = adminRepository.getAuthorProfileDetailsById(authorId);
        if (authorDetails.size() == 0) {
            log.error("No User Found...{}", authorId);
            throw new ApiException(MessageConstants.NO_AUTHOR_REGISTER + authorId);
        }
        return authorDetails.stream().findAny().get();
    }

    @Override
    public void checkAuthorizedAdmin(Long userId) {
        if (!DBProperties.properties.getProperty(QRCODE_UPLOADER).contains(String.valueOf(userId))) {
            log.error("You are not authorized.");
            throw new ApiException(MessageConstants.NOT_AUTHORIZED);
        }
    }
}
