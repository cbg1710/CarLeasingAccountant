package de.chris.apps.cars.vehicle;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class Vehicle {

    private DataHandler dataHandler;

    private Vehicle (DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    DataHandler getDataHandler() {
        return dataHandler;
    }

    public void updateOdometer(int odometer) throws IOException {
        dataHandler.setOdometer(odometer);
    }

    public int getOdometer() throws IOException {
        return dataHandler.getOdometer();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(dataHandler, vehicle.dataHandler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataHandler);
    }

    public static Vehicle addVehicle(String vin, LocalDate pickUpDay, LocalDate releaseDay,
                                     int maxDistance) throws IOException {
        DataHandler dataHandler = DataHandler.addNewVehicle(new Data(vin, pickUpDay, releaseDay, maxDistance));
        return new Vehicle(dataHandler);
    }

    public static Vehicle getVehicle(String vin) {
        return new Vehicle(DataHandler.getDataHandler(vin));
    }
}
