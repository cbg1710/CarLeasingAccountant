package de.chris.apps.cars.vehicle;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.google.common.base.Objects;

public class History {

    @JsonProperty
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date = null;

    @JsonProperty
    int distance = 0;

    @JsonProperty
    float allowedDistance = 0;

    public History() {
    }

    public History(int distance, float allowedDistance) {
        this(distance, allowedDistance, LocalDate.now());
    }

    public History(int distance, float allowedDistance, LocalDate date) {
        this.date = LocalDate.now();
        this.distance = distance;
        this.allowedDistance = allowedDistance;
    }

    public int getDistance() {
        return distance;
    }

    public float getAllowedDistance() {
        return allowedDistance;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        History history = (History) o;
        return distance == history.distance && allowedDistance == history.allowedDistance && history.date.equals(date);
    }

    @Override
    public String toString() {
        return "[History:" + " date= " + date.toString() + " allowedDistance=" + allowedDistance + " distance="
                + distance + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(distance, allowedDistance);
    }
}
