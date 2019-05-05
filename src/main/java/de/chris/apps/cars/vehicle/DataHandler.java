package de.chris.apps.cars.vehicle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Objects;

class DataHandler {
    private static final Logger LOG = LoggerFactory.getLogger(DataHandler.class);
    private static final String DIRECTORY_PATH = "vehicles";
    private static final String FILE_EXTENSION = ".JSON";

    private File dataFile;

    private DataHandler(String vin) throws IOException {
        createDir();
        dataFile = createDataFile(vin);
    }

    private DataHandler(File dataFile) {
        this.dataFile = dataFile;
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
            LOG.info("Create new vehicle data file of vin {}", vin);
            result.createNewFile();
        }
        return result;
    }

    File getDataFile() {
        return dataFile;
    }

    void deleteDataFile() {
        dataFile.delete();
    }

    public void setOdometer(int odometer) throws IOException {
        getData().getCurrentOdometer().updateOdometer(odometer);
    }

    Data getData() throws IOException {
        return new ObjectMapper().readValue(dataFile, Data.class);
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
        String json = new ObjectMapper().writeValueAsString(data);
        DataHandler result = new DataHandler(data.getVin());
        try (FileWriter file = new FileWriter(result.getDataFile())) {
            file.write(json);
            LOG.info("Wrote {} to file", json);
        }
        return result;
    }

    static DataHandler getDataHandler(String vin) {
        File dataFile = new File(DIRECTORY_PATH + "/" + vin + FILE_EXTENSION);
        if (!dataFile.exists()) {
            throw new VehicleNotExisting(vin);
        }
        return new DataHandler(dataFile);
    }
}

class VehicleNotExisting extends RuntimeException {
    VehicleNotExisting(String vin) {
        super("Vehicle with vin: " + vin + " not existing");
    }
}
