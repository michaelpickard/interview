package com.workmarket.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.http.HttpStatus;

import java.util.Objects;

public class Status {

  private final HttpStatus code;
  private final String message;

  public Status(
      @JsonProperty("code") final HttpStatus code,
      @JsonProperty("message")final String message) {
    this.code = code;
    this.message = message;
  }

  public HttpStatus getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "Status{" + "code=" + code + ", message='" + message + '\'' + '}';
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final Status status = (Status) o;
    return getCode() == status.getCode() &&
        Objects.equals(getMessage(), status.getMessage());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCode(), getMessage());
  }
}
