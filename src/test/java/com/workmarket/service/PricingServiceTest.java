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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PricingServiceTest extends BaseTest {

  private PricingService service;

  @Before
  public void setUp() {
    service = new PricingService();
  }

  @Test
  public void testSetup() throws Exception {
    assertNotNull(service);
  }


}
