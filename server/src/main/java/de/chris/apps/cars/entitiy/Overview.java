package de.chris.apps.cars.entitiy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import de.chris.apps.cars.vehicle.Data;
import de.chris.apps.cars.vehicle.Vehicle;

import java.io.IOException;
import java.time.LocalDate;

public class Overview {
    @JsonProperty
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate pickUpDay = null;
    @JsonProperty
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate returnDay = null;
    @JsonProperty
    private long remainingDays = 0;
    @JsonProperty
    private int maximumDistance = 0;
    @JsonProperty
    private Data.CurrentOdometer currentOdometer = null;
    @JsonProperty
    private int remainingDistance = 0;
    @JsonProperty
    private float averageDistancePerDay = 0.0f;
    @JsonProperty
    private float allowedDistancePerDay = 0.0f;
    @JsonProperty
    private float distanceDifference = 0.0f;

    public Overview() {}

    public Overview(Vehicle vehicle) throws IOException {
        pickUpDay = vehicle.getPickupDate();
        returnDay = vehicle.getReturnDate();
        remainingDays = vehicle.getRemainingDays();
        maximumDistance = vehicle.getMaximumDistance();
        currentOdometer = vehicle.getCurrentOdometer();
        remainingDistance = vehicle.getRemainingDistance();
        averageDistancePerDay = vehicle.getAverageDistancePerDay();
        allowedDistancePerDay = vehicle.getDistancePerDay();
        distanceDifference = vehicle.getDistanceDifference();
    }
}
