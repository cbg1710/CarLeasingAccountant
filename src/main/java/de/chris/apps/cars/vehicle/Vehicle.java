package de.chris.apps.cars.vehicle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public Data.CurrentOdometer getCurrentOdometer() throws IOException {
        return dataHandler.getCurrentOdometer();
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

    public float getDistanceDifference() throws IOException {
        float result = round(dataHandler.getData().getDistanceDiff());
        LOG.info("Distance difference is {}", result);
        return result;
    }

    public float getDistancePerDay() throws IOException {
        float result = round(dataHandler.getData().getDistancePerDay());
        LOG.info("Distance per day {}", result);
        return result;
    }

    public float getAverageDistancePerDay() throws IOException {
        float result = round(dataHandler.getData().getAverageDistancePerDay());
        LOG.info("Average distance per day {}", result);
        return result;
    }

    private float round(float value) {
        BigDecimal result = new BigDecimal(Float.toString(value));
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result.floatValue();
    }

    public long getPassedDays() throws IOException {
        long result = dataHandler.getData().getPassedDays();
        LOG.info("Already passed days {}", result);
        return result;
    }

    public int getMaximumDistance() throws IOException {
        return dataHandler.getData().getMaximumDistance();
    }

    public LocalDate getPickupDate() throws IOException {
        return dataHandler.getData().getPickUpDay();
    }

    public LocalDate getReturnDate() throws IOException {
        return dataHandler.getData().getReturnDay();
    }

    public String getVin() throws IOException {
        return dataHandler.getData().getVin();
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
        return addVehicle(new Data(vin, pickUpDay, releaseDay, maxDistance));
    }

    public static Vehicle addVehicle(Data data) throws IOException {
        DataHandler dataHandler = DataHandler.addNewVehicle(data);
        return new Vehicle(dataHandler);
    }

    public static Vehicle getVehicle(String vin) {
        return new Vehicle(DataHandler.getDataHandler(vin));
    }
}
