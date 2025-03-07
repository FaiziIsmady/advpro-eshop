package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
        this.paymentData.put("testKey", "testValue");
    }

    @Test
    void testCreatePaymentWithDefaultStatus() {
        Payment payment = new Payment("PAY001", "VOUCHER", paymentData);
        
        assertEquals("PAY001", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals("REJECTED", payment.getStatus());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentWithCustomStatus() {
        Payment payment = new Payment("PAY001", "VOUCHER", paymentData, "SUCCESS");
        
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentWithInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("PAY001", "VOUCHER", paymentData, "INVALID_STATUS");
        });
    }

    @Test
    void testSetStatusToSuccess() {
        Payment payment = new Payment("PAY001", "VOUCHER", paymentData);
        payment.setStatus("SUCCESS");
        
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetInvalidStatus() {
        Payment payment = new Payment("PAY001", "VOUCHER", paymentData);
        
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("INVALID_STATUS");
        });
    }

    @Test
    void testSetPaymentData() {
        Payment payment = new Payment("PAY001", "VOUCHER", paymentData);
        Map<String, String> newData = new HashMap<>();
        newData.put("newKey", "newValue");
        
        payment.setPaymentData(newData);
        
        assertEquals(newData, payment.getPaymentData());
    }

    @Test
    void testSetEmptyPaymentData() {
        Payment payment = new Payment("PAY001", "VOUCHER", paymentData);
        Map<String, String> emptyData = new HashMap<>();
        
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setPaymentData(emptyData);
        });
    }

    @Test
    void testSetNullPaymentData() {
        Payment payment = new Payment("PAY001", "VOUCHER", paymentData);
        
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setPaymentData(null);
        });
    }

    @Test
    void testSetOrderId() {
        Payment payment = new Payment("PAY001", "VOUCHER", paymentData);
        payment.setOrderId("ORDER123");
        
        assertEquals("ORDER123", payment.getOrderId());
    }
}