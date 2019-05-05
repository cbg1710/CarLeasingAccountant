package de.chris.apps.cars.vehicle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    private static final String VIN = "TEST_VIN";
    private static final LocalDate PICK_UP =
            LocalDate.of(2019, 3, 9);
    private static final LocalDate RELEASE = PICK_UP.plusYears(1);
    private static final int MAX_ODO = 13099;
    Vehicle vehicle;

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
        assertEquals(actual, vehicle);
    }

    @Test
    public void updateOdometer() throws IOException {
        vehicle.updateOdometer(15);
        assertEquals(15, Vehicle.getVehicle(VIN).getOdometer());
    }
}