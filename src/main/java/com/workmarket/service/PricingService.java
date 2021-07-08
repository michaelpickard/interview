/*
 * Copyright 2021, WorkMarket, Inc. All Rights Reserved.
 */
package com.workmarket.service;

import com.workmarket.facade.ExternalGeographicAPIFacade;
import com.workmarket.model.Coordinates;
import com.workmarket.model.ParcelDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PricingService {

  public enum DistanceCategory {
    SHORT,
    MEDIUM,
    LONG
  }

	private static final Logger logger = LoggerFactory.getLogger(PricingService.class);

  private static double BASE_DELIVERY_PRICE = 5.00;
  private static double SHORT_DELIVERY_MODIFIER = 1;
  private static double MEDIUM_DELIVERY_MODIFIER = 1.5;
  private static double LONG_DELIVERY_MODIFIER = 2;
  private static double DIFFERENT_COUNTRY_MODIFIER = 3.0;

  public double calculateDeliveryCharge(final ParcelDetails parcelDetails) {
    final ExternalGeographicAPIFacade geographyHelper = new ExternalGeographicAPIFacade();

    final Coordinates origin = parcelDetails.getOrigin();
    final Coordinates destination = parcelDetails.getDestination();

    final boolean sameCountry = geographyHelper.isSameCountry(origin, destination);
    final double distanceBetween = geographyHelper.distanceBetween(origin, destination);
    final DistanceCategory distanceCategory = getDistanceCategory(distanceBetween);

    double totalDeliveryCharge = BASE_DELIVERY_PRICE;
    if (!sameCountry)
    totalDeliveryCharge = totalDeliveryCharge * DIFFERENT_COUNTRY_MODIFIER;
    switch (distanceCategory) {
      case SHORT:
        totalDeliveryCharge = totalDeliveryCharge * SHORT_DELIVERY_MODIFIER;
      case MEDIUM:
        totalDeliveryCharge = totalDeliveryCharge * MEDIUM_DELIVERY_MODIFIER;
      case LONG:
        totalDeliveryCharge = totalDeliveryCharge * LONG_DELIVERY_MODIFIER;
    }

    return totalDeliveryCharge;
  }

  public DistanceCategory getDistanceCategory(double distance) {
    distance = Math.floor(distance);
    if (distance < 500) {
      return DistanceCategory.SHORT;
    }
    if (distance < 1500) {
      return DistanceCategory.MEDIUM;
    }
    return DistanceCategory.LONG;
  }
}
