package com.tzs.marshall.service.impl;

import com.google.gson.Gson;
import com.sun.istack.NotNull;
import com.tzs.marshall.bean.DBProperties;
import com.tzs.marshall.bean.Location;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.RideRequest;
import com.tzs.marshall.bean.distnaceMatrix.DistanceMatrix;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.repo.RideRequestRepository;
import com.tzs.marshall.repo.UserPostLoginRepository;
import com.tzs.marshall.service.RideRequestService;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.tzs.marshall.constants.Constants.*;

@Service
public class RideRequestServiceImpl implements RideRequestService {

    @Autowired
    private RideRequestRepository rideRequestRepository;
    @Autowired
    private UserPostLoginRepository userPostLoginRepository;

    private static Logger log = LoggerFactory.getLogger(RideRequestServiceImpl.class);

    @Override
    public void handleRideRequest(RideRequest rideRequest, String option) {
    }

    @Override
    public PersistentUserDetails openBookingRequest(RideRequest rideRequest, Long userId) {
        String driverStatus = ON_DUTY;
        String bookingStatus = OPEN;
        Long bookingRequestId = null;
        int randomPin = (int) (Math.random() * 900000) + 100000;
        String otp = String.valueOf(randomPin);
        rideRequest.setBookingStatus(bookingStatus);
        rideRequest.setOtp(otp);
        try {
            //fetch any existing booking status of the customer
            Map<String, Long> statusIdMap = rideRequestRepository.getExistingBookingStatusByUserId(userId);
            //if status is OPEN then update the record else insert new record
            bookingRequestId = statusIdMap.get(OPEN);
            if (Objects.nonNull(bookingRequestId)) {
                rideRequest.setBookingRequestId(Objects.requireNonNull(bookingRequestId));
                rideRequestRepository.updateBookingRequest(rideRequest, userId);
            } else {
                bookingRequestId = rideRequestRepository.saveBookingRequest(rideRequest, userId);
            }
            List<Long> nearestAvailableDrivers = new ArrayList<>();
            int count = 0;
            while (count < 3 && nearestAvailableDrivers.isEmpty()) {
                Map<Integer, Location> driverLocations = rideRequestRepository.fetchDriverLocationsAndIdsByStatus(driverStatus);
                nearestAvailableDrivers = findNearestAvailableDrivers(rideRequest, driverLocations);
                if (!nearestAvailableDrivers.isEmpty()) {
                    break;
                }
                //TODO: ask driver to update their current location
                Thread.sleep(5000);
                count++;
            }
            if (nearestAvailableDrivers.isEmpty()) {
                log.error("There is no nearest driver available.");
                throw new ApiException("There is no available driver at this moment");
            }
            List<RideRequest> persistentNearestDrivers = persistNearestAvailableDriverWithBookingId(bookingRequestId, nearestAvailableDrivers, bookingStatus);
            rideRequestRepository.insertNewRequestForNearestAvailableDrivers(persistentNearestDrivers);
            //broadcast ride requests
            Response response = broadcastRideRequests(nearestAvailableDrivers, rideRequest);
            if (response.code() != 200) {
                log.error("unable to broadcast the ride request due to status code: {}", response.code());
                throw new ApiException("Unable to broadcast ride request");
            }

            //wait for the driver to accept the request
            count = 0;
            List<Integer> acceptedDriverId = new ArrayList<>();
            long interval = Long.parseLong(DBProperties.properties.getProperty("REQUEST_ACCEPT_INTERVAL"));
            while (count < 3 && acceptedDriverId.isEmpty()) {
                log.info("Waiting for drivers to accept the request");
                Thread.sleep(interval);
                //get the driver detail who has accepted the booking request
                acceptedDriverId = rideRequestRepository.getDriverBookingRequestByStatusAndBookingId(bookingRequestId, ACCEPT);
                if (!acceptedDriverId.isEmpty()) {
                    break;
                }
                count++;
            }
            //update driver details in booking request: this has been handled at acceptRideBookingRequest method
            //send customer/booking request details to driver : this has been handled while a driver accept the booking request
            // and driver details to customer i.e. send profile pic, rickshaw pics as well
            if (!acceptedDriverId.isEmpty()) {
                List<PersistentUserDetails> driverDetails = userPostLoginRepository.getUserProfileAndEssentialDetailsById((long) acceptedDriverId.stream().findFirst().get());
                return driverDetails.stream().findFirst().get();
            } else {
                log.error("No driver has accepted the request");
                throw new ApiException("No driver has accepted the request");
            }
        } catch (Exception e) {
            log.error("Something went wrong: {}", e.getMessage());
            rollBack(rideRequest, bookingRequestId);
            throw new ApiException(e.getMessage());
        }
    }

    @Override
    public List<RideRequest> fetchRideBookingRequestsForDriversByDBSAndDriverId(Long userId, String status) {
        List<RideRequest> allBookingRequestsByIdAndStatus = rideRequestRepository.getAllRideBookingRequestsByDBSAndDriverId(userId, status.toUpperCase());
        return allBookingRequestsByIdAndStatus;
    }

    @Override
    public List<RideRequest> fetchRideBookingRequestsByUserId(Long userId) {
        return rideRequestRepository.getAllRideBookingRequestsByUserId(userId);
    }

    @Override
    public void updateDriverDutyStatus(Long userId, String status) {
        if (!ON_DUTY.equalsIgnoreCase(status) && !OFF_DUTY.equalsIgnoreCase(status)) {
            throw new ApiException(String.format("%s is not a valid status. Please use %s or %s", status, ON_DUTY, OFF_DUTY));
        }
        rideRequestRepository.updateDriverDutyStatusById(userId, status.toUpperCase());
    }

    @Override
    public void writeUserLocation(Location location) {
        rideRequestRepository.writeUserCurrentLocation(location);
    }

    @Override
    public void updateRideBookingStatus(String bookingRequestId, String status) {
        if (CLOSE.equalsIgnoreCase(status)) {
            List<RideRequest> rideBookingRequestByBookingId = rideRequestRepository.getRideBookingRequestByBookingId(Long.valueOf(bookingRequestId));
            RideRequest rideRequest = rideBookingRequestByBookingId.stream().findFirst().orElse(new RideRequest());
            if (PAID.equalsIgnoreCase(rideRequest.getPaymentStatus())) {
                rideRequestRepository.updateRideBookingRequestStatusByBookingId(Long.valueOf(bookingRequestId), status.toUpperCase());
            } else {
                throw new ApiException(MessageConstants.PAYMENT_ERR);
            }
        } else {
            rideRequestRepository.updateRideBookingRequestStatusByBookingId(Long.valueOf(bookingRequestId), status.toUpperCase());
        }
    }

    @Override
    public RideRequest acceptRideBookingRequest(String bookingRequestId, Long driverId) {
        List<Integer> acceptedDrivers = rideRequestRepository.getDriverBookingRequestByStatusAndBookingId(Long.valueOf(bookingRequestId), ACCEPT);
        if (acceptedDrivers != null || !acceptedDrivers.isEmpty()) {
            rideRequestRepository.updateDriverBookingStatus(Long.valueOf(bookingRequestId), null, CLOSE);
            rideRequestRepository.updateDriverBookingStatus(Long.valueOf(bookingRequestId), driverId, ACCEPT);
            rideRequestRepository.acceptRideBookingRequest(Long.valueOf(bookingRequestId), driverId, BOOK);
            List<RideRequest> bookingRequestByBookingId = rideRequestRepository.getRideBookingRequestByBookingId(Long.valueOf(bookingRequestId));
            return bookingRequestByBookingId.stream().findFirst().orElse(new RideRequest());
        } else {
            log.error("{} bookingRequest has already been accepted by driverId: {}", bookingRequestId, acceptedDrivers);
            throw new ApiException("This request is already accepted");
        }
    }

    @Override
    public void rejectRideBookingRequest(String bookingRequestId, Long driverId) {
        rideRequestRepository.rejectRideBookingRequest(Long.valueOf(bookingRequestId), driverId);
    }

    @Override
    public String getDriverDutyStatus(Long userId) {
        return rideRequestRepository.getDriverDutyStatusById(userId);
    }

    @Override
    public RideRequest verifyOtpAndStartRide(String otp, String bookingRequestId) {
        List<RideRequest> rideBookingRequestByBookingId = rideRequestRepository.getRideBookingRequestByBookingId(Long.valueOf(bookingRequestId));
        RideRequest rideRequest = rideBookingRequestByBookingId.stream().findFirst().orElse(new RideRequest());
        if (rideRequest.getOtp().equalsIgnoreCase(otp)) {
            rideRequestRepository.updateRideBookingRequestStatusByBookingId(Long.valueOf(bookingRequestId), START);
            rideRequest.setBookingStatus(START);
        } else {
            throw new ApiException(MessageConstants.INVALID_TOKEN);
        }
        return rideRequest;
    }

    @Override
    public Boolean updatePaymentStatusOfRideBookingRequest(String bookingRequestId, String paymentStatus) {
        int i = rideRequestRepository.updatePaymentStatusByRideBookingRequestId(Long.valueOf(bookingRequestId), paymentStatus.toUpperCase());
        return i == 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Map<String, Object> getTotalEarningByDriver(Long userId) {

        Map<String, Object> rideMap = new HashMap<>();
        List<RideRequest> allRideBookingRequestsByUserId = rideRequestRepository.getAllRideBookingRequestsByUserId(userId);
        getTotalEarning(allRideBookingRequestsByUserId, rideMap);
        List<RideRequest> closedRides = (List<RideRequest>) rideMap.get("totalRides");
        getTotalEarningOfTheDay(closedRides, rideMap);
        return rideMap;
    }

    @Override
    public void createOrUpdateFirebaseToken(Long userId, String token) {
        rideRequestRepository.insertOrUpdateFirebaseTokenById(userId, token);
    }

    @Override
    public String getFirebaseTokenById(Long userId) {
        Map<Long, String> firebaseTokenByDriverId = rideRequestRepository.getFirebaseTokenByDriverId(List.of(userId));
        return firebaseTokenByDriverId.get(userId);
    }

    private void getTotalEarning(List<RideRequest> allRideBookingRequestsByUserId, Map<String, Object> rideMap) {
        AtomicReference<Double> totalEarnings = new AtomicReference<>(0D);
        List<RideRequest> closedRides = new ArrayList<>();
        allRideBookingRequestsByUserId.stream().filter(ride -> CLOSE.equalsIgnoreCase(ride.getBookingStatus()))
                .forEach(ride -> {
                    totalEarnings.set(totalEarnings.get() + ride.getFare());
                    closedRides.add(ride);
                });
        Double commission = ((Double.parseDouble(DBProperties.properties.getProperty("COMMISSION")) * totalEarnings.get()) / 100);
        Double balance = totalEarnings.get() - commission;
        rideMap.put("totalEarning", totalEarnings);
        rideMap.put("totalRides", closedRides);
        rideMap.put("totalCommission", commission);
        rideMap.put("balance", balance);
    }

    private void getTotalEarningOfTheDay(List<RideRequest> closedRides, Map<String, Object> rideMap) {
        AtomicReference<Double> totalEarningsOfTheDay = new AtomicReference<>(0D);
        List<RideRequest> closedRidesOfTheDay = new ArrayList<>();
        closedRides.stream().filter(ride -> ride.getDate().equals(Date.valueOf(LocalDate.now())))
                .forEach(ride -> {
                    totalEarningsOfTheDay.set(totalEarningsOfTheDay.get() + ride.getFare());
                    closedRidesOfTheDay.add(ride);
                });
        Double totalCommissionOfTheDay = ((Double.parseDouble(DBProperties.properties.getProperty("COMMISSION")) * totalEarningsOfTheDay.get()) / 100);
        Double balanceOfTheDay = totalEarningsOfTheDay.get() - totalCommissionOfTheDay;

        rideMap.put("totalEarningOfTheDay", totalEarningsOfTheDay.get());
        rideMap.put("totalRidesOfTheDay", closedRidesOfTheDay);
        rideMap.put("totalCommissionOfTheDay", totalCommissionOfTheDay);
        rideMap.put("balanceOfTheDay", balanceOfTheDay);
    }

    private List<Long> findNearestAvailableDrivers(RideRequest rideRequest, @NotNull Map<Integer, Location> driverLocations) {
        List<Long> nearestDriverIds = driverLocations.values().stream().filter(loc -> {
            double driverCustomerDistance = calculateDriverAndCustomerDistance(rideRequest.getPickupLocation().getLatitude(), rideRequest.getPickupLocation().getLongitude(), loc.getLatitude(), loc.getLongitude());
            return driverCustomerDistance < Double.parseDouble(DBProperties.properties.getProperty("BOOKING_RADIUS"));
        }).map(Location::getUserId).collect(Collectors.toList());
        return nearestDriverIds;
    }

    private Double calculateDriverAndCustomerDistance(Double customerPickupLatitude, Double customerPickupLongitude, Double driverLatitude, Double driverLongitude) {
        //Google Maps Api Call to calculate distance between two points
        try {
            String baseURL = DBProperties.properties.getProperty("MAP_BASE_URL");
            String path = DBProperties.properties.getProperty("MAP_DISTANCE_PATH");
            String originLatitude = String.valueOf(customerPickupLatitude);
            String originLongitude = String.valueOf(customerPickupLongitude);
            String destinationLatitude = String.valueOf(driverLatitude);
            String destinationLongitude = String.valueOf(driverLongitude);
            String mapDelimiter = "%2C";
            String mapKey = DBProperties.properties.getProperty("MAP_KEY");
            String url = baseURL + path + "?origins=" + originLatitude + mapDelimiter + originLongitude + "&destinations=" + destinationLatitude + mapDelimiter + destinationLongitude + "&key=" + mapKey;
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            DistanceMatrix distanceMatrix = new Gson().fromJson(Objects.requireNonNull(response.body()).string(), DistanceMatrix.class);
            if (!Objects.equals(distanceMatrix.getStatus(), "200")) {
                log.error("Map API call failed with status code: {}", distanceMatrix.getStatus());
                throw new ApiException("Map api call failed");
            }
            AtomicReference<Double> dist = new AtomicReference<>();
            distanceMatrix.getRows().forEach(r -> r.getElements().forEach(e -> dist.set((Double) e.getDistance().get("value"))));
            return (dist.get() / 1000.0);
        } catch (Exception e) {
            log.error("Unable to calculate distance.");
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    private void rollBack(RideRequest rideRequest, Long bookingRequestId) {
        rideRequest.setBookingStatus(NOT_SERVED);
        rideRequestRepository.updateRideBookingRequestStatusByBookingId(bookingRequestId, rideRequest.getBookingStatus());
        rideRequestRepository.updateDriverBookingStatus(bookingRequestId, null, CLOSE);
    }

    private Response broadcastRideRequests(List<Long> nearestAvailableDrivers, RideRequest rideRequest) {
        try {
            //fetch firebase token for nearest available drivers
            Map<Long, String> firebaseTokenByDriverId = rideRequestRepository.getFirebaseTokenByDriverId(nearestAvailableDrivers);
            //broadcast the booking request to the nearest available drivers using firebase post api call
            List<String> registrationIdList = new ArrayList<>(firebaseTokenByDriverId.values());
//            List<String> registrationIdList = List.of("cmtZQwzPT0uNaLuAHc1uhY:APA91bG-_3A4TiX8aDvAESG_DhzTOJr63jMBSLIBQ7BQaI9ENGddxROTF5VD2tMrC4i9e2TBcq1vwCQ_hyD-DYGBFDgVJbuRbwZBoIEra1dgDuLmxV3nor19gpDkkXWOOxv7UBQLebyE", "c1Vxh7cJT0e0qTpanGqCAW:APA91bHRfo2gH9hJXkqqDTSFjGsVNIa9HCpeFjNUL8ZActJf5zsDmmRtbbo5rBSPRw42KNZjk_ZZOIVeZAJ-8Qun-iaTeicoA2V2MZZv3_RTIvFC6PIgLaqpaztPXtHMI8_ZvOQjnf0q", "cJjodo7cSZm50Kxa_2slLu:APA91bHoOOT41IcYvV8mFAwZJ_t3X-KrypTfBbZlePEYRiKq4KqXN698X7c7dVrSv9hWNDJKEZf-RP8oC5E-VokPk2trjcNkxEoEw5Vv7Xm2rsiGV_GI5R_8R3dwfaF6EwUJtHD3vuZn");
            String registrationIds = registrationIdList.stream().collect(Collectors.joining("\",\"", "\"", "\""));

            String baseURL = DBProperties.properties.getProperty("FIREBASE_URL");
            String serverKey = DBProperties.properties.getProperty("FIREBASE_KEY");
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            String jsonString = "{\"registration_ids\":[" + registrationIds + "],\"notification\":{\"title\":\"New Ride Request\",\"body\":{\"Number of Passengers\":\" " + rideRequest.getPassengers() + " \",\"Pickup Location\":\" " + rideRequest.getPickupLocationWord() + " \"}}}";
            RequestBody body = RequestBody.create(jsonString, mediaType);
            Request request = new Request.Builder()
                    .url(baseURL)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "key=" + serverKey)
                    .method("POST", body)
                    .build();
            Response response = client.newCall(request).execute();
            return response;
        } catch (Exception e) {
            log.error("Unable to broadcast ride request to drivers.");
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    private List<RideRequest> persistNearestAvailableDriverWithBookingId(Long bookingRequestId, List<Long> nearestAvailableDrivers, String bookingStatus) {
        List<RideRequest> persistentNearestAvailableDrivers = new ArrayList<>();
        nearestAvailableDrivers.forEach(d -> {
            RideRequest rideRequest = new RideRequest(bookingRequestId, d, bookingStatus);
            persistentNearestAvailableDrivers.add(rideRequest);
        });
        return persistentNearestAvailableDrivers;
    }

}
