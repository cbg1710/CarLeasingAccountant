package de.chris.apps.cars.vehicle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
public class Data {

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

    Data(LocalDate pickUpDay, LocalDate returnDay, int maximumDistance) {
        this.pickUpDay = pickUpDay;
        this.returnDay = returnDay;
        this.maximumDistance = maximumDistance;
    }

    LocalDate getPickUpDay() {
        return pickUpDay;
    }

    LocalDate getReturnDay() {
        return returnDay;
    }

    int getMaximumDistance() {
        return maximumDistance;
    }

    CurrentOdometer getCurrentOdometer() {
        return currentOdometer;
    }

    long getRemainingDays() {
        return ChronoUnit.DAYS.between(LocalDate.now(), returnDay);
    }

    long getPassedDays() {
        return ChronoUnit.DAYS.between(pickUpDay, LocalDate.now());
    }

    int getRemainingDistance() {
        return maximumDistance - currentOdometer.getOdometer();
    }

    float getDistancePerDay() {
        long days = ChronoUnit.DAYS.between(pickUpDay, returnDay);
        return round(maximumDistance / (float) days);
    }

    float getAverageDistancePerDay() {
        return round(currentOdometer.odometer / (float) getPassedDays());
    }

    float getDistanceDiff() {
        return round(getAllowedDistance() - currentOdometer.getOdometer());
    }

    float getAllowedDistance() {
        return round(getPassedDays() * getDistancePerDay());
    }

    private static float round(float value) {
        BigDecimal result = new BigDecimal(Float.toString(value));
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result.floatValue();
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
                Objects.equals(pickUpDay, data.pickUpDay) &&
                Objects.equals(returnDay, data.returnDay) &&
                Objects.equals(currentOdometer, data.currentOdometer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pickUpDay, returnDay, maximumDistance, currentOdometer);
    }

    public static class CurrentOdometer {
        @JsonProperty
        @JsonSerialize(using = LocalDateSerializer.class)
        @JsonDeserialize(using = LocalDateDeserializer.class)
        LocalDate odometerDay = null;
        @JsonProperty
        int odometer = 0;

        CurrentOdometer() {}

        void updateOdometer(int odometer) {
            this.odometer = odometer;
            odometerDay = LocalDate.now();
        }

        int getOdometer() {
            return odometer;
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
            return odometer == that.odometer &&
                    Objects.equals(odometerDay, that.odometerDay);
        }

        @Override
        public int hashCode() {
            return Objects.hash(odometerDay, odometer);
        }
    }
}
