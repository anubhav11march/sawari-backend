package com.tzs.marshall.repo.impl;

import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.repo.UserPreLoginRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Repository
public class UserPreLoginRepositoryImpl implements UserPreLoginRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(UserPreLoginRepositoryImpl.class);

    public UserPreLoginRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<String, Object> getValidUser(String userName) {
        try {
            SqlParameterSource input = new MapSqlParameterSource().addValue("userName", userName);

            return new SimpleJdbcCall(Objects.requireNonNull(jdbcTemplate.getJdbcTemplate().getDataSource()))
                    .withCatalogName("marshall_service")
                    .withProcedureName("spUserLogin")
                    .returningResultSet("validUser",
                            BeanPropertyRowMapper.newInstance(PersistentUserDetails.class))
                    .execute(input);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public int updatePassword(String email, String password) {
        try {
            String query = "UPDATE marshall_service.user_registration SET password=:password " +
                    "WHERE subs_id=(SELECT subs_id FROM marshall_service.subscribe_by_email WHERE email = :email) and is_deleted=:is_deleted";
            return jdbcTemplate.update(query, new MapSqlParameterSource().addValue("password", password)
                    .addValue("email", email).addValue("is_deleted", Constants.isDeleted));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public int saveOtp(String email, String otp) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("email", email);
            mapSqlParameterSource.addValue("otp", otp);
            mapSqlParameterSource.addValue("expires_at", Timestamp.valueOf(LocalDateTime.now().plusMinutes(15)));

            return new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getJdbcTemplate().getDataSource()))
                    .withTableName("email_otp")
                    .usingColumns("email", "otp", "expires_at")
                    .execute(mapSqlParameterSource);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public Map<String, String> getValidOtpAndEmail(String otp) {
        try {
            Timestamp expires_at = Timestamp.valueOf(LocalDateTime.now());
            String query = "Select * from marshall_service.email_otp where otp=:otp and expires_at>:expires_at";
            Map<String, String> otpEmailMap = new HashMap<>();
            jdbcTemplate.query(query, new MapSqlParameterSource().addValue("otp", otp).addValue("expires_at", expires_at),
                    (rs, rowNum) -> otpEmailMap.put(rs.getString("otp"), rs.getString("email")));
            return otpEmailMap;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }
}
