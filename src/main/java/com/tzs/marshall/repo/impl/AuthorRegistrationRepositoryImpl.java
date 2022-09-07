package com.tzs.marshall.repo.impl;

import com.tzs.marshall.bean.NewsLetterEmailSubs;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.repo.AuthorRegistrationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class AuthorRegistrationRepositoryImpl implements AuthorRegistrationRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(AuthorRegistrationRepositoryImpl.class);

    public AuthorRegistrationRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
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
        String query = "SELECT user_id, user_name, email, mobile FROM marshall_service.subscribe_by_email eml, marshall_service.user_registration reg " +
                "WHERE eml.subs_id = reg.subs_id AND (eml.email=:email OR reg.user_name=:userName OR reg.mobile = :mobile)";
        try {
            return jdbcTemplate.query(query,
                    new MapSqlParameterSource().addValue("email", authorDetails.getEmail())
                            .addValue("userName", authorDetails.getUsername())
                            .addValue("mobile", authorDetails.getMobile()),
                    (rs, rowNum) -> new PersistentUserDetails(rs.getLong("user_id"), rs.getString("email"), rs.getString("user_name"), rs.getString("mobile")));
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
            mapSqlParameterSource.addValue("is_enable", Constants.isEnable);

            return new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getJdbcTemplate().getDataSource()))
                    .withTableName("user_registration")
                    .usingColumns("first_name", "middle_name", "last_name", "user_name", "mobile", "password", "subs_id", "is_enable")
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
    public int enableUser(String email) {
        String query = "UPDATE marshall_service.user_registration SET is_enable=:is_enable " +
                "WHERE subs_id=(SELECT subs_id FROM marshall_service.subscribe_by_email WHERE email = :email)";
        try {
            return jdbcTemplate.update(query, new MapSqlParameterSource().addValue("is_enable", Constants.isEnable).addValue("email", email));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public void rollbackRegistration(PersistentUserDetails authorDetails) {
        String userBridge = "DELETE FROM marshall_service.user_role_type_bridge WHERE user_id=:userId";
        String userRegistration = "DELETE FROM marshall_service.user_registration WHERE user_id=:userId";
        try {
            authorDetails = findExistingUsers(authorDetails).stream().findAny().get();
            log.info("UserId found: " + authorDetails.getUserId());
            jdbcTemplate.update(userBridge, new MapSqlParameterSource("userId", authorDetails.getUserId()));
            jdbcTemplate.update(userRegistration, new MapSqlParameterSource("userId", authorDetails.getUserId()));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }
}