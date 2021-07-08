package com.workmarket.facade;

import com.workmarket.model.Coordinates;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidParameterException;

/**
 * This class represents a facade layer for communicating with an external maps API such as Google
 * Maps.
 *
 * <p>It is essentially a mock layer so we don't have to make actual external calls in this
 * interview.
 */
public class ExternalMapAPIFacade {

  public URI getParcelLocationAsMapURI(final Coordinates location)
      throws URISyntaxException, InvalidParameterException {
    if (!isLocationValid(location)) {
      throw new InvalidParameterException("Invalid location: " + location.toString());
    }
    return new URI(
        "http://cool_map_page.com?lat="
            + location.getLatitude()
            + "&long="
            + location.getLongitude());
  }

  private boolean isLocationValid(final Coordinates location) {
    if (location == null) {
      return false;
    }
    final double latitude = location.getLatitude();
    final double longitude = location.getLongitude();
    return !(latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180);
  }
}
