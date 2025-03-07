package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {

    }

    @Override
    public Payment setStatus(Payment payment, String status) {

    }

    @Override
    public Payment getPayment(String paymentId) {

    }

    @Override
    public List<Payment> getAllPayments() {

    }

    @Override
    public Payment processCodPayment(Order order, Map<String, String> paymentData) {

    }
    
    private boolean isValidCodData(String address, String deliveryFee) {

    }
}