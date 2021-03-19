package de.chris.apps.cars.vehicle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        File result = new File(DIRECTORY_PATH + File.separator + jsonData.getVin() + FILE_EXTENSION);
        if (!result.exists()) {
            LOG.info("Create new vehicle data file {}", result.getName());
            if (!result.createNewFile()) {
                throw new CouldNotCreateVehicle(jsonData.getVin());
            }
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
        try {
            Files.delete(jsonDataFile.toPath());
        } catch (IOException e) {
            LOG.error("Could not delete file " + jsonDataFile.getName(), e);
        }
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
        data.addHistory(new History(odometer, getData().getAllowedDistance()));
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
        return jsonDataFile.equals(handler.jsonDataFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jsonDataFile);
    }

    static Handler addNewVehicle(JsonData data) throws IOException {
        if (getDataFile(data.getVin()).exists()) {
            throw new VehicleAlreadyExists(data.getVin());
        }
        return new Handler(data);
    }

    static Map<String, String> listVehicles() {
        try (Stream<Path> walk = Files.walk(Paths.get(DIRECTORY_PATH))) {
            List<String> jsonFiles = walk
                    .sorted((e1, e2) -> getFileTime(e1).compareTo(getFileTime(e2)))
                    .map(x -> x.getFileName().toString()).filter(f -> f.endsWith(FILE_EXTENSION))
                    .map(s -> s.split("\\.")[0]).collect(Collectors.toList());

            Map<String, String> result = new LinkedHashMap<>();
            for (String s : jsonFiles) {
                result.put(s, Handler.getDataHandler(s).getData().getName());
            }
            return result;
        }
        catch (IOException e) {
            return new HashMap<>();
        }
    }

    private static FileTime getFileTime(Path p) {
        try {
            return Files.getFileAttributeView(p, BasicFileAttributeView.class).readAttributes()
                    .creationTime();
        }
        catch (IOException e) {
            return FileTime.from(0, TimeUnit.NANOSECONDS);
        }
    }

    static Handler getDataHandler(String vin) {
        File file = getDataFile(vin);
        if (!file.exists()) {
            throw new VehicleNotExisting(vin);
        }
        return new Handler(file);
    }

    private static File getDataFile(String vin) {
        return new File(DIRECTORY_PATH + File.separator + vin + FILE_EXTENSION);
    }
}
