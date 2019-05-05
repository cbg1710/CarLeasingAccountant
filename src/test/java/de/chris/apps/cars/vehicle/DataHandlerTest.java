package de.chris.apps.cars.vehicle;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DataHandlerTest {

    private static final String VIN = "TEST_VIN";
    private static final LocalDate PICK_UP = LocalDate.of(2019, 3, 9);
    private static final LocalDate RELEASE = PICK_UP.plusYears(1);
    private static final int MAX_ODO = 13099;

    private DataHandler dataHandler;
    private Data data;

    @BeforeAll
    public void setUp() throws IOException {
        data = new Data(VIN, PICK_UP, RELEASE, MAX_ODO);
        dataHandler = DataHandler.addNewVehicle(data);
    }

    @Test
    public void dataExists() {
        assertTrue(dataHandler.getDataFile().exists());
    }

    @Test
    public void getDataFromFile() throws IOException {
        Data actual = dataHandler.getData();
        assertEquals(data, actual);
    }

    @Test
    public void getDataHandlerWhichDontExists() {
        assertThrows(VehicleNotExisting.class,
                () -> DataHandler.getDataHandler("NOT_THERE"));
    }

    @Test
    public void getDataHandlerAlreadyExists() {
        assertThrows(VehicleAlreadyExists.class,
                () -> DataHandler.addNewVehicle(data));
    }

    @AfterAll
    public void cleanUp() {
        dataHandler.deleteDataFile();
    }

}