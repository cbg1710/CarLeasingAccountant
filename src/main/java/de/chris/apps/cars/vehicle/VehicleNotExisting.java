package de.chris.apps.cars.vehicle;

public class VehicleNotExisting extends RuntimeException {
    VehicleNotExisting(String vin) {
        super("Vehicle with vin: " + vin + " not existing");
    }
}
