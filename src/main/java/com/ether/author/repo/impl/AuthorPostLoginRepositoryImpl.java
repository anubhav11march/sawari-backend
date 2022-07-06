package com.ether.author.repo.impl;

import com.ether.author.bean.PersistentAuthorDetails;
import com.ether.author.constants.Constants;
import com.ether.author.constants.MessageConstants;
import com.ether.author.error.ApiException;
import com.ether.author.repo.AuthorPostLoginRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class AuthorPostLoginRepositoryImpl implements AuthorPostLoginRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(AuthorPostLoginRepositoryImpl.class);

    public AuthorPostLoginRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PersistentAuthorDetails> getUserDetailsById(Long userId) {
        try {
            String query = "SELECT * FROM ether_service.view_user_details WHERE user_id=:user_id AND is_deleted=:is_deleted AND is_enable=:is_enable";
            return jdbcTemplate.query(query, new MapSqlParameterSource().addValue("user_id", userId)
                            .addValue("is_deleted", Constants.isDeleted)
                            .addValue("is_enable", Constants.isEnable)
                    , BeanPropertyRowMapper.newInstance(PersistentAuthorDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public Map<String, Object> saveOrUpdateUserDetails(PersistentAuthorDetails authorDetails) {
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
                    .withSchemaName("ether_service")
                    .withProcedureName("spInsertOrUpdateUserDetails")
                    .execute(mapSqlParameterSource);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }
}