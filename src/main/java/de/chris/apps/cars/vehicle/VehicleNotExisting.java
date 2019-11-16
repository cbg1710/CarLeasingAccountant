package de.chris.apps.cars.vehicle;

public class VehicleNotExisting extends RuntimeException {
    private static final long serialVersionUID = 8460613660424609112L;

    VehicleNotExisting(String vin) {
        super("Vehicle with vin: " + vin + " not existing");
    }
}
