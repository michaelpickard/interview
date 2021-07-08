package com.workmarket.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ParcelDetails {

  private final String uuid;
  private final Coordinates origin;
  private final Coordinates destination;

  public ParcelDetails(
      @JsonProperty("uuid") final String uuid,
      @JsonProperty("origin") final Coordinates origin,
      @JsonProperty("destination")final Coordinates destination) {
    this.uuid = uuid;
    this.origin = origin;
    this.destination = destination;
  }

  public String getUuid() {
    return uuid;
  }

  public Coordinates getOrigin() {
    return origin;
  }

  public Coordinates getDestination() {
    return destination;
  }

  @Override
  public String toString() {
    return "Package{"
        + "uuid='"
        + uuid
        + "'"
        + ", location="
        + (origin != null ? origin.toString() : "null")
        + ", destination="
        + (destination != null ? destination.toString() : "null")
        + "}";
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final ParcelDetails that = (ParcelDetails) o;
    return Objects.equals(getUuid(), that.getUuid()) &&
        Objects.equals(getOrigin(), that.getOrigin()) &&
        Objects.equals(getDestination(), that.getDestination());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUuid(), getOrigin(), getDestination());
  }
}
