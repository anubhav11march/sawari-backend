package com.tzs.marshall.repo.admin;

import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.PersistentUserRights;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AdminRepositoryImpl implements AdminRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(AdminRepositoryImpl.class);

    public AdminRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PersistentUserDetails> getAllAuthorRights() {
        try {
            String query = "SELECT * FROM marshall_service.view_user_rights WHERE role_name=:roleName";
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("roleName", Constants.USER);
            return jdbcTemplate.query(query, mapSqlParameterSource, BeanPropertyRowMapper.newInstance(PersistentUserDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<PersistentUserDetails> getAuthorRightsById(Long userId) {
        try {
            String query = "SELECT * FROM marshall_service.view_user_rights WHERE user_id=:userId";
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("userId", userId);
            return jdbcTemplate.query(query, mapSqlParameterSource, BeanPropertyRowMapper.newInstance(PersistentUserDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public Map<String, Object> updateAuthorRights(PersistentUserRights authorRights) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("userId", authorRights.getUserId())
                    .addValue("roleName", authorRights.getRoleName())
                    .addValue("typeName", authorRights.getTypeName())
                    .addValue("permissionName", authorRights.getPermissionName())
                    .addValue("associateId", authorRights.getAssociateId())
                    .addValue("bridgeId", authorRights.getBridgeId())
                    .addValue("isDeleted", authorRights.isDeleted())
                    .addValue("isEnable", authorRights.isEnable());
            return new SimpleJdbcCall(Objects.requireNonNull(jdbcTemplate.getJdbcTemplate().getDataSource()))
                    .withCatalogName("marshall_service")
                    .withProcedureName("spUpdateUserRights")
                    .execute(mapSqlParameterSource);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<PersistentUserDetails> getAllAuthors(String role) {
        try {
            String query = "SELECT * FROM marshall_service.view_user_details WHERE role_name=:roleName";
            return jdbcTemplate.query(query, new MapSqlParameterSource().addValue("roleName", role),
                    BeanPropertyRowMapper.newInstance(PersistentUserDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<PersistentUserDetails> getAllIncompleteProfileAuthorsDetails(String role) {
        try {
            String query = "SELECT * FROM marshall_service.view_incomplete_user_details WHERE role_name=:roleName";
            return jdbcTemplate.query(query, new MapSqlParameterSource().addValue("roleName", role),
                    BeanPropertyRowMapper.newInstance(PersistentUserDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<PersistentUserDetails> getCompleteAuthorProfileDetailsById(Long userId) {
        try {
            String query = "SELECT * FROM marshall_service.view_user_details WHERE user_id=:userId";
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("userId", userId),
                    BeanPropertyRowMapper.newInstance(PersistentUserDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<PersistentUserDetails> getAuthorProfileDetailsById(Long userId) {
        try {
            String query = "SELECT * FROM marshall_service.view_incomplete_user_details WHERE user_id=:userId";
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("userId", userId),
                    BeanPropertyRowMapper.newInstance(PersistentUserDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

}
