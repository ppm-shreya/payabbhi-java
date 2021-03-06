package com.payabbhi.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.payabbhi.Payabbhi;
import com.payabbhi.exception.PayabbhiException;
import java.util.HashMap;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("serial")
public class PaymentTest extends BaseTest {

  @Before
  public void setUp() throws Exception {
    Payabbhi.accessId = "some_access_id";
    Payabbhi.secretKey = "some_secret_key";

    mockFetcher();
  }

  @Test
  public void testGetAllPayments() throws PayabbhiException {
    PayabbhiCollection<Payment> payments = Payment.all();
    assertEquals(171, payments.count().intValue());
    assertNotEquals(null, payments.getData());
  }

  @Test
  public void testGetAllPaymentsWithFilters() throws PayabbhiException {
    PayabbhiCollection<Payment> payments =
        Payment.all(
            new HashMap<String, Object>() {
              {
                put("from", 1531208000);
              }
            });
    assertEquals(1, payments.count().intValue());
    assertNotEquals(null, payments.getData());
    List<Payment> paylist = payments.getData();
    assertEquals(1, paylist.size());
  }

  @Test
  public void testRetrievePaymentDetails() throws PayabbhiException {
    Payment payinfo = Payment.retrieve("pay_C5OcIOxiCrZXAcHG");
    assertEquals("pay_C5OcIOxiCrZXAcHG", payinfo.get("id"));
    assertEquals("order_8j3N11lCbJ2NtzOd", payinfo.get("order_id"));
  }

  @Test
  public void testCapturePayment() throws PayabbhiException {
    Payment payinfo = Payment.retrieve("pay_C5OcIOxiCrZXAcHG");
    assertEquals("authorized", payinfo.get("status"));
    payinfo.capture();
    assertEquals("captured", payinfo.get("status"));
    assertEquals("pay_C5OcIOxiCrZXAcHG", payinfo.get("id"));
  }

  @Test
  public void testRetrieveRefundsOfPayment() throws PayabbhiException {
    PayabbhiCollection<Refund> refunds = Payment.refunds("pay_R6mPqlzzukJTgWbS");
    assertEquals(3, refunds.count().intValue());
    assertNotEquals(null, refunds.getData());
    List<Refund> paylist = refunds.getData();
    assertEquals(1, paylist.size());
  }
}
