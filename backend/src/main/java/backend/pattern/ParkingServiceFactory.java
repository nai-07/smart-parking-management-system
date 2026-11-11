package backend.pattern;

import backend.service.ParkingService;

public class ParkingServiceFactory {

    private ParkingServiceFactory() {
    }

    public static ParkingService create(ParkingService parkingService) {
        return parkingService;
    }
}