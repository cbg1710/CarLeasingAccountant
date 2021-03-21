package de.chris.apps.cars.vehicle;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.chris.apps.cars.entitiy.Trend;
import de.chris.apps.cars.entitiy.Trend.TrendType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    public History getLatest() {
        var sorted = getSortedHistories();
        return sorted[sorted.length - 1];
    }

    public History[] getSortedHistories() {
        List<History> result = new ArrayList<>();
        Arrays.stream(histories).sorted((h1, h2) -> h1.getDate().compareTo(h2.getDate()))
                .forEachOrdered(result::add);
        return result.toArray(new History[result.size()]);
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

    public Trend calculateTrend() {
        var meassurePoints = Arrays.asList(getSortedHistories());
        if (meassurePoints.size() < 2) {
            return new Trend(TrendType.NO_TREND, 0);
        }

        Collections.reverse(meassurePoints);
        var daysToCalc = meassurePoints.size() < 7 ? meassurePoints.size() - 1 :  7;
        var lastDay = calcDriffenKillometers(meassurePoints.get(0), meassurePoints.get(1));

        var kilmeters = 0; 
        var divider = 0; 
        for (int i = 1; i < daysToCalc; i++) {
            kilmeters += calcDriffenKillometers(meassurePoints.get(i), meassurePoints.get(i+1));
            divider++;
        }

        var avgKm = kilmeters / (float) divider;

        var result = (int) (lastDay - avgKm);
        if (result > 0) {
            return new Trend(TrendType.RISING, result);
        }
        else if (result < 0) {
            return new Trend(TrendType.FALLING, result);
        }
        return new Trend(TrendType.STABLE, result);
    }

    public int calcDriffenKillometers(History day, History dayBefore) {
        return day.distance - dayBefore.distance;
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
        if (!vin.equals(jsonData.vin)) {
            return false;
        }
        if (!data.equals(jsonData.data)) {
            return false;
        }
        return Arrays.equals(histories, jsonData.histories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin, data);
    }
}
