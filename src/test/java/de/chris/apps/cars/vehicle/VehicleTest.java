package de.chris.apps.cars.vehicle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VehicleTest {

    private static final String VIN = "TEST_VIN";
    private static final LocalDate PICK_UP =
            LocalDate.now().minusMonths(2);
    private static final LocalDate RELEASE = PICK_UP.plusYears(1);
    private static final int MAX_ODO = 365;
    private Vehicle vehicle;

    @BeforeEach
    public void setUp() throws IOException {
        vehicle = Vehicle.addVehicle(VIN, PICK_UP, RELEASE, MAX_ODO);
    }

    @AfterEach
    public void cleanUp() {
        vehicle.getDataHandler().deleteDataFile();
    }

    @Test
    public void getExitingVehicle() {
        Vehicle actual = Vehicle.getVehicle(VIN);
        assertEquals(vehicle, actual);
    }

    @Test
    public void getRemainingDays() throws IOException {
        assertEquals(ChronoUnit.DAYS.between(LocalDate.now(), RELEASE),vehicle.getRemainingDays());
    }

    @Test
    public void updateOdometer() throws IOException {
        vehicle.updateOdometer(15);
        assertEquals(15, Vehicle.getVehicle(VIN).getOdometer());
    }

    @Test
    public void getRemainingDistance() throws IOException {
        updateOdometer();
        assertEquals(MAX_ODO - 15, vehicle.getRemainingDistance());
    }

    @Test
    public void getDistancePerDay() throws IOException {
        assertEquals(1, Math.round(vehicle.getDistancePerDay()));
    }

    @Test
    public void getAverageDistancePerDay() throws IOException {
        vehicle.updateOdometer(61);
        assertEquals(1, Math.round(vehicle.getAverageDistancePerDay()));
    }

    @Test
    public void getAlreadyPassedDays() throws IOException {
        assertTrue(vehicle.getPassedDays() >= 58
                && vehicle.getPassedDays() <= 61);
    }

    @Test
    public void startUpDataEquals() throws IOException {
        assertEquals(VIN, vehicle.getVin());
        assertEquals(PICK_UP, vehicle.getPickupDate());
        assertEquals(RELEASE, vehicle.getReturnDate());
        assertEquals(MAX_ODO, vehicle.getMaximumDistance());
    }
}