package de.chris.apps.cars.vehicle;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HandlerTest {

    private static final String VIN = "TEST_VIN";
    private static final LocalDate PICK_UP = LocalDate.of(2019, 3, 9);
    private static final LocalDate RELEASE = PICK_UP.plusYears(1);
    private static final int MAX_ODO = 13099;

    private Handler dataHandler;
    private JsonData jsonData;
    private History firstDay = new History(10, 20);
    private History secondDay = new History(15, 40);

    @BeforeAll
    public void setUp() throws IOException {
        Map<LocalDate, History> map = new HashMap<>();
        map.put(PICK_UP, firstDay);
        map.put(PICK_UP.plusDays(1), secondDay);
        jsonData = new JsonData(VIN, new Data(PICK_UP, RELEASE, MAX_ODO), map);
        dataHandler = Handler.addNewVehicle(jsonData);
    }

    @Test
    public void dataExists() {
        assertTrue(dataHandler.getDataFile().exists());
    }

    @Test
    public void getDataFromFile() throws IOException {
        JsonData actual = dataHandler.getJsonData();
        assertEquals(jsonData, actual);
    }

    @Test
    public void getDataHandlerWhichDontExists() {
        assertThrows(VehicleNotExisting.class,
                () -> Handler.getDataHandler("NOT_THERE"));
    }

    @Test
    public void getDataHandlerAlreadyExists() {
        assertThrows(VehicleAlreadyExists.class,
                () -> Handler.addNewVehicle(jsonData));
    }

    @AfterAll
    public void cleanUp() {
        dataHandler.deleteDataFile();
    }

}