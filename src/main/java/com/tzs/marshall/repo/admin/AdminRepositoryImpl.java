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

import java.util.HashMap;
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
    public List<PersistentUserDetails> getAllUserRights() {
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
    public List<PersistentUserDetails> getUserRightsById(Long userId) {
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
    public Map<String, Object> updateUserRights(PersistentUserRights authorRights) {
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
    public List<PersistentUserDetails> getAllUsers(String role, int after, int limit) {
        try {
            String query = "SELECT * FROM marshall_service.view_user_details vsd, marshall_service.profile_contents pc " +
                    "WHERE vsd.user_id=pc.profile_user_id and vsd.role_name=:roleName and vsd.user_id>:after order by vsd.user_id limit :limit";
            return jdbcTemplate.query(query, new MapSqlParameterSource().addValue("roleName", role).addValue("after", after)
                            .addValue("limit", limit),
                    BeanPropertyRowMapper.newInstance(PersistentUserDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<PersistentUserDetails> getAllIncompleteProfileUsersDetails(String role, int after, int limit) {
        try {
            String query = "SELECT * FROM marshall_service.view_incomplete_user_details vsd, marshall_service.profile_contents pc " +
                    "WHERE vsd.user_id=pc.profile_user_id and vsd.role_name=:roleName and vsd.user_id>:after order by vsd.user_id limit :limit";
            return jdbcTemplate.query(query, new MapSqlParameterSource().addValue("roleName", role).addValue("after", after)
                            .addValue("limit", limit),
                    BeanPropertyRowMapper.newInstance(PersistentUserDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<PersistentUserDetails> getCompleteUserProfileDetailsById(Long userId) {
        try {
            String query = "SELECT * FROM marshall_service.view_user_details vsd, marshall_service.profile_contents pc where vsd.user_id=pc.profile_user_id and vsd.user_id=:userId";
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("userId", userId),
                    BeanPropertyRowMapper.newInstance(PersistentUserDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<PersistentUserDetails> getUserProfileDetailsById(Long userId) {
        try {
            String query = "SELECT user_id, first_name, middle_name, last_name, user_name, phone, mobile, email, alternate_email, role_name, type_name, permission_name, join_date, vsd.is_deleted, is_enable, " +
                    "profile_photo_name, profile_photo_path, profile_photo_size, aadhar_number, paytm_number, aadhar_back_photo_name, aadhar_back_photo_path, aadhar_back_photo_size, " +
                    "aadhar_front_photo_name, aadhar_front_photo_path, aadhar_front_photo_size, rickshaw_number, rickshaw_front_photo_name, rickshaw_front_photo_path, " +
                    "rickshaw_front_photo_size, rickshaw_back_photo_name, rickshaw_back_photo_path, rickshaw_back_photo_size, rickshaw_side_photo_name, rickshaw_side_photo_path, " +
                    "rickshaw_side_photo_size, upload_date, modify_date " +
                    "FROM marshall_service.view_incomplete_user_details vsd " +
                    "LEFT OUTER JOIN " +
                    "marshall_service.profile_contents pc on vsd.user_id=pc.profile_user_id WHERE vsd.user_id=:userId";
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("userId", userId),
                    BeanPropertyRowMapper.newInstance(PersistentUserDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<PersistentUserDetails> getAllUsersProfile(String role, int after, int limit, Map filters) {
        try {
            StringBuilder query = new StringBuilder();
            String sql;
            query.append("SELECT * FROM ")
                    .append("(SELECT user_id, first_name, middle_name, last_name, user_name, phone, mobile, email, alternate_email, role_name, type_name, permission_name, join_date, vsd.is_deleted, is_enable, " +
                            "profile_photo_name, profile_photo_path, profile_photo_size, aadhar_number, paytm_number, aadhar_back_photo_name, aadhar_back_photo_path, aadhar_back_photo_size, " +
                            "aadhar_front_photo_name, aadhar_front_photo_path, aadhar_front_photo_size, rickshaw_number, rickshaw_front_photo_name, rickshaw_front_photo_path, " +
                            "rickshaw_front_photo_size, rickshaw_back_photo_name, rickshaw_back_photo_path, rickshaw_back_photo_size, rickshaw_side_photo_name, rickshaw_side_photo_path, " +
                            "rickshaw_side_photo_size, upload_date, modify_date " +
                            "FROM marshall_service.view_incomplete_user_details vsd " +
                            "LEFT OUTER JOIN " +
                            "marshall_service.profile_contents pc on vsd.user_id=pc.profile_user_id WHERE vsd.user_id>:after and vsd.role_name=:roleName order by vsd.user_id limit :limit) upd ");
            sql = applyFilters(filters, query);
            return jdbcTemplate.query(sql, new MapSqlParameterSource().addValue("roleName", role).addValue("after", after)
                            .addValue("limit", limit),
                    BeanPropertyRowMapper.newInstance(PersistentUserDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    private String applyFilters(Map filters, StringBuilder query) {
        String sql;
        if (filters != null || !filters.isEmpty()) {
            query.append("WHERE ");
            HashMap<String, String> filter = (HashMap<String, String>) filters;
            filter.entrySet().stream().forEach(e -> {
                query.append("lower(upd.").append(e.getKey()).append(")='").append(e.getValue().toLowerCase()).append("' ");
                query.append("AND ");
            });
            sql = query.substring(0, query.lastIndexOf("AND "));
        } else {
            sql = query.toString();
        }
        return sql;
    }

}
