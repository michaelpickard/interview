package com.workmarket.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workmarket.BaseTest;
import com.workmarket.model.ParcelDetails;
import com.workmarket.model.Response;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.workmarket.web.ParcelController.URL_CREATE_PARCEL;
import static com.workmarket.web.ParcelController.URL_GET_PARCEL;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ParcelControllerIT extends BaseTest {

  @Autowired private MockMvc mvc;

  @Test
  public void getIndex() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(
            content().string(equalTo("Welcome to UltraMegaTracked Parcel Delivery Service!")));
  }

  @Test
  public void basicCreateAndRetrieve() throws Exception {
    final ParcelDetails newParcelDetails =
        new ParcelDetails(null, LOCATION_NEW_YORK, LOCATION_TORONTO);

    final MvcResult createResult =
        mvc.perform(
                MockMvcRequestBuilders.post(URL_CREATE_PARCEL)
                    .content(asJsonString(newParcelDetails))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
    final Response<ParcelDetails> createResponse =
        new ObjectMapper()
            .readValue(createResult.getResponse().getContentAsString(), Response.class);

    final ParcelDetails createResponsePayload = createResponse.getPayload();
    assertNotNull(createResponsePayload);
    final String parcelUuid = createResponsePayload.getUuid();

    final MvcResult getResult =
        mvc.perform(
                MockMvcRequestBuilders.get(URL_GET_PARCEL)
                    .param("uuid", parcelUuid)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    final Response<ParcelDetails> getResponse =
        new ObjectMapper().readValue(getResult.getResponse().getContentAsString(), Response.class);
    final ParcelDetails getResponsePayload = getResponse.getPayload();
    assertNotNull(getResponsePayload);
    assertThat(getResponsePayload.getUuid(), equalTo(parcelUuid));
    assertThat(getResponsePayload.getOrigin(), equalTo(newParcelDetails.getOrigin()));
    assertThat(getResponsePayload.getDestination(), equalTo(newParcelDetails.getDestination()));
  }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
