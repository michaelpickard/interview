package com.workmarket.facade;

import com.workmarket.model.Coordinates;

import java.util.Random;

/**
 * This class represents a facade layer for communicating with an external API that answers
 * questions like: "Are two sets of coordinates in the same country?" "How far apart are two
 * coordinates?" etc.
 *
 * <p>It is essentially a mock layer so we don't have to make actual external calls in this
 * interview.
 */
public class ExternalGeographicAPIFacade {

  final Random random;

  public ExternalGeographicAPIFacade() {
    random = new Random();
  }

  public boolean isSameCountry(final Coordinates locationA, final Coordinates locationB) {
    return random.nextBoolean();
  }

  public double distanceBetween(final Coordinates locationA, final Coordinates locationB) {
    return random.nextDouble() * 1000.0;
  }
}
