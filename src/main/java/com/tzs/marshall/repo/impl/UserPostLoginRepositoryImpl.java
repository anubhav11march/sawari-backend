package com.tzs.marshall.repo.impl;

import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.ProfileDetails;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.repo.UserPostLoginRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class UserPostLoginRepositoryImpl implements UserPostLoginRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(UserPostLoginRepositoryImpl.class);

    public UserPostLoginRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PersistentUserDetails> getUserDetailsById(Long userId) {
        try {
            String query = "SELECT * FROM marshall_service.view_user_details vu, marshall_service.profile_contents pc " +
                    "WHERE vu.user_id=pc.profile_user_id AND user_id=:user_id AND vu.is_deleted=:is_deleted AND vu.is_enable=:is_enable";
            return jdbcTemplate.query(query, new MapSqlParameterSource().addValue("user_id", userId)
                            .addValue("is_deleted", Constants.isDeleted)
                            .addValue("is_enable", Constants.isEnable)
                    , BeanPropertyRowMapper.newInstance(PersistentUserDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public Map<String, Object> saveOrUpdateUserDetails(PersistentUserDetails authorDetails) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

            mapSqlParameterSource.addValue("userId", authorDetails.getUserId());
            mapSqlParameterSource.addValue("firstName", authorDetails.getFirstName());
            mapSqlParameterSource.addValue("middleName", authorDetails.getMiddleName());
            mapSqlParameterSource.addValue("lastName", authorDetails.getLastName());
            mapSqlParameterSource.addValue("phone", authorDetails.getPhone());
            mapSqlParameterSource.addValue("mobile", authorDetails.getMobile());

            mapSqlParameterSource.addValue("alternateEmail", authorDetails.getAlternateEmail());

            mapSqlParameterSource.addValue("street", authorDetails.getStreet());
            mapSqlParameterSource.addValue("city", authorDetails.getCity());
            mapSqlParameterSource.addValue("state", authorDetails.getState());
            mapSqlParameterSource.addValue("country", authorDetails.getCountry());
            mapSqlParameterSource.addValue("zipcode", authorDetails.getZipCode());

            mapSqlParameterSource.addValue("department", authorDetails.getDepartment());
            mapSqlParameterSource.addValue("institution", authorDetails.getInstitution());

            return new SimpleJdbcCall(Objects.requireNonNull(jdbcTemplate.getJdbcTemplate().getDataSource()))
                    .withCatalogName("marshall_service")
                    .withProcedureName("spInsertOrUpdateUserDetails")
                    .execute(mapSqlParameterSource);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<PersistentUserDetails> getUserProfileAndEssentialDetailsById(Long userId) {
        try {
            String query = "SELECT * FROM marshall_service.view_incomplete_user_details vu, marshall_service.profile_contents pc " +
                    "WHERE vu.user_id=pc.profile_user_id AND user_id=:user_id AND vu.is_deleted=:is_deleted AND vu.is_enable=:is_enable";
            return jdbcTemplate.query(query, new MapSqlParameterSource().addValue("user_id", userId)
                            .addValue("is_deleted", Constants.isDeleted)
                            .addValue("is_enable", Constants.isEnable)
                    , BeanPropertyRowMapper.newInstance(PersistentUserDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public int updateProfileDetails(PersistentUserDetails userDetails) {
        try {
            String sql = "UPDATE marshall_service.profile_contents SET " +
                    "profile_photo_name=:profilePhotoName, profile_photo_path=:profilePhotoPath, profile_photo_size=:profilePhotoSize, " +
                    "paytm_number=:paytmNumber, rickshaw_number=:rickshawNumber, " +
                    "rickshaw_front_photo_name=:rickshawFrontPhotoName, rickshaw_front_photo_path=:rickshawFrontPhotoPath, rickshaw_front_photo_size=:rickshawFrontPhotoSize, " +
                    "rickshaw_back_photo_name=:rickshawBackPhotoName, rickshaw_back_photo_path=:rickshawBackPhotoPath, rickshaw_back_photo_size=:rickshawBackPhotoSize, " +
                    "rickshaw_side_photo_name=:rickshawSidePhotoName, rickshaw_side_photo_path=:rickshawSidePhotoPath, rickshaw_side_photo_size=:rickshawSidePhotoSize, " +
                    "modify_date=:modifyDate WHERE profile_user_id=:profileUserId AND is_deleted=:isDeleted";
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource
                    .addValue("profileUserId", userDetails.getUserId())
                    .addValue("profilePhotoName", userDetails.getProfilePhotoName())
                    .addValue("profilePhotoPath", userDetails.getProfilePhotoPath())
                    .addValue("profilePhotoSize", userDetails.getProfilePhotoSize())
                    .addValue("paytmNumber", userDetails.getPaytmNumber())
                    .addValue("rickshawNumber", userDetails.getRickshawNumber())
                    .addValue("rickshawFrontPhotoName", userDetails.getRickshawFrontPhotoName())
                    .addValue("rickshawFrontPhotoPath", userDetails.getRickshawFrontPhotoPath())
                    .addValue("rickshawFrontPhotoSize", userDetails.getRickshawFrontPhotoSize())
                    .addValue("rickshawBackPhotoName", userDetails.getRickshawBackPhotoName())
                    .addValue("rickshawBackPhotoPath", userDetails.getRickshawBackPhotoPath())
                    .addValue("rickshawBackPhotoSize", userDetails.getRickshawBackPhotoSize())
                    .addValue("rickshawSidePhotoName", userDetails.getRickshawSidePhotoName())
                    .addValue("rickshawSidePhotoPath", userDetails.getRickshawSidePhotoPath())
                    .addValue("rickshawSidePhotoSize", userDetails.getRickshawSidePhotoSize())
                    .addValue("modifyDate", Timestamp.valueOf(LocalDateTime.now()))
                    .addValue("isDeleted", Constants.isDeleted);

            return jdbcTemplate.update(sql, mapSqlParameterSource);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public int updateProfilePhoto(ProfileDetails userDetails) {
        int update =0;
        try {
            String sql = "UPDATE marshall_service.profile_contents SET " +
                    "profile_photo_name=:profilePhotoName, profile_photo_path=:profilePhotoPath, profile_photo_size=:profilePhotoSize, " +
                    "modify_date=:modifyDate WHERE profile_user_id=:profileUserId AND is_deleted=:isDeleted";
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource
                    .addValue("profileUserId", userDetails.getUserId())
                    .addValue("profilePhotoName", userDetails.getProfilePhotoName())
                    .addValue("profilePhotoPath", userDetails.getProfilePhotoPath())
                    .addValue("profilePhotoSize", userDetails.getProfilePhotoSize())
                    .addValue("modifyDate", Timestamp.valueOf(LocalDateTime.now()))
                    .addValue("isDeleted", Constants.isDeleted);

            update = jdbcTemplate.update(sql, mapSqlParameterSource);
            if (update != 1) {
                 update = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate().getDataSource())
                        .withCatalogName(Constants.SCHEMA)
                        .withTableName("profile_contents")
                        .usingColumns("profile_user_id", "profile_photo_name", "profile_photo_path", "profile_photo_size",
                                 "modify_date", "is_deleted")
                        .execute(mapSqlParameterSource);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
        return update;
    }
}
