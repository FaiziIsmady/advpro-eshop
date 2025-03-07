package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

public class PaymentVoucher {
    private String voucherCode;
    
    public PaymentVoucher(String voucherCode) {
        this.voucherCode = voucherCode;
    }
    
    public String getVoucherCode() {
        return voucherCode;
    }
    
    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }
    
    public PaymentStatus validateVoucher() {
        if (!isValidVoucherFormat()) {
            return PaymentStatus.REJECTED;
        }
        return PaymentStatus.SUCCESS;
    }
    
    private boolean isValidVoucherFormat() {
        if (voucherCode == null || voucherCode.length() != 16) {
            return false;
        }

        if (!voucherCode.startsWith("ESHOP")) {
            return false;
        }

        int numericCount = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) {
                numericCount++;
            }
        }

        return numericCount >= 8;
    }
}