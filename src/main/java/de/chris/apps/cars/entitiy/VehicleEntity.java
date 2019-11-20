package de.chris.apps.cars.entitiy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleEntity {
  @JsonProperty
  public String vehicle = "";
  @JsonProperty
  public String name = "";

  public VehicleEntity(String vin, String name) {
    vehicle = vin;
    this.name = name;
  }
}
