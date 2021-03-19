package de.chris.apps.cars.vehicle;

public class CouldNotCreateVehicle extends RuntimeException {
    private static final long serialVersionUID = -5854627578783732506L;

    CouldNotCreateVehicle(String vin) {
        super("Could not create file for vehicle with vin: " + vin);
    }
}