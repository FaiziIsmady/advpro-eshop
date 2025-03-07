package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;

import java.util.Map;

@Getter
public class PaymentCashOnDelivery {
    private Map<String, String> paymentData;

    public PaymentCashOnDelivery(Map<String, String> paymentData) {
        if (paymentData == null) {
            throw new IllegalArgumentException("Payment data cannot be null");
        }
        this.paymentData = paymentData;
    }

    public void setPaymentData(Map<String, String> paymentData) {
        if (paymentData == null) {
            throw new IllegalArgumentException("Payment data cannot be null");
        }
        this.paymentData = paymentData;
    }

    public PaymentStatus validatePaymentData() {
        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");

        if (isNullOrEmpty(address) || isNullOrEmpty(deliveryFee)) {
            return PaymentStatus.REJECTED;
        }

        return PaymentStatus.SUCCESS;
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}