package com.workmarket.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Coordinates {

  private final double latitude;
  private final double longitude;

  public Coordinates(
      @JsonProperty("latitude") final double latitude,
      @JsonProperty("longitude")final double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  @Override
  public String toString() {
    return "Coordinates{" + "latitude=" + latitude + ", longitude=" + longitude + '}';
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final Coordinates that = (Coordinates) o;
    return Double.compare(that.getLatitude(), getLatitude()) == 0 &&
        Double.compare(that.getLongitude(), getLongitude()) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getLatitude(), getLongitude());
  }
}
