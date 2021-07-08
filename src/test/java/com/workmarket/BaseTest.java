package com.workmarket;

import com.workmarket.model.Coordinates;

import java.util.UUID;

public class BaseTest {
  protected static Coordinates LOCATION_TORONTO = new Coordinates(43.647500, -79.395430);
  protected static Coordinates LOCATION_NEW_YORK = new Coordinates(40.750900, -73.985260);
  protected static String PARCEL_UUID = UUID.randomUUID().toString();
}
