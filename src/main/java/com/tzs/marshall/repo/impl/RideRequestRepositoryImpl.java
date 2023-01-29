package com.tzs.marshall.repo.impl;

import com.tzs.marshall.bean.Location;
import com.tzs.marshall.bean.RideRequest;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.repo.RideRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.tzs.marshall.constants.Constants.REJECT;

@Repository
public class RideRequestRepositoryImpl implements RideRequestRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(RideRequestRepositoryImpl.class);

    public RideRequestRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Integer> fetchDriverIdsByStatus(String status) {
        try {
            String query = "Select driver_id from marshall_service.driver_status where status=:status";
            return jdbcTemplate
                    .query(query,
                            new MapSqlParameterSource().addValue("status", status),
                            BeanPropertyRowMapper.newInstance(Integer.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }

    }

    @Override
    public Map<Integer, Location> fetchDriverLocationsAndIdsByStatus(String driverStatus) {
        try {
            Map<Integer, Location> locationMap = new HashMap<>();
            String query = "SELECT ul.user_id, ul.latitude, ul.longitude FROM marshall_service.user_location ul, marshall_service.driver_status ds " +
                    "WHERE ul.user_id = ds.driver_id AND ds.status =:driverStatus";
            jdbcTemplate.query(query, new MapSqlParameterSource().addValue("driverStatus", driverStatus),
                    (rs, rowNum) -> locationMap.put(rs.getInt("user_id"), new Location(rs.getLong("user_id"), rs.getString("latitude"), rs.getString("longitude"))));
            return locationMap;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public long saveBookingRequest(RideRequest rideRequest, Long userId) {
        long bookingRequestId = 0;
        MapSqlParameterSource mapSqlParameterSource = getMapSqlParameterSource(rideRequest, userId);
        try {
            Number key = new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getJdbcTemplate().getDataSource()))
                    .withTableName("ride_request")
                    .usingColumns("customer_id", "mobile_no", "pickup_location_points", "drop_location_points", "passengers", "fare", "currency", "distance", "otp", "booking_status", "payment_mode", "payment_status")
                    .usingGeneratedKeyColumns("booking_request_id")
                    .executeAndReturnKey(mapSqlParameterSource);
            bookingRequestId = Long.parseLong(String.valueOf(key));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
        return bookingRequestId;
    }

    @Override
    public void insertNewRequestForNearestAvailableDrivers(List<RideRequest> persistentNearestDrivers) {
        try {
            String query = "INSERT INTO marshall_service.driver_booking_status (booking_request_id, driver_id, booking_status) " +
                    "VALUES (?,?,?)";
            List<Object[]> objects = new ArrayList<>();
            persistentNearestDrivers.forEach(d -> {
                objects.add(new Object[] {d.getBookingRequestId(), d.getDriverId(), d.getBookingStatus()});
            });
            jdbcTemplate.getJdbcOperations().batchUpdate(query, objects);
        } catch (Exception e) {
            log.error(e.getMessage());
            if (!e.getMessage().contains("Duplicate entry"))
                throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public Map<String, Long> getExistingBookingStatusByUserId(Long userId) {
        try {
            Map<String, Long> statusIdMap = new HashMap<>();
            String query = "SELECT booking_request_id, booking_status FROM marshall_service.ride_request WHERE customer_id=:userId ORDER BY date DESC LIMIT 1";
            jdbcTemplate.query(query, new MapSqlParameterSource().addValue("userId", userId),
                    (rs, rowNum) -> statusIdMap.put(rs.getString("booking_status"), rs.getLong("booking_request_id")));
            return statusIdMap;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public void updateBookingRequest(RideRequest rideRequest, Long userId) {
        try {
            String query = "UPDATE marshall_service.ride_request SET " +
                    "pickup_location_points=:pickup_location_points, drop_location_points=:drop_location_points, passengers=:passengers, fare=:fare, currency=:currency, distance=:distance, otp=:otp, " +
                    "booking_status=:booking_status, payment_mode=:payment_mode, payment_status=:payment_status, date=:date " +
                    "WHERE booking_request_id=:booking_request_id";
            jdbcTemplate.update(query, getMapSqlParameterSource(rideRequest, userId));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<RideRequest> getAllRideBookingRequestsByDBSAndDriverId(Long driverId, String option) {
        try {
            String query = "SELECT rr.* FROM marshall_service.ride_request rr, marshall_service.driver_booking_status dbs WHERE " +
                    "rr.booking_request_id=dbs.booking_request_id AND dbs.driver_id=:driverId AND dbs.booking_status=:option";
            List<RideRequest> rideRequests = jdbcTemplate.query(query, new MapSqlParameterSource().addValue("driverId", driverId).addValue("option", option),
                    BeanPropertyRowMapper.newInstance(RideRequest.class));
            return rideRequests;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<RideRequest> getAllRideBookingRequestsByUserId(Long userId) {
        try {
            String query = "SELECT * FROM marshall_service.ride_request WHERE customer_id=:userId OR driver_id=:userId";
            List<RideRequest> rideRequests = jdbcTemplate.query(query, new MapSqlParameterSource().addValue("userId", userId),
                    BeanPropertyRowMapper.newInstance(RideRequest.class));
            return rideRequests;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public void updateDriverDutyStatusById(Long userId, String status) {
        try {
            String query = "INSERT INTO marshall_service.driver_status (driver_id, status) VALUES (:driverId,:status) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "status=:status";
            jdbcTemplate.update(query, new MapSqlParameterSource().addValue("driverId", userId).addValue("status", status));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public void writeUserCurrentLocation(Location location) {
        try {
            String query = "INSERT INTO marshall_service.user_location (user_id, latitude, longitude) VALUES (:userId,:latitude, :longitude) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "latitude=:latitude, longitude=:longitude";
            jdbcTemplate.update(query, new MapSqlParameterSource().addValue("userId", location.getUserId())
                    .addValue("latitude", location.getLatitude())
                    .addValue("longitude", location.getLongitude()));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public void updateRideBookingRequestStatusByBookingId(Long bookingRequestId, String status) {
        try {
            String query = "UPDATE marshall_service.ride_request SET booking_status=:booking_status " +
                    "WHERE booking_request_id=:booking_request_id";
            jdbcTemplate.update(query, new MapSqlParameterSource().addValue("booking_request_id", bookingRequestId)
                    .addValue("booking_status", status));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public void acceptRideBookingRequest(Long bookingRequestId, Long driverId, String status) {
        try {
            String query = "UPDATE marshall_service.ride_request SET booking_status=:status, driver_id=:driverId " +
                    "WHERE booking_request_id=:bookingRequestId";
            jdbcTemplate.update(query, new MapSqlParameterSource().addValue("status", status)
                    .addValue("driverId", driverId)
                    .addValue("bookingRequestId", bookingRequestId));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public void updateDriverBookingStatus(Long bookingRequestId, Long driverId, String status) {
        try {
            String queryWithDriverId = "UPDATE marshall_service.driver_booking_status SET booking_status=:status " +
                    "WHERE booking_request_id=:bookingRequestId AND driver_id=:driverId";
            String queryWithoutDriverId = "UPDATE marshall_service.driver_booking_status SET booking_status=:status " +
                    "WHERE booking_request_id=:bookingRequestId";
            String query = driverId != null ? queryWithDriverId : queryWithoutDriverId;
            jdbcTemplate.update(query, new MapSqlParameterSource().addValue("status", status)
                    .addValue("bookingRequestId", bookingRequestId)
                    .addValue("driverId", driverId));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<RideRequest> getRideBookingRequestByBookingId(Long bookingRequestId) {
        try {
            String query = "SELECT * FROM marshall_service.ride_request WHERE booking_request_id=:bookingRequestId";
            return jdbcTemplate.query(query, new MapSqlParameterSource().addValue("bookingRequestId", bookingRequestId),
                    BeanPropertyRowMapper.newInstance(RideRequest.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public void rejectRideBookingRequest(Long bookingRequestId, Long driverId) {
            updateDriverBookingStatus(bookingRequestId, driverId, REJECT);
            updateRideBookingRequestStatusByBookingId(bookingRequestId, REJECT);
    }

    @Override
    public Map<Integer, String> getDriverDutyStatusById(Long userId) {
        try {
            Map<Integer, String> driverStatusMap = new HashMap<>();
            String query = "SELECT driver_id, status FROM marshall_service.driver_status WHERE driver_id=:userId";
            jdbcTemplate.query(query, new MapSqlParameterSource().addValue("userId", userId),
                    (rs, rowNum) -> driverStatusMap.put(rs.getInt("driver_id"), rs.getString("status")));
            return driverStatusMap;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    private MapSqlParameterSource getMapSqlParameterSource(RideRequest rideRequest, Long userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("booking_request_id", rideRequest.getBookingRequestId());
        mapSqlParameterSource.addValue("customer_id", userId);
        mapSqlParameterSource.addValue("mobile_no", rideRequest.getMobileNo());
        mapSqlParameterSource.addValue("pickup_location_points", rideRequest.getPickupLocationPoints());
        mapSqlParameterSource.addValue("drop_location_points", rideRequest.getDropLocationPoints());
        mapSqlParameterSource.addValue("passengers", rideRequest.getPassengers());
        mapSqlParameterSource.addValue("fare", rideRequest.getFare());
        mapSqlParameterSource.addValue("currency", rideRequest.getCurrency());
        mapSqlParameterSource.addValue("distance", rideRequest.getDistance());
        mapSqlParameterSource.addValue("otp", rideRequest.getOtp());
        mapSqlParameterSource.addValue("booking_status", rideRequest.getBookingStatus());
        mapSqlParameterSource.addValue("payment_mode", rideRequest.getPaymentMode());
        mapSqlParameterSource.addValue("payment_status", rideRequest.getPaymentStatus());
        mapSqlParameterSource.addValue("date", Timestamp.valueOf(LocalDateTime.now()));
        return mapSqlParameterSource;
    }
}
