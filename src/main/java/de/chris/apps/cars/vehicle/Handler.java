package de.chris.apps.cars.vehicle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Handler {
    private static final Logger LOG = LoggerFactory.getLogger(Handler.class);
    private static final String DIRECTORY_PATH = "vehicles";
    private static final String FILE_EXTENSION = ".JSON";

    private File jsonDataFile;
    private ObjectMapper objectMapper = new ObjectMapper();

    protected Handler(File jsonDataFile) {
        this.jsonDataFile = jsonDataFile;
    }

    protected Handler(JsonData jsonData) throws IOException {
        createDir();
        jsonDataFile = createDataFile(jsonData);
        writeDataToFile(jsonData);
    }

    private void createDir() {
        File dir = new File(DIRECTORY_PATH);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    private File createDataFile(JsonData jsonData) throws IOException {
        File result = new File(DIRECTORY_PATH + "/" + jsonData.getVin() + FILE_EXTENSION);
        if (!result.exists()) {
            LOG.info("Create new vehicle data file {}", result.getName());
            result.createNewFile();
        }
        return result;
    }

    private void writeDataToFile(JsonData jsonData) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(jsonDataFile)) {
            objectMapper.writeValue(fos, jsonData);
        }
    }

    File getDataFile() {
        return jsonDataFile;
    }

    void deleteDataFile() {
        LOG.warn("Delete data file {}", jsonDataFile.getName());
        jsonDataFile.delete();
    }

    JsonData getJsonData() throws IOException {
        return objectMapper.readValue(jsonDataFile, JsonData.class);
    }

    Data getData() throws IOException {
        return getJsonData().getData();
    }

    void setOdometer(int odometer) throws IOException {
        JsonData data = getJsonData();
        data.getData().getCurrentOdometer().updateOdometer(odometer);
        writeDataToFile(data);
    }

    int getOdometer() throws IOException {
        return getJsonData().getData().getCurrentOdometer().getOdometer();
    }

    Data.CurrentOdometer getCurrentOdometer() throws IOException {
        return getJsonData().getData().getCurrentOdometer();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Handler handler = (Handler) o;
        return Objects.equal(jsonDataFile, handler.jsonDataFile);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(jsonDataFile);
    }

    static Handler addNewVehicle(JsonData data) throws IOException {
        if (getDataFile(data.getVin()).exists()) {
            throw new VehicleAlreadyExists(data.getVin());
        }
        return new Handler(data);
    }

    static Handler getDataHandler(String vin) {
        File file = getDataFile(vin);
        if (!file.exists()) {
            throw new VehicleNotExisting(vin);
        }
        return new Handler(file);
    }

    private static File getDataFile(String vin) {
        return new File(DIRECTORY_PATH + "/" + vin + FILE_EXTENSION);
    }
}


class VehicleNotExisting extends RuntimeException {
    VehicleNotExisting(String vin) {
        super("Vehicle with vin: " + vin + " not existing");
    }
}

class VehicleAlreadyExists extends RuntimeException {
    VehicleAlreadyExists(String vin) {
        super("Vehicle with vin: " + vin + " already exists");
    }
}
