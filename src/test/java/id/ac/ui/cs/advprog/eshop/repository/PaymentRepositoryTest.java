package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    private PaymentRepository paymentRepository;
    private Payment payment1;
    private Payment payment2;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("key1", "value1");
        payment1 = new Payment("PAY001", "VOUCHER", paymentData1);
        
        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("key2", "value2");
        payment2 = new Payment("PAY002", "COD", paymentData2);
    }

    @Test
    void testSavePayment() {
        Payment savedPayment = paymentRepository.save(payment1);
        
        assertEquals(payment1.getId(), savedPayment.getId());
        assertEquals(payment1.getMethod(), savedPayment.getMethod());
        assertEquals(payment1.getStatus(), savedPayment.getStatus());
        assertEquals(payment1.getPaymentData(), savedPayment.getPaymentData());
    }

    @Test
    void testFindById() {
        paymentRepository.save(payment1);
        Payment foundPayment = paymentRepository.findById(payment1.getId());
        
        assertNotNull(foundPayment);
        assertEquals(payment1.getId(), foundPayment.getId());
    }

    @Test
    void testFindByIdNotFound() {
        Payment foundPayment = paymentRepository.findById("NON_EXISTENT_ID");
        
        assertNull(foundPayment);
    }

    @Test
    void testFindAll() {
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);
        
        List<Payment> allPayments = paymentRepository.findAll();
        
        assertEquals(2, allPayments.size());
        assertTrue(allPayments.contains(payment1));
        assertTrue(allPayments.contains(payment2));
    }

    @Test
    void testUpdateExistingPayment() {
        paymentRepository.save(payment1);
        
        // Update payment status
        payment1.setStatus(PaymentStatus.SUCCESS.getValue());
        Payment updatedPayment = paymentRepository.save(payment1);
        
        assertEquals(PaymentStatus.SUCCESS.getValue(), updatedPayment.getStatus());
        
        // Verify the update in repository
        Payment foundPayment = paymentRepository.findById(payment1.getId());
        assertEquals(PaymentStatus.SUCCESS.getValue(), foundPayment.getStatus());
    }
}