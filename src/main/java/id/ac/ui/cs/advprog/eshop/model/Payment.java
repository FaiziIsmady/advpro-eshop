package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;
    private String orderId;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.status = PaymentStatus.REJECTED.getValue(); // Default status
        setPaymentData(paymentData);
    }

    public Payment(String id, String method, Map<String, String> paymentData, String status) {
        this(id, method, paymentData);
        setStatus(status);
    }

    public void setStatus(String status) {
        if (isValidStatus(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setPaymentData(Map<String, String> paymentData) {
        if (paymentData == null || paymentData.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.paymentData = paymentData;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    private boolean isValidStatus(String status) {
        return status.equals("SUCCESS") || status.equals("REJECTED");
    }
}