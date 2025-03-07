package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Order order;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());

        order = new Order("ORDER001", products, System.currentTimeMillis(), "user1");

        paymentData = new HashMap<>();
        paymentData.put("testKey", "testValue");
    }

    @Test
    void testAddPayment() {
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment payment = paymentService.addPayment(order, "VOUCHER", paymentData);

        assertNotNull(payment);
        assertEquals(order.getId(), payment.getOrderId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());

        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void testSetStatusToSuccess() {
        Payment payment = new Payment("PAY001", "VOUCHER", paymentData);
        payment.setOrderId(order.getId());

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderRepository.findById(order.getId())).thenReturn(order);

        Payment updatedPayment = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), updatedPayment.getStatus());
        assertEquals(PaymentStatus.SUCCESS.getValue(), order.getStatus());

        verify(paymentRepository).save(any(Payment.class));
        verify(orderRepository).save(order);
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = new Payment("PAY001", "VOUCHER", paymentData);
        payment.setOrderId(order.getId());

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderRepository.findById(order.getId())).thenReturn(order);

        Payment updatedPayment = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), updatedPayment.getStatus());
        assertEquals("FAILED", order.getStatus());

        verify(paymentRepository).save(any(Payment.class));
        verify(orderRepository).save(order);
    }

    @Test
    void testGetPayment() {
        Payment payment = new Payment("PAY001", "VOUCHER", paymentData);

        when(paymentRepository.findById("PAY001")).thenReturn(payment);

        Payment foundPayment = paymentService.getPayment("PAY001");

        assertNotNull(foundPayment);
        assertEquals("PAY001", foundPayment.getId());

        verify(paymentRepository).findById("PAY001");
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        payments.add(new Payment("PAY001", "VOUCHER", paymentData1));

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("address", "123 Main St");
        paymentData2.put("deliveryFee", "10000");
        payments.add(new Payment("PAY002", "COD", paymentData2));

        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> allPayments = paymentService.getAllPayments();

        assertEquals(2, allPayments.size());

        verify(paymentRepository).findAll();
    }

    @Test
    void testProcessCodPaymentValid() {
        Map<String, String> codData = new HashMap<>();
        codData.put("address", "123 Main St");
        codData.put("deliveryFee", "10000");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment payment = paymentService.processCodPayment(order, codData);

        assertNotNull(payment);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());

        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void testProcessCodPaymentInvalid() {
        Map<String, String> codData = new HashMap<>();
        codData.put("address", ""); // Empty address

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment payment = paymentService.processCodPayment(order, codData);

        assertNotNull(payment);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());

        verify(paymentRepository).save(any(Payment.class));
    }
}