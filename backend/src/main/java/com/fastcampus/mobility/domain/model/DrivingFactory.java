package com.fastcampus.mobility.domain.model;

import com.fastcampus.mobility.domain.command.DrivingCreateCommand;
import com.fastcampus.mobility.domain.model.impl.*;

public class DrivingFactory {

    public static Driving of(DrivingCreateCommand command) {
        Driving driving = new Driving();
        driving.setCallType(command.getCallType());
        driving.setVehicleType(command.getVehicleType());

        DrivingRoute drivingRoute = new DrivingRoute();
        drivingRoute.setBoardingCoordinates(command.getBoardingCoordinates());
        drivingRoute.setDestinationCoordinates(command.getDestinationCoordinates());
        driving.setDrivingRoute(drivingRoute);

        driving.setRatePolicy(getRatePolicy(driving));
        return driving;
    }

    private static RatePolicy getRatePolicy(Driving driving) {
        switch (driving.getCallType()) {
            case BLUE:
                return new BlueCallRatePolicy(getDistanceRatePolicy(driving));
            case SMART:
                return new SmartCallRatePolicy(getDistanceRatePolicy(driving));
            default:
                throw new IllegalStateException("Unexpected value: " + driving.getCallType());
        }
    }

    private static DistanceRatePolicy getDistanceRatePolicy(Driving driving) {
        switch (driving.getVehicleType()) {
            case REGULAR:
                return new RegularDistanceRatePolicy(driving);
            case DELUXE:
                return new DeluxeDistanceRatePolicy(driving);
            default:
                throw new IllegalStateException("Unexpected value: " + driving.getVehicleType());
        }
    }
}
