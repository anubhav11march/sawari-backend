package com.tzs.marshall.repo.admin;

import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.PersistentUserRights;
import com.tzs.marshall.bean.RideRequest;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.*;

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
            return jdbcTemplate.query(query, mapSqlParameterSource,
                    BeanPropertyRowMapper.newInstance(PersistentUserDetails.class));
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
            return jdbcTemplate.query(query, mapSqlParameterSource,
                    BeanPropertyRowMapper.newInstance(PersistentUserDetails.class));
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
            String query = "SELECT * FROM marshall_service.view_user_details vsd, marshall_service.profile_contents pc "
                    +
                    "WHERE vsd.user_id=pc.profile_user_id and vsd.role_name=:roleName and vsd.user_id>:after order by vsd.user_id limit :limit";
            return jdbcTemplate.query(query,
                    new MapSqlParameterSource().addValue("roleName", role).addValue("after", after)
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
            String query = "SELECT * FROM marshall_service.view_incomplete_user_details vsd, marshall_service.profile_contents pc "
                    +
                    "WHERE vsd.user_id=pc.profile_user_id and vsd.role_name=:roleName and vsd.user_id>:after order by vsd.user_id limit :limit";
            return jdbcTemplate.query(query,
                    new MapSqlParameterSource().addValue("roleName", role).addValue("after", after)
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
            String query = "SELECT user_id, first_name, middle_name, last_name, user_name, phone, mobile, email, alternate_email, role_name, type_name, permission_name, join_date, vsd.is_deleted, is_enable, "
                    +
                    "profile_photo_name, profile_photo_path, profile_photo_size, aadhar_number, paytm_number, aadhar_back_photo_name, aadhar_back_photo_path, aadhar_back_photo_size, "
                    +
                    "aadhar_front_photo_name, aadhar_front_photo_path, aadhar_front_photo_size, rickshaw_number, rickshaw_front_photo_name, rickshaw_front_photo_path, "
                    +
                    "rickshaw_front_photo_size, rickshaw_back_photo_name, rickshaw_back_photo_path, rickshaw_back_photo_size, rickshaw_side_photo_name, rickshaw_side_photo_path, "
                    +
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
    public List<PersistentUserDetails> getAllUsersProfile(String role, Map filters) {
        try {
            StringBuilder query = new StringBuilder();
            String sql;
            query.append("SELECT * FROM ")
                    .append("(SELECT user_id, first_name, middle_name, last_name, user_name, phone, mobile, email, alternate_email, role_name, type_name, permission_name, join_date, vsd.is_deleted, is_enable, "
                            +
                            "profile_photo_name, profile_photo_path, profile_photo_size, aadhar_number, paytm_number, aadhar_back_photo_name, aadhar_back_photo_path, aadhar_back_photo_size, "
                            +
                            "aadhar_front_photo_name, aadhar_front_photo_path, aadhar_front_photo_size, rickshaw_number, rickshaw_front_photo_name, rickshaw_front_photo_path, "
                            +
                            "rickshaw_front_photo_size, rickshaw_back_photo_name, rickshaw_back_photo_path, rickshaw_back_photo_size, rickshaw_side_photo_name, rickshaw_side_photo_path, "
                            +
                            "rickshaw_side_photo_size, upload_date, modify_date " +
                            "FROM marshall_service.view_incomplete_user_details vsd " +
                            "LEFT OUTER JOIN " +
                            "marshall_service.profile_contents pc on vsd.user_id=pc.profile_user_id WHERE vsd.role_name=:roleName order by vsd.user_id) upd ");
            sql = applyFilters(filters, query);
            return jdbcTemplate.query(sql, new MapSqlParameterSource().addValue("roleName", role),
                    BeanPropertyRowMapper.newInstance(PersistentUserDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<RideRequest> getAllUsersRides(int after, int limit, Map filters) {
        try {
            StringBuilder query = new StringBuilder();
            String sql;
            query.append("SELECT * FROM ")
                    .append("(SELECT " +
                            "  concat(uc.first_name, ' ', uc.last_name) AS customer_name, " +
                            "  concat(ud.first_name, ' ', ud.last_name) AS driver_name, " +
                            "  date, booking_request_id, customer_id, mobile_no, pickup_location_points, pickup_location_word, drop_location_points, drop_location_word, passengers, distance, fare, discount, currency, otp, booking_status, payment_mode, payment_status, driver_id, modify_date "
                            +
                            "FROM " +
                            "  marshall_service.ride_request r " +
                            "  LEFT JOIN marshall_service.view_incomplete_user_details uc ON r.customer_id = uc.user_id "
                            +
                            "  LEFT JOIN marshall_service.view_incomplete_user_details ud ON r.driver_id = ud.user_id "
                            +
                            "  WHERE r.booking_request_id>:after ORDER BY r.booking_request_id LIMIT :limit) upd ");
            sql = applyFilters(filters, query).concat(" ORDER BY date DESC ");
            return jdbcTemplate.query(sql, new MapSqlParameterSource().addValue("after", after)
                    .addValue("limit", limit),
                    BeanPropertyRowMapper.newInstance(RideRequest.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<RideRequest> getAllUsersRidesById(Long userId) {
        try {
            StringBuilder query = new StringBuilder();
            String sql;
            query.append("SELECT * FROM ")
                    .append("(SELECT " +
                            "  concat(uc.first_name, ' ', uc.last_name) AS customer_name, " +
                            "  concat(ud.first_name, ' ', ud.last_name) AS driver_name, " +
                            "  date, booking_request_id, customer_id, mobile_no, pickup_location_points, pickup_location_word, drop_location_points, drop_location_word, passengers, distance, fare, discount, currency, otp, booking_status, payment_mode, payment_status, driver_id, modify_date "
                            +
                            "FROM " +
                            "  marshall_service.ride_request r " +
                            "  LEFT JOIN marshall_service.view_incomplete_user_details uc ON r.customer_id = uc.user_id "
                            +
                            "  LEFT JOIN marshall_service.view_incomplete_user_details ud ON r.driver_id = ud.user_id "
                            +
                            "  WHERE r.customer_id=:userId OR r.driver_id=:userId) upd " +
                            "  ORDER BY date DESC ");
            sql = query.toString();
            return jdbcTemplate.query(sql, new MapSqlParameterSource().addValue("userId", userId),
                    BeanPropertyRowMapper.newInstance(RideRequest.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public void updateDBProperties(Map<String, String> properties) {
        try {
            String query = "UPDATE marshall_service.properties SET value=? WHERE name=?";
            List<Object[]> params = new ArrayList<>();

            properties.entrySet().forEach(e -> {
                params.add(new Object[] { e.getValue(), e.getKey() });
            });
            jdbcTemplate.getJdbcOperations().batchUpdate(query, params);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public void uploadQRCodeToDB(String qrCodeName, String qrCodePath) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("name", qrCodeName).addValue("value", qrCodePath);
            new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getJdbcTemplate().getDataSource()))
                    .withCatalogName("marshall_service")
                    .withTableName("properties")
                    .usingColumns("name", "value")
                    .execute(mapSqlParameterSource);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public void updateQRCode(String qrCodeName, String path) {
        try {
            String query = "UPDATE marshall_service.properties SET value=:path WHERE name=:qrCodeName";
            jdbcTemplate.update(query,
                    new MapSqlParameterSource().addValue("path", path).addValue("qrCodeName", qrCodeName));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    private String applyFilters(Map filters, StringBuilder query) {
        String sql;
        if (filters != null && !filters.isEmpty()) {
            query.append("WHERE ");
            HashMap<String, String> filter = (HashMap<String, String>) filters;
            filter.entrySet().stream().forEach(e -> {
                query.append("lower(upd.").append(e.getKey()).append(")='").append(e.getValue().toLowerCase())
                        .append("' ");
                query.append("AND ");
            });
            sql = query.substring(0, query.lastIndexOf("AND "));
        } else {
            sql = query.toString();
        }
        return sql;
    }

}
