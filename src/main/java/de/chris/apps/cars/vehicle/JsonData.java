package de.chris.apps.cars.vehicle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateKeyDeserializer;
import com.google.common.base.Objects;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class JsonData {

    @JsonProperty
    private String vin = null;
    @JsonProperty
    private Data data = null;
    @JsonProperty("history")
    @JsonDeserialize(keyUsing = LocalDateKeyDeserializer.class)
    private Map<LocalDate, History> historyMap = null;

    public JsonData() {}

    public JsonData(String vin, Data data) {
        this.vin = vin;
        this.data = data;
        historyMap = new HashMap<>();
    }

    public JsonData(String vin, Data data, Map<LocalDate, History> historyMap) {
        this.vin = vin;
        this.data = data;
        this.historyMap = historyMap;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Data getData() {
        return data;
    }

    public Map<LocalDate, History> getHistoryMap() {
        return historyMap;
    }

    public void addHistory(History history) {
        historyMap.put(LocalDate.now(), history);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JsonData jsonData = (JsonData) o;
        return Objects.equal(vin, jsonData.vin) &&
                Objects.equal(data, jsonData.data) &&
                Objects.equal(historyMap, jsonData.historyMap);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(vin, data, historyMap);
    }
}
