package com.workmarket.service;

import com.workmarket.BaseTest;
import com.workmarket.dao.ParcelDataManager;
import com.workmarket.exception.ParcelNotFoundException;
import com.workmarket.model.ParcelDetails;
import com.workmarket.model.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParcelServiceTest extends BaseTest {

  @Mock private ParcelDataManager mockDataManager;

  private ParcelService service;

  @Before
  public void setUp() {
    service = new ParcelService(mockDataManager);
  }

  @Test
  public void createParcel() throws Exception {
    final ParcelDetails newParcel = new ParcelDetails(null, LOCATION_TORONTO, LOCATION_NEW_YORK);

    service.createParcel(newParcel);

    verify(mockDataManager)
        .addParcelDetails(
            argThat(
                actual ->
                    actual.getOrigin().equals(newParcel.getOrigin())
                        && actual.getDestination().equals(newParcel.getDestination())));
  }

  @Test
  public void getParcel() throws Exception {
    final ParcelDetails parcel =
        new ParcelDetails(PARCEL_UUID, LOCATION_TORONTO, LOCATION_NEW_YORK);
    when(mockDataManager.getParcelDetails(PARCEL_UUID)).thenReturn(parcel);

    final Response<ParcelDetails> response = service.getParcel(PARCEL_UUID);

    assertThat(response.getStatus().getCode(), equalTo(HttpStatus.OK));
    verify(mockDataManager).getParcelDetails(PARCEL_UUID);
  }

  @Test
  public void getParcel_notFound() throws Exception {
    when(mockDataManager.getParcelDetails(PARCEL_UUID)).thenThrow(new ParcelNotFoundException());

    final Response<ParcelDetails> response = service.getParcel(PARCEL_UUID);

    assertThat(response.getStatus().getCode(), equalTo(HttpStatus.NOT_FOUND));
    assertNull(response.getPayload());
  }
}
