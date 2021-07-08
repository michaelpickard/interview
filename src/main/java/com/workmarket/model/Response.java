package com.workmarket.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;

public class Response<T> {

  private final Status status;
  private final T payload;

  public Response(
      @JsonProperty("status") final Status status,
      @JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
      @JsonProperty("payload") final T payload) {
    this.status = status;
    this.payload = payload;
  }

  public Status getStatus() {
    return status;
  }

  public T getPayload() {
    return payload;
  }

  @Override
  public String toString() {
    return "Response{"
        + "status="
        + (status != null ? status.toString() : "null")
        + ", payload="
        + (payload != null ? payload.toString() : "null")
        + '}';
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final Response<?> response = (Response<?>) o;
    return Objects.equals(getStatus(), response.getStatus()) &&
        Objects.equals(getPayload(), response.getPayload());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getStatus(), getPayload());
  }
}
