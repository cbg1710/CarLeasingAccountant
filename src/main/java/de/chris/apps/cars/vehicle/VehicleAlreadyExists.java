package de.chris.apps.cars.vehicle;

public class VehicleAlreadyExists extends RuntimeException {
    VehicleAlreadyExists(String vin) {
        super("Vehicle with vin: " + vin + " already exists");
    }
}
