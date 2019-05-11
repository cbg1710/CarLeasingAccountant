package de.chris.apps.cars.vehicle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import de.chris.apps.cars.vehicle.history.History;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonData {

    @JsonProperty
    private String vin = null;
    @JsonProperty
    private Data data = null;
    @JsonProperty
    private List<History> historyList = new ArrayList<>();

    public JsonData() {}

    public JsonData(String vin, Data data) {
        this.vin = vin;
        this.data = data;
    }


    public JsonData(String vin, Data data, History... histories) {
        this.vin = vin;
        this.data = data;
        this.historyList = Arrays.asList(histories);
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

    public List<History> getHistoryList() {
        return historyList;
    }

    public void addHistory(History history) {
        historyList.add(history);
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
                Objects.equal(historyList, jsonData.historyList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(vin, data, historyList);
    }
}
