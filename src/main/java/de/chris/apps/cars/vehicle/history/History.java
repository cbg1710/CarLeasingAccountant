package de.chris.apps.cars.vehicle.history;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.google.common.base.Objects;

import java.time.LocalDate;

public class History {

    @JsonProperty
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    LocalDate date;
    @JsonProperty
    int distance = 0;
    @JsonProperty
    int allowedDistance = 0;

    public History() {}

    public History(LocalDate date, int distance, int allowedDistance) {
        this.date = date;
        this.distance = distance;
        this.allowedDistance = allowedDistance;
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
        return distance == history.distance &&
                allowedDistance == history.allowedDistance &&
                Objects.equal(date, history.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date, distance, allowedDistance);
    }
}
