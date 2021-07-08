/*
 * Copyright 2021, WorkMarket, Inc. All Rights Reserved.
 */
package com.workmarket.service;

import com.workmarket.dao.ParcelDataManager;
import com.workmarket.exception.DuplicateParcelException;
import com.workmarket.exception.ParcelNotFoundException;
import com.workmarket.facade.ExternalMapAPIFacade;
import com.workmarket.localization.Messages;
import com.workmarket.model.ParcelDetails;
import com.workmarket.model.ParcelLocation;
import com.workmarket.model.Response;
import com.workmarket.model.Status;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@Service
public class ParcelService {

  private final ParcelDataManager dataManager;

  private final ExternalMapAPIFacade mapFacade = new ExternalMapAPIFacade();

  @Autowired
  public ParcelService(final ParcelDataManager dataManager) {
    this.dataManager = dataManager;
  }

  public Response<ParcelDetails> createParcel(final ParcelDetails newParcelDetails) {
    final String parcelUuid =
        StringUtils.isNotBlank(newParcelDetails.getUuid())
            ? newParcelDetails.getUuid()
            : UUID.randomUUID().toString();

    final ParcelDetails parcelDetails =
        new ParcelDetails(
            parcelUuid,
            newParcelDetails.getOrigin(),
            newParcelDetails.getDestination()
        );

    try {
      dataManager.addParcelDetails(parcelDetails);
    } catch (final DuplicateParcelException e) {
      return buildResponse(HttpStatus.BAD_REQUEST, Messages.STATUS_DUPLICATE_PARCEL, null);
    }
    return getParcel(parcelUuid);
  }

  public Response<ParcelDetails> getParcel(final String parcelUuid) {
    try {
      final ParcelDetails parcelDetails = dataManager.getParcelDetails(parcelUuid);
      return buildResponse(HttpStatus.OK, Messages.STATUS_SUCCESS, parcelDetails);
    } catch (final ParcelNotFoundException e) {
      return buildResponse(HttpStatus.NOT_FOUND, Messages.STATUS_NOT_FOUND_PARCEL, null);
    }
  }

  public Response<URI> getParcelLocationAsMapURI(final String parcelUuid) {
    try {
      final ParcelLocation parcelLocation = dataManager.getParcelLocation(parcelUuid);
      final URI mapURI = mapFacade.getParcelLocationAsMapURI(parcelLocation.getCurrentLocation());
      return new Response<>(new Status(HttpStatus.OK, Messages.STATUS_SUCCESS), mapURI);
    } catch (final ParcelNotFoundException e) {
      return buildResponse(HttpStatus.NOT_FOUND, Messages.STATUS_NOT_FOUND_PARCEL, null);
    } catch (final URISyntaxException e) {
      return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, Messages.STATUS_FAILURE_UNKNOWN, null);
    }
  }


  /*
        NEW METHODS CAN GO DOWN HERE
   */





  private <T> Response<T> buildResponse(
      final HttpStatus httpStatus, final String statusMessage, final T payload) {
    return new Response<T>(new Status(httpStatus, statusMessage), payload);
  }
}
