package com.workmarket.dao;

import com.workmarket.exception.DuplicateParcelException;
import com.workmarket.exception.ParcelNotFoundException;
import com.workmarket.model.Coordinates;
import com.workmarket.model.ParcelDetails;
import com.workmarket.model.ParcelLocation;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParcelDataManager {

  /*
  Assume the data model here (and this entire class!) can NOT be changed.  It is intentionally "not ideal".
   */

  private static final List<ParcelDetails> parcelDetails = new ArrayList<>();
  private static final List<ParcelLocation> parcelLocations = new ArrayList<>();

  private static final Map<String, Coordinates> parcelDestinations = new HashMap<>();

  public ParcelDetails addParcelDetails(final ParcelDetails newParcelDetails) throws DuplicateParcelException {
    Assert.notNull(newParcelDetails, "parcel details are required");
    final ParcelDetails existingParcelDetails = lookupParcelDetails(newParcelDetails.getUuid());
    if (existingParcelDetails != null) {
      throw new DuplicateParcelException();
    }
    parcelDetails.add(newParcelDetails);
    return newParcelDetails;
  }

  public ParcelDetails getParcelDetails(final String parcelUuid) throws ParcelNotFoundException {
    Assert.notNull(parcelUuid, "parcel uuid is required");
    final ParcelDetails parcelDetails = lookupParcelDetails(parcelUuid);
    if (parcelDetails == null) {
      throw new ParcelNotFoundException();
    }
    return parcelDetails;
  }

  public ParcelLocation getParcelLocation(final String parcelUuid) throws ParcelNotFoundException {
    Assert.notNull(parcelUuid, "parcel uuid is required");
    final ParcelLocation parcelLocation = lookupParcelLocation(parcelUuid);
    if (parcelLocation == null) {
      throw new ParcelNotFoundException();
    }
    return parcelLocation;
  }

  public ParcelLocation updateParcelCurrentLocation(final ParcelLocation newParcelLocation)
      throws ParcelNotFoundException {
    Assert.notNull(newParcelLocation, "new parcel location is required");
    final ParcelLocation oldParcelLocation = getParcelLocation(newParcelLocation.getUuid());
    parcelLocations.remove(oldParcelLocation);
    parcelLocations.add(newParcelLocation);
    return newParcelLocation;
  }

  public List<ParcelDetails> getAllParcelDetails() {
    return parcelDetails;
  }

  public List<ParcelLocation> getAllParcelLocations() {
    return parcelLocations;
  }

  private ParcelDetails lookupParcelDetails(final String parcelUuid) {
    return ParcelDataManager.parcelDetails.stream()
        .filter(parcel -> parcelUuid.equals(parcel.getUuid()))
        .findFirst()
        .orElse(null);
  }

  private ParcelLocation lookupParcelLocation(final String parcelUuid) {
    return parcelLocations.stream()
        .filter(parcel -> parcelUuid.equals(parcel.getUuid()))
        .findFirst()
        .orElse(null);
  }


}
