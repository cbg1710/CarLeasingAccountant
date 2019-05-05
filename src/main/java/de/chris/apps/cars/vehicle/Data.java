package de.chris.apps.cars.vehicle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Data {

    @JsonProperty
    private String vin = null;
    @JsonProperty
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate pickUpDay = null;
    @JsonProperty
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate returnDay = null;
    @JsonProperty
    private int maximumDistance = 0;
    @JsonProperty
    private CurrentOdometer currentOdometer = new CurrentOdometer();

    Data() {}

    Data(String vin, LocalDate pickUpDay, LocalDate returnDay, int maximumDistance) {
        this.vin = vin;
        this.pickUpDay = pickUpDay;
        this.returnDay = returnDay;
        this.maximumDistance = maximumDistance;
    }

    public String getVin() {
        return vin;
    }

    public LocalDate getPickUpDay() {
        return pickUpDay;
    }

    public LocalDate getReturnDay() {
        return returnDay;
    }

    public int getMaximumDistance() {
        return maximumDistance;
    }

    public CurrentOdometer getCurrentOdometer() {
        return currentOdometer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Data data = (Data) o;
        return maximumDistance == data.maximumDistance &&
                Objects.equals(vin, data.vin) &&
                Objects.equals(pickUpDay, data.pickUpDay) &&
                Objects.equals(returnDay, data.returnDay) &&
                Objects.equals(currentOdometer, data.currentOdometer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin, pickUpDay, returnDay, maximumDistance, currentOdometer);
    }

    static class CurrentOdometer {
        @JsonProperty
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTime odometerDay = null;
        @JsonProperty
        int currentOdometer = 0;

        CurrentOdometer() {}

        public void updateOdometer(int currentOdometer) {
            this.currentOdometer = currentOdometer;
            odometerDay = LocalDateTime.now();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CurrentOdometer that = (CurrentOdometer) o;
            return currentOdometer == that.currentOdometer &&
                    Objects.equals(odometerDay, that.odometerDay);
        }

        @Override
        public int hashCode() {
            return Objects.hash(odometerDay, currentOdometer);
        }
    }
}
