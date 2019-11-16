package de.chris.apps.cars.vehicle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonData {

    @JsonProperty
    private String vin = null;
    @JsonProperty
    private Data data = null;
    @JsonProperty("history")
    private History[] histories = new History[0];

    public JsonData() {
    }

    public JsonData(String vin, Data data) {
        this.vin = vin;
        this.data = data;
    }

    public JsonData(String vin, Data data, History[] histories) {
        this.vin = vin;
        this.data = data;
        this.histories = histories;
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

    public History[] getHistories() {
        return histories.clone();
    }

    public void addHistory(History history) {
        for (int i = 0; i < histories.length; i++) {
            if (histories[i].getDate().compareTo(history.getDate()) == 0) {
                histories[i] = history;
                return;
            }
        }
        List<History> newHistories = new ArrayList<>(Arrays.asList(histories));
        newHistories.add(history);
        histories = newHistories.toArray(new History[newHistories.size()]);
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
        return Objects.equal(vin, jsonData.vin) && Objects.equal(data, jsonData.data)
                && Arrays.equals(histories, jsonData.histories);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(vin, data, histories);
    }
}
