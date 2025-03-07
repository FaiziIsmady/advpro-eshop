package id.ac.ui.cs.advprog.eshop.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    REJECTED("REJECTED"),
    SUCCESS("SUCCESS");

    private final String value;

    private PaymentStatus(String value) {
        this.value = value;
    }

    public static boolean contains(String status) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.getValue().equals(status)) {
                return true;
            }
        }
        return false;
    }
}
