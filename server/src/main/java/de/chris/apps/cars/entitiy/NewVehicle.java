package de.chris.apps.cars.entitiy;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class NewVehicle {
  @JsonProperty
  private String vin = "";
  @JsonProperty
  private String name = "";
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

  public NewVehicle(String vin, String name, LocalDate pickUpDate, LocalDate returnDate,
      int maxDistance) {
    this.vin = vin;
    this.name = name;
    this.pickUpDay = pickUpDate;
    this.returnDay = returnDate;
    this.maximumDistance = maxDistance;
  }

  public String getVin() {
    return vin;
  }

  public String getName() {
    return name;
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
}
