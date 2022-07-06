package com.ether.author.repo.admin;

import com.ether.author.bean.PersistentAuthorDetails;
import com.ether.author.bean.PersistentAuthorRights;
import com.ether.author.constants.Constants;
import com.ether.author.constants.MessageConstants;
import com.ether.author.error.ApiException;
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
    public List<PersistentAuthorDetails> getAllAuthorRights() {
        try {
            String query = "SELECT * FROM ether_service.view_user_rights WHERE role_name=:roleName";
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("roleName", Constants.ROLE_AUTHOR);
            return jdbcTemplate.query(query, mapSqlParameterSource, BeanPropertyRowMapper.newInstance(PersistentAuthorDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<PersistentAuthorDetails> getAuthorRightsById(Long userId) {
        try {
            String query = "SELECT * FROM ether_service.view_user_rights WHERE user_id=:userId AND role_name=:roleName";
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("userId", userId)
                    .addValue("roleName", Constants.ROLE_AUTHOR);
            return jdbcTemplate.query(query, mapSqlParameterSource, BeanPropertyRowMapper.newInstance(PersistentAuthorDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public Map<String, Object> updateAuthorRights(PersistentAuthorRights authorRights) {
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
                    .withSchemaName("ether_service")
                    .withProcedureName("spUpdateUserRights")
                    .execute(mapSqlParameterSource);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<PersistentAuthorDetails> getAllAuthors() {
        try {
            String query = "SELECT * FROM ether_service.view_user_details WHERE role_name=:roleName";
            return jdbcTemplate.query(query, new MapSqlParameterSource().addValue("roleName", Constants.ROLE_AUTHOR),
                    BeanPropertyRowMapper.newInstance(PersistentAuthorDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<PersistentAuthorDetails> getAllIncompleteProfileAuthorsDetails() {
        try {
            String query = "SELECT * FROM ether_service.view_incomplete_user_details WHERE role_name=:roleName";
            return jdbcTemplate.query(query, new MapSqlParameterSource().addValue("roleName", Constants.ROLE_AUTHOR),
                    BeanPropertyRowMapper.newInstance(PersistentAuthorDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<PersistentAuthorDetails> getCompleteAuthorProfileDetailsById(Long authorId) {
        try {
            String query = "SELECT * FROM ether_service.view_user_details WHERE user_id=:userId AND role_name=:roleName";
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("userId", authorId)
                            .addValue("roleName", Constants.ROLE_AUTHOR),
                    BeanPropertyRowMapper.newInstance(PersistentAuthorDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<PersistentAuthorDetails> getAuthorProfileDetailsById(Long authorId) {
        try {
            String query = "SELECT * FROM ether_service.view_incomplete_user_details WHERE user_id=:userId AND role_name=:roleName";
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("userId", authorId)
                            .addValue("roleName", Constants.ROLE_AUTHOR),
                    BeanPropertyRowMapper.newInstance(PersistentAuthorDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }
}
