package com.tzs.marshall.repo.impl;

import com.tzs.marshall.bean.NewsLetterEmailSubs;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.ProfileDetails;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.repo.UserRegistrationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
public class UserRegistrationRepositoryImpl implements UserRegistrationRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(UserRegistrationRepositoryImpl.class);

    public UserRegistrationRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<NewsLetterEmailSubs> findSubsIdByEmail(String email) {
        String query = "Select subs_id, email from marshall_service.subscribe_by_email where email=:email";
        try {
            return jdbcTemplate.query(query, new MapSqlParameterSource().addValue("email", email),
                    (rs, rowNum) -> new NewsLetterEmailSubs(rs.getLong("subs_id"), rs.getString("email")));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<PersistentUserDetails> findExistingUsers(PersistentUserDetails authorDetails) {
        String query = "SELECT c.*, p.paytm_number FROM (SELECT user_id, user_name, email, mobile, is_enable FROM marshall_service.subscribe_by_email eml, marshall_service.user_registration reg " +
                " WHERE eml.subs_id = reg.subs_id) c " +
                " LEFT JOIN marshall_service.profile_contents p ON p.profile_user_id=c.user_id " +
                " WHERE " +
                " p.paytm_number=:paytmNumber OR c.email=:email OR c.user_name=:userName OR c.mobile=:mobile";
        try {
            return jdbcTemplate.query(query,
                    new MapSqlParameterSource().addValue("email", authorDetails.getEmail())
                            .addValue("userName", authorDetails.getUsername())
                            .addValue("mobile", authorDetails.getMobile())
                            .addValue("paytmNumber", authorDetails.getPaytmNumber()),
                    (rs, rowNum) -> new PersistentUserDetails(rs.getLong("user_id"), rs.getString("email"),
                            rs.getString("user_name"), rs.getString("mobile"), rs.getString("paytm_number")));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<NewsLetterEmailSubs> saveNewsLetterSubsEmail(String email) {
        try {
            Number number = new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getJdbcTemplate().getDataSource()))
                    .withTableName("subscribe_by_email")
                    .usingColumns("email")
                    .execute(new MapSqlParameterSource().addValue("email", email));
            if (number.longValue() > 0) {
                return findSubsIdByEmail(email);
            }
            throw new ApiException(MessageConstants.EMAIL_ALREADY_REGISTERED);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public int saveUserEssentialDetails(PersistentUserDetails authorDetails, Long subsId) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("firstName", authorDetails.getFirstName());
            mapSqlParameterSource.addValue("middleName", authorDetails.getMiddleName());
            mapSqlParameterSource.addValue("lastName", authorDetails.getLastName());
            mapSqlParameterSource.addValue("userName", authorDetails.getUsername());
            mapSqlParameterSource.addValue("mobile", authorDetails.getMobile());
            mapSqlParameterSource.addValue("password", authorDetails.getPassword());
            mapSqlParameterSource.addValue("subsId", subsId);
            mapSqlParameterSource.addValue("is_enable", !Constants.isEnable);

            return new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getJdbcTemplate().getDataSource()))
                    .withTableName("user_registration")
                    .usingColumns("first_name", "middle_name", "last_name", "user_name", "mobile", "password", "subs_id", "is_enable")
                    .usingGeneratedKeyColumns("user_id")
                    .execute(mapSqlParameterSource);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public void insertIntoUserBridgeTable(String userName, String roleName, String typeName) {
        try {
            SqlParameterSource input = new MapSqlParameterSource().addValue("userName", userName).addValue("userRoleName", roleName).addValue("userTypeName", typeName);

            new SimpleJdbcCall(Objects.requireNonNull(jdbcTemplate.getJdbcTemplate().getDataSource()))
                    .withCatalogName("marshall_service")
                    .withProcedureName("spUserRoleTypeBridge")
                    .execute(input);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public int enableUser(String email, String reqType) {
        String query = "UPDATE marshall_service.user_registration SET is_enable=:is_enable " +
                "WHERE subs_id=(SELECT subs_id FROM marshall_service.subscribe_by_email WHERE email = :email)" +
                " OR mobile= :email";
        try {
            return jdbcTemplate.update(query, new MapSqlParameterSource().addValue("is_enable", Constants.isEnable).addValue("email", email));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public void rollbackRegistration(PersistentUserDetails userDetails) {
        String userBridge = "DELETE FROM marshall_service.user_role_type_bridge WHERE user_id=(SELECT user_id FROM marshall_service.user_registration WHERE user_name=:userName)";
        String profileDetails = "DELETE FROM marshall_service.profile_contents WHERE profile_user_id=(SELECT user_id FROM marshall_service.user_registration WHERE user_name=:userName)";
        String userRegistration = "DELETE FROM marshall_service.user_registration WHERE user_name=:userName";
        try {
            log.info("UserId found: " + userDetails.getUserId());
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource("userName", userDetails.getUserName());
            jdbcTemplate.update(userBridge, mapSqlParameterSource);
            jdbcTemplate.update(profileDetails, mapSqlParameterSource);
            jdbcTemplate.update(userRegistration, mapSqlParameterSource);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public int saveDriverImagesDetails(ProfileDetails profileDetails, String roleName) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource
                    .addValue("profileUserId", profileDetails.getUserId())
                    .addValue("profilePhotoName", profileDetails.getProfilePhotoName())
                    .addValue("profilePhotoPath", profileDetails.getProfilePhotoPath())
                    .addValue("profilePhotoSize", profileDetails.getProfilePhotoSize())
                    .addValue("aadharNumber", profileDetails.getAadharNumber())
                    .addValue("aadharBackPhotoName", profileDetails.getAadharBackPhotoName())
                    .addValue("aadharBackPhotoPath", profileDetails.getAadharBackPhotoPath())
                    .addValue("aadharBackPhotoSize", profileDetails.getAadharBackPhotoSize())
                    .addValue("aadharFrontPhotoName", profileDetails.getAadharFrontPhotoName())
                    .addValue("aadharFrontPhotoPath", profileDetails.getAadharFrontPhotoPath())
                    .addValue("aadharFrontPhotoSize", profileDetails.getAadharFrontPhotoSize())
                    .addValue("paytmNumber", profileDetails.getPaytmNumber())
                    .addValue("rickshawNumber", profileDetails.getRickshawNumber())
                    .addValue("rickshawFrontPhotoName", profileDetails.getRickshawFrontPhotoName())
                    .addValue("rickshawFrontPhotoPath", profileDetails.getRickshawFrontPhotoPath())
                    .addValue("rickshawFrontPhotoSize", profileDetails.getRickshawFrontPhotoSize())
                    .addValue("rickshawBackPhotoName", profileDetails.getRickshawBackPhotoName())
                    .addValue("rickshawBackPhotoPath", profileDetails.getRickshawBackPhotoPath())
                    .addValue("rickshawBackPhotoSize", profileDetails.getRickshawBackPhotoSize())
                    .addValue("rickshawSidePhotoName", profileDetails.getRickshawSidePhotoName())
                    .addValue("rickshawSidePhotoPath", profileDetails.getRickshawSidePhotoPath())
                    .addValue("rickshawSidePhotoSize", profileDetails.getRickshawSidePhotoSize())
                    .addValue("uploadDate", Timestamp.valueOf(LocalDateTime.now()))
                    .addValue("modifyDate", Timestamp.valueOf(LocalDateTime.now()))
                    .addValue("isDeleted", Constants.isDeleted);

            return new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getJdbcTemplate().getDataSource()))
                    .withCatalogName(Constants.SCHEMA)
                    .withTableName("profile_contents")
                    .usingColumns("profile_user_id", "profile_photo_name", "profile_photo_path", "profile_photo_size",
                            "paytm_number", "aadhar_number", "aadhar_back_photo_name", "aadhar_back_photo_path", "aadhar_back_photo_size",
                            "aadhar_front_photo_name", "aadhar_front_photo_path", "aadhar_front_photo_size",
                            "rickshaw_number", "rickshaw_front_photo_name", "rickshaw_front_photo_path", "rickshaw_front_photo_size",
                            "rickshaw_back_photo_name", "rickshaw_back_photo_path", "rickshaw_back_photo_size",
                            "rickshaw_side_photo_name", "rickshaw_side_photo_path", "rickshaw_side_photo_size",
                            "upload_date", "modify_date", "is_deleted")
                    .execute(mapSqlParameterSource);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<CustomRowMapper> findExistingUserWithMobileNumber(Long userId, String mobileNumber) {
        String sqlUR = "SELECT user_id, mobile, paytm_number FROM marshall_service.user_registration ur LEFT JOIN marshall_service.profile_contents pc " +
                "ON pc.profile_user_id=ur.user_id WHERE mobile=:mobileNumber or paytm_number=:paytmNumber";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("mobileNumber", mobileNumber)
                .addValue("paytmNumber", mobileNumber);
        List<CustomRowMapper> query = jdbcTemplate.query(sqlUR, mapSqlParameterSource,
                (rs, rowNum) -> new CustomRowMapper(rs.getString("user_id"), rs.getString("mobile"),
                        rs.getString("paytm_number")));
        return query;
    }

    public static class CustomRowMapper {
        private final String userId;
        private final String mobileNumber;
        private final String paytmNumber;

        CustomRowMapper(String userId, String mobileNumber, String paytmNumber) {
            this.userId = userId;
            this.mobileNumber = mobileNumber;
            this.paytmNumber = paytmNumber;
        }

        public String getMobileNumber() {
            return this.mobileNumber;
        }

        public String getPaytmNumber() {
            return this.paytmNumber;
        }

        public String getUserId() {
            return this.userId;
        }
    }
}
