package de.chris.apps.cars.vehicle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

class DataHandler {
    private static final Logger LOG = LoggerFactory.getLogger(DataHandler.class);
    private static final String DIRECTORY_PATH = "vehicles";
    private static final String FILE_EXTENSION = ".JSON";

    private File dataFile;
    private ObjectMapper objectMapper = new ObjectMapper();

    private DataHandler(File dataFile) {
        this.dataFile = dataFile;
    }

    private DataHandler(Data data) throws IOException {
        createDir();
        dataFile = createDataFile(data.getVin());
        writeDataToFile(data);
    }

    private void createDir() {
        File dir = new File(DIRECTORY_PATH);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    private File createDataFile(String vin) throws IOException {
        File result = new File(DIRECTORY_PATH + "/" + vin + FILE_EXTENSION);
        if (!result.exists()) {
            LOG.info("Create new vehicle data file {}", result.getName());
            result.createNewFile();
        }
        return result;
    }

    private void writeDataToFile(Data data) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            objectMapper.writeValue(fos, data);
        }
    }

    File getDataFile() {
        return dataFile;
    }

    void deleteDataFile() {
        LOG.warn("Delete data file {}", dataFile.getName());
        dataFile.delete();
    }

    void setOdometer(int odometer) throws IOException {
        Data data = getData();
        data.getCurrentOdometer().updateOdometer(odometer);
        writeDataToFile(data);
    }

    int getOdometer() throws IOException {
        return getData().getCurrentOdometer().getOdometer();
    }

    Data.CurrentOdometer getCurrentOdometer() throws IOException {
        return getData().getCurrentOdometer();
    }

    Data getData() throws IOException {
        return objectMapper.readValue(dataFile, Data.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataHandler that = (DataHandler) o;
        return Objects.equals(dataFile, that.dataFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataFile);
    }

    static DataHandler addNewVehicle(Data data) throws IOException {
        if (getDataFile(data.getVin()).exists()) {
            throw new VehicleAlreadyExists(data.getVin());
        }
        return new DataHandler(data);
    }

    static DataHandler getDataHandler(String vin) {
        File file = getDataFile(vin);
        if (!file.exists()) {
            throw new VehicleNotExisting(vin);
        }
        return new DataHandler(file);
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
