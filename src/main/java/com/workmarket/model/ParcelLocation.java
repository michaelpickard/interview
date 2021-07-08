package com.workmarket.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ParcelLocation {

  private final String uuid;
  private final Coordinates currentLocation;

  public ParcelLocation(
      @JsonProperty("uuid") final String uuid,
      @JsonProperty("currentLocation") final Coordinates currentLocation) {
    this.uuid = uuid;
    this.currentLocation = currentLocation;
  }

  public String getUuid() {
    return uuid;
  }

  public Coordinates getCurrentLocation() {
    return currentLocation;
  }

  @Override
  public String toString() {
    return "Package{"
        + "uuid='"
        + uuid
        + "'"
        + ", location="
        + (currentLocation != null ? currentLocation.toString() : "null")
        + "}";
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final ParcelLocation that = (ParcelLocation) o;
    return Objects.equals(getUuid(), that.getUuid()) &&
        Objects.equals(getCurrentLocation(), that.getCurrentLocation());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUuid(), getCurrentLocation());
  }
}
