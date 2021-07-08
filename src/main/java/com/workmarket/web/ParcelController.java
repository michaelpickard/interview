package com.workmarket.web;

import com.workmarket.model.ParcelDetails;
import com.workmarket.model.Response;
import com.workmarket.service.ParcelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class ParcelController {

  public static final String URL_CREATE_PARCEL = "/rename-this-full-url-for-create-parcel";
  public static final String URL_GET_PARCEL = "/rename-this-full-url-for-get-parcel";
  public static final String URL_GET_PARCEL_LOCATION_AS_MAP =
      "/rename-this-full-url-for-get-parcel-location-as-map";

  private final ParcelService parcelService;

  @Autowired
  public ParcelController(final ParcelService parcelService) {
    this.parcelService = parcelService;
  }

  @RequestMapping("/")
  public String index() {
    return "Welcome to UltraMegaTracked Parcel Delivery Service!";
  };

  @RequestMapping(
      value = URL_CREATE_PARCEL,
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Response<ParcelDetails> createParcel(@RequestBody final ParcelDetails parcel) {
    return parcelService.createParcel(parcel);
  }

  @RequestMapping(value = URL_GET_PARCEL, method = RequestMethod.GET)
  @ResponseBody
  public Response<ParcelDetails> getParcel(@RequestParam final String uuid) {
    return parcelService.getParcel(uuid);
  }

  @RequestMapping(value = URL_GET_PARCEL_LOCATION_AS_MAP, method = RequestMethod.GET)
  @ResponseBody
  public Response<URI> getParcelLocationAsMap(@RequestParam final String uuid) {
    return parcelService.getParcelLocationAsMapURI(uuid);
  }
}
