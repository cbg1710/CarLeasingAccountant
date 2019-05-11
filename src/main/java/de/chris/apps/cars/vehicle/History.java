package de.chris.apps.cars.vehicle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

public class History {

    @JsonProperty
    int distance = 0;
    @JsonProperty
    int allowedDistance = 0;

    public History() {}

    public History(int distance, int allowedDistance) {
        this.distance = distance;
        this.allowedDistance = allowedDistance;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getAllowedDistance() {
        return allowedDistance;
    }

    public void setAllowedDistance(int allowedDistance) {
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
                allowedDistance == history.allowedDistance;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(distance, allowedDistance);
    }
}
