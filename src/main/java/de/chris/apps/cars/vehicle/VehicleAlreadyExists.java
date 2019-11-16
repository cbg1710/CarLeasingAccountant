package de.chris.apps.cars.vehicle;

public class VehicleAlreadyExists extends RuntimeException {
    private static final long serialVersionUID = -5874607578783732506L;

    VehicleAlreadyExists(String vin) {
        super("Vehicle with vin: " + vin + " already exists");
    }
}
