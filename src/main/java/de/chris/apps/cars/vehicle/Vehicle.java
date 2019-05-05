package de.chris.apps.cars.vehicle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class Vehicle {
    private static final Logger LOG = LoggerFactory.getLogger(Vehicle.class);
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

    public long getRemainingDays() throws IOException {
        long result = dataHandler.getData().getRemainingDays();
        LOG.info("Remaining rental days {}", result);
        return result;
    }

    public int getRemainingDistance() throws IOException {
        int result = dataHandler.getData().getRemainingDistance();
        LOG.info("Remaining distance {}", result);
        return result;
    }

    public float getDistancePerDay() throws IOException {
        float result = dataHandler.getData().getDistancePerDay();
        LOG.info("Distance per day {}", result);
        return result;
    }

    public float getAverageDistancePerDay() throws IOException {
        float result = dataHandler.getData().getAverageDistancePerDay();
        LOG.info("Average distance per day {}", result);
        return result;
    }

    public long getPassedDays() throws IOException {
        long result = dataHandler.getData().getPassedDays();
        LOG.info("Already passed days {}", result);
        return result;
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
