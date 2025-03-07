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
        String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(paymentId, method, paymentData);
        payment.setOrderId(order.getId());
        return paymentRepository.save(payment);
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);
        paymentRepository.save(payment);

        // Update order status based on payment status
        Order order = orderRepository.findById(payment.getOrderId());
        if (order != null) {
            if ("SUCCESS".equals(status)) {
                order.setStatus("SUCCESS");
            } else if ("REJECTED".equals(status)) {
                order.setStatus("FAILED");
            }
            orderRepository.save(order);
        }

        return payment;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment processCodPayment(Order order, Map<String, String> paymentData) {
        Payment payment = new Payment(UUID.randomUUID().toString(), "COD", paymentData);
        payment.setOrderId(order.getId());

        if (isValidCodData(paymentData.get("address"), paymentData.get("deliveryFee"))) {
            payment.setStatus("SUCCESS");
            order.setStatus("SUCCESS");
        } else {
            payment.setStatus("REJECTED");
            order.setStatus("FAILED");
        }

        paymentRepository.save(payment);
        orderRepository.save(order);

        return payment;
    }

    private boolean isValidCodData(String address, String deliveryFee) {
        return address != null && !address.isEmpty() &&
                deliveryFee != null && !deliveryFee.isEmpty();
    }
}