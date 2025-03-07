package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentCashOnDeliveryTest {

    private PaymentCashOnDelivery cod;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
        paymentData.put("address", "123 Main St");
        paymentData.put("deliveryFee", "10000");

        cod = new PaymentCashOnDelivery(paymentData);
    }

    @Test
    void testValidPaymentData() {
        PaymentStatus status = cod.validatePaymentData();

        assertEquals(PaymentStatus.SUCCESS, status);
    }

    @Test
    void testEmptyAddress() {
        paymentData.put("address", "");
        cod = new PaymentCashOnDelivery(paymentData);

        PaymentStatus status = cod.validatePaymentData();

        assertEquals(PaymentStatus.REJECTED, status);
    }

    @Test
    void testNullAddress() {
        paymentData.put("address", null);
        cod = new PaymentCashOnDelivery(paymentData);

        PaymentStatus status = cod.validatePaymentData();

        assertEquals(PaymentStatus.REJECTED, status);
    }

    @Test
    void testEmptyDeliveryFee() {
        paymentData.put("deliveryFee", "");
        cod = new PaymentCashOnDelivery(paymentData);

        PaymentStatus status = cod.validatePaymentData();

        assertEquals(PaymentStatus.REJECTED, status);
    }

    @Test
    void testNullDeliveryFee() {
        paymentData.put("deliveryFee", null);
        cod = new PaymentCashOnDelivery(paymentData);

        PaymentStatus status = cod.validatePaymentData();

        assertEquals(PaymentStatus.REJECTED, status);
    }

    @Test
    void testMissingAddress() {
        paymentData.remove("address");
        cod = new PaymentCashOnDelivery(paymentData);

        PaymentStatus status = cod.validatePaymentData();

        assertEquals(PaymentStatus.REJECTED, status);
    }

    @Test
    void testMissingDeliveryFee() {
        paymentData.remove("deliveryFee");
        cod = new PaymentCashOnDelivery(paymentData);

        PaymentStatus status = cod.validatePaymentData();

        assertEquals(PaymentStatus.REJECTED, status);
    }

    @Test
    void testEmptyPaymentData() {
        cod = new PaymentCashOnDelivery(new HashMap<>());

        PaymentStatus status = cod.validatePaymentData();

        assertEquals(PaymentStatus.REJECTED, status);
    }

    @Test
    void testNullPaymentData() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PaymentCashOnDelivery(null);
        });
    }

    @Test
    void testSetPaymentData() {
        Map<String, String> newData = new HashMap<>();
        newData.put("address", "456 New St");
        newData.put("deliveryFee", "20000");

        cod.setPaymentData(newData);

        PaymentStatus status = cod.validatePaymentData();
        assertEquals(PaymentStatus.SUCCESS, status);
        assertEquals("456 New St", cod.getPaymentData().get("address"));
        assertEquals("20000", cod.getPaymentData().get("deliveryFee"));
    }

    @Test
    void testSetInvalidPaymentData() {
        Map<String, String> invalidData = new HashMap<>();
        invalidData.put("address", "");
        invalidData.put("deliveryFee", "20000");

        cod.setPaymentData(invalidData);

        PaymentStatus status = cod.validatePaymentData();
        assertEquals(PaymentStatus.REJECTED, status);
    }
}