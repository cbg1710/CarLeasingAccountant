package de.chris.apps.cars.vehicle;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HandlerTest {

    private static final String VIN = "TEST_VIN";
    private static final String NAME = "TEST_NAME";
    private static final LocalDate PICK_UP = LocalDate.of(2019, 3, 9);
    private static final LocalDate RELEASE = PICK_UP.plusYears(1);
    private static final int MAX_ODO = 13099;

    private Handler dataHandler;
    private JsonData jsonData;
    private History firstDay = new History(10, 20, PICK_UP);
    private History secondDay = new History(15, 40, PICK_UP.plusDays(1));

    @BeforeAll
    public void setUp() throws IOException {
        History[] histories = new History[] {firstDay, secondDay};
        jsonData = new JsonData(VIN, new Data(NAME, PICK_UP, RELEASE, MAX_ODO), histories);
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
        assertThrows(VehicleNotExisting.class, () -> Handler.getDataHandler("NOT_THERE"));
    }

    @Test
    public void getDataHandlerAlreadyExists() {
        assertThrows(VehicleAlreadyExists.class, () -> Handler.addNewVehicle(jsonData));
    }

    @Test
    void getAllVehicles() throws IOException {
        Map<String, String> vehicles = Handler.listVehicles();
        assertEquals(1, vehicles.size());
        assertEquals(NAME, vehicles.get(VIN));

        List<Handler> handler = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            handler.add(Handler.addNewVehicle(
                    new JsonData("vin" + i, new Data("name" + i, PICK_UP, RELEASE, MAX_ODO),
                            new History[] {firstDay, secondDay})));
        }
        vehicles = Handler.listVehicles();
        assertEquals(10, vehicles.size());
        Arrays.stream(new File("vehicles").listFiles())
                .forEach(f -> System.out.println(f.toString()));
        handler.forEach(Handler::deleteDataFile);
    }

    @AfterAll
    public void cleanUp() {
        dataHandler.deleteDataFile();
    }

}
