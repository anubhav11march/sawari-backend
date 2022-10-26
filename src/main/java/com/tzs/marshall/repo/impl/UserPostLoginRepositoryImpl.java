package com.tzs.marshall.repo.impl;

import com.tzs.marshall.bean.PersistentUserDetails;
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
                    "rickshaw_number=:rickshawNumber, rickshaw_photo_name=:rickshawPhotoName, rickshaw_photo_path=:rickshawPhotoPath, rickshaw_photo_size=:rickshawPhotoSize, " +
                    "modify_date=:modifyDate WHERE profile_user_id=:profileUserId AND is_deleted=:isDeleted";
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource
                    .addValue("profileUserId", userDetails.getUserId())
                    .addValue("profilePhotoName", userDetails.getProfilePhotoName())
                    .addValue("profilePhotoPath", userDetails.getProfilePhotoPath())
                    .addValue("profilePhotoSize", userDetails.getProfilePhotoSize())
                    .addValue("rickshawNumber", userDetails.getRickshawNumber())
                    .addValue("rickshawPhotoName", userDetails.getRickshawPhotoName())
                    .addValue("rickshawPhotoPath", userDetails.getRickshawPhotoPath())
                    .addValue("rickshawPhotoSize", userDetails.getRickshawPhotoSize())
                    .addValue("modifyDate", Timestamp.valueOf(LocalDateTime.now()))
                    .addValue("isDeleted", Constants.isDeleted);

            return jdbcTemplate.update(sql, mapSqlParameterSource);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }
}
