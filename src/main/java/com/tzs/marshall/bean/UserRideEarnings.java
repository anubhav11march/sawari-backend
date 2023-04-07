package com.tzs.marshall.bean;

import java.util.List;
import java.util.Map;

import static com.tzs.marshall.constants.Constants.DRIVER;

public class UserRideEarnings {
    PersistentUserDetails userDetails;
    Double totalEarningOfTheDay;
    List<RideRequest> totalRidesOfTheDay;
    Double totalCommissionOfTheDay;
    Double balanceOfTheDay;

    Double totalEarning;
    List<RideRequest> totalRides;
    Double totalCommission;
    Double balance;

    Map<String, Object> userRideEarningsMap;

    public UserRideEarnings() {
    }

    public UserRideEarnings(PersistentUserDetails userDetails, Map<String, Object> userRideEarningsMap) {
        this.userDetails = userDetails;
        this.totalRidesOfTheDay = (List<RideRequest>) userRideEarningsMap.get("totalRidesOfTheDay");
        this.totalRides = (List<RideRequest>) userRideEarningsMap.get("totalRides");
        if (DRIVER.equalsIgnoreCase(userDetails.getRoleName())) {
            this.setUserRideEarningsMap(userRideEarningsMap);
        }
    }

    public UserRideEarnings(PersistentUserDetails userDetails, Double totalEarningOfTheDay, List<RideRequest> totalRidesOfTheDay, Double totalCommissionOfTheDay, Double balanceOfTheDay, Double totalEarning, List<RideRequest> totalRides, Double totalCommission, Double balance) {
        this.userDetails = userDetails;
        this.totalEarningOfTheDay = totalEarningOfTheDay;
        this.totalRidesOfTheDay = totalRidesOfTheDay;
        this.totalCommissionOfTheDay = totalCommissionOfTheDay;
        this.balanceOfTheDay = balanceOfTheDay;
        this.totalEarning = totalEarning;
        this.totalRides = totalRides;
        this.totalCommission = totalCommission;
        this.balance = balance;
    }

    public void setUserRideEarningsMap(Map<String, Object> rideMap) {
        this.userRideEarningsMap = rideMap;
        this.totalEarningOfTheDay = (Double) rideMap.get("totalEarningOfTheDay");;
        this.totalCommissionOfTheDay = (Double) rideMap.get("totalCommissionOfTheDay");
        this.balanceOfTheDay = (Double) rideMap.get("balanceOfTheDay");
        this.totalEarning = (Double) rideMap.get("totalEarning");
        this.totalCommission = (Double) rideMap.get("totalCommission");
        this.balance = (Double) rideMap.get("balance");
    }

    public PersistentUserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(PersistentUserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public Double getTotalEarningOfTheDay() {
        return totalEarningOfTheDay;
    }

    public void setTotalEarningOfTheDay(Double totalEarningOfTheDay) {
        this.totalEarningOfTheDay = totalEarningOfTheDay;
    }

    public List<RideRequest> getTotalRidesOfTheDay() {
        return totalRidesOfTheDay;
    }

    public void setTotalRidesOfTheDay(List<RideRequest> totalRidesOfTheDay) {
        this.totalRidesOfTheDay = totalRidesOfTheDay;
    }

    public Double getTotalCommissionOfTheDay() {
        return totalCommissionOfTheDay;
    }

    public void setTotalCommissionOfTheDay(Double totalCommissionOfTheDay) {
        this.totalCommissionOfTheDay = totalCommissionOfTheDay;
    }

    public Double getBalanceOfTheDay() {
        return balanceOfTheDay;
    }

    public void setBalanceOfTheDay(Double balanceOfTheDay) {
        this.balanceOfTheDay = balanceOfTheDay;
    }

    public Double getTotalEarning() {
        return totalEarning;
    }

    public void setTotalEarning(Double totalEarning) {
        this.totalEarning = totalEarning;
    }

    public List<RideRequest> getTotalRides() {
        return totalRides;
    }

    public void setTotalRides(List<RideRequest> totalRides) {
        this.totalRides = totalRides;
    }

    public Double getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(Double totalCommission) {
        this.totalCommission = totalCommission;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "UserRideEarnings{" +
                "userDetails=" + userDetails +
                ", totalEarningOfTheDay=" + totalEarningOfTheDay +
                ", totalRidesOfTheDay=" + totalRidesOfTheDay +
                ", totalCommissionOfTheDay=" + totalCommissionOfTheDay +
                ", balanceOfTheDay=" + balanceOfTheDay +
                ", totalEarning=" + totalEarning +
                ", totalRides=" + totalRides +
                ", totalCommission=" + totalCommission +
                ", balance=" + balance +
                '}';
    }
}
