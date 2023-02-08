package com.tzs.marshall.service.impl;

import com.google.gson.Gson;
import com.sun.istack.NotNull;
import com.tzs.marshall.bean.DBProperties;
import com.tzs.marshall.bean.Location;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.RideRequest;
import com.tzs.marshall.bean.distnaceMatrix.DistanceDuration;
import com.tzs.marshall.bean.distnaceMatrix.DistanceMatrix;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.repo.RideRequestRepository;
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

import static com.tzs.marshall.constants.Constants.CLOSE;
import static com.tzs.marshall.constants.Constants.NOT_SERVED;

@Service
public class RideRequestHelper {

    @Autowired
    private RideRequestRepository rideRequestRepository;

    private static Logger log = LoggerFactory.getLogger(RideRequestServiceImpl.class);

    public void getTotalEarning(List<RideRequest> allRideBookingRequestsByUserId, Map<String, Object> rideMap) {
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

    public void getTotalEarningOfTheDay(List<RideRequest> closedRides, Map<String, Object> rideMap) {
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

    public List<Long> findNearestAvailableDrivers(RideRequest rideRequest, @NotNull Map<Integer, Location> driverLocations) {
        log.info("finding nearest available driver");
        double bookingRadius = Double.parseDouble(DBProperties.properties.getProperty("BOOKING_RADIUS"));
        String coordinateDelimiter = "%2C";
        String locationDelimiter = "%7C";
        String origins = rideRequest.getPickupLocation().getLatitude() + coordinateDelimiter + rideRequest.getPickupLocation().getLongitude();
        StringBuilder destinationsBuilder = new StringBuilder();
        TreeMap<Integer, Location> sortedMapById = new TreeMap<>(driverLocations);
        sortedMapById.values().forEach(l -> destinationsBuilder.append(l.getLatitude()).append(coordinateDelimiter).append(l.getLongitude()).append(locationDelimiter));
        String destinations = destinationsBuilder.substring(0, destinationsBuilder.lastIndexOf(locationDelimiter));
        Map<Long, DistanceDuration> userIdDistanceDurationMap = calculateDriverAndCustomerDistance(origins, destinations, sortedMapById);

        log.info("Filtering driver for distance gt {}", bookingRadius);
        //filter out those drivers whose distance is more than the booking radius.
        List<Long> nearestAvailableDriver = userIdDistanceDurationMap.entrySet().stream()
                .filter(distanceDuration -> distanceDuration.getValue().getDistance() <= bookingRadius)
                .sorted(Comparator.comparingDouble((Map.Entry<Long, DistanceDuration> o) -> o.getValue().getDuration())
                        .thenComparingDouble(o -> o.getValue().getDistance()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

 /*       List<Location> filteredDrivers = driverLocations.values().stream().filter(loc -> {
            double driverCustomerDistance = calculateDriverAndCustomerDistance(userIdDistanceDurationMap, rideRequest.getPickupLocation().getLatitude(), rideRequest.getPickupLocation().getLongitude(), loc);
            return driverCustomerDistance <= bookingRadius;
        }).collect(Collectors.toList());
        List<Map.Entry<Long, DistanceDuration>> driversDistanceMatrixList = new ArrayList<>(userIdDistanceDurationMap.entrySet());
        List<Long> nearestAvailableDriver = driversDistanceMatrixList.stream().map(Map.Entry::getKey).collect(Collectors.toList());*/
        log.info("nearest available drivers: {}", nearestAvailableDriver);
        return nearestAvailableDriver;
    }

    public Map<Long, DistanceDuration> calculateDriverAndCustomerDistance(String origins, String destinations, TreeMap<Integer, Location> sortedMapById) {
        log.info("Calculating distance for {} drivers", sortedMapById.size());
        Map<Long, DistanceDuration> distanceDurationMap = new HashMap<>();
        //Google Maps Api Call to calculate distance between two points
        Response response = null;
        try {
            String baseURL = DBProperties.properties.getProperty("MAP_BASE_URL");
            String path = DBProperties.properties.getProperty("MAP_DISTANCE_PATH");
            String mapKey = DBProperties.properties.getProperty("MAP_KEY");
            String url = baseURL + path + "?origins=" + origins + "&destinations=" + destinations + "&key=" + mapKey;
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            response = client.newCall(request).execute();

            DistanceMatrix distanceMatrix = new Gson().fromJson(Objects.requireNonNull(response.body()).string(), DistanceMatrix.class);

            if (!Objects.equals(distanceMatrix.getStatus(), "OK")) {
                log.error("Map API call failed with status code: {}", distanceMatrix.getStatus());
                throw new ApiException("Map api call failed");
            }

            /*
             *  Distance Matrix API maintains the order of provided destinations, therefore
             *  associate the driverId for each distance return by Google Maps API.
             *  sortedMapById is a TreeMap sorted by driverId in Ascending order
             *
             */
            Iterator<Integer> iterator = sortedMapById.navigableKeySet().iterator();
            distanceMatrix.getRows().forEach(row -> row.getElements().forEach(element -> {
                if (iterator.hasNext()) {
                    distanceDurationMap
                            .put(iterator.next().longValue(),
                                    new DistanceDuration((Double) element.getDistance().get("value"), (Double) element.getDuration().get("value")));
                }
            }));

            log.info("distance calculation complete");
            return distanceDurationMap;
        } catch (Exception e) {
            log.error("Unable to calculate distance.");
            throw new ApiException("Unable to calculate distance.");
        } finally {
            assert response != null;
            response.close();
        }
    }

    public void rollBack(RideRequest rideRequest, Long bookingRequestId) {
        rideRequest.setBookingStatus(NOT_SERVED);
        rideRequestRepository.updateRideBookingRequestStatusByBookingId(bookingRequestId, rideRequest.getBookingStatus());
        rideRequestRepository.updateDriverBookingStatus(bookingRequestId, null, CLOSE);
        log.info("ride request marked as NOT_SERVED");
    }

    public void broadcastRideRequests(List<Long> nearestAvailableDrivers, RideRequest rideRequest) {
        log.info("broadcasting ride requests");
        Response response = null;
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
            response = client.newCall(request).execute();
            if (response.code() != 200) {
                log.error("unable to broadcast the ride request due to status code: {}", response.code());
                throw new ApiException("Unable to broadcast ride request");
            }
            log.info("ride requests broadcast to drivers: {}", nearestAvailableDrivers);
        } catch (Exception e) {
            log.error("Unable to broadcast ride request to drivers.");
            throw new ApiException("Unable to broadcast ride request to drivers.");
        } finally {
            assert response != null;
            response.close();
        }
    }

    public List<RideRequest> persistNearestAvailableDriverWithBookingId(Long bookingRequestId, List<Long> nearestAvailableDrivers, String bookingStatus) {
        List<RideRequest> persistentNearestAvailableDrivers = new ArrayList<>();
        nearestAvailableDrivers.forEach(d -> {
            RideRequest rideRequest = new RideRequest(bookingRequestId, d, bookingStatus);
            persistentNearestAvailableDrivers.add(rideRequest);
        });
        return persistentNearestAvailableDrivers;
    }

    public void prepareResponse(Map<String, Object> responseMap, RideRequest rideRequest, PersistentUserDetails driver) {
        rideRequest.setCustomerId(null);
        rideRequest.setBookingRequestId(null);
        rideRequest.setDriverId(null);
        PersistentUserDetails driverResponse = new PersistentUserDetails();
        driverResponse.setFirstName(driver.getFirstName());
        driverResponse.setLastName(driver.getLastName());
        driverResponse.setMobile(driver.getMobile());
        driverResponse.setRickshawNumber(driver.getRickshawNumber());
        driverResponse.setRoleName(driver.getRoleName());

        responseMap.put("driver", driverResponse);
        responseMap.put("ride", rideRequest);
    }
}
