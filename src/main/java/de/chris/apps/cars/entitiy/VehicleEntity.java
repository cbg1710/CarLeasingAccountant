package de.chris.apps.cars.entitiy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleEntity {
  @JsonProperty
  public String vehicle = "";

  public VehicleEntity(String vin) {
    vehicle = vin;
  }
}
