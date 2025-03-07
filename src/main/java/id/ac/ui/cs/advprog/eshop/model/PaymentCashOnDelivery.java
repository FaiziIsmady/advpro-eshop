package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;

import java.util.Map;

@Getter
public class PaymentCashOnDelivery {
    private Map<String, String> paymentData;

    public PaymentCashOnDelivery(Map<String, String> paymentData) {

    }

    public void setPaymentData(Map<String, String> paymentData) {

    }

    public PaymentStatus validatePaymentData() {

    }

    private boolean isNullOrEmpty(String value) {

    }
}