package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentVoucherTest {
    
    @Test
    void testValidVoucher() {
        PaymentVoucher voucher = new PaymentVoucher("ESHOP1234ABC5678");
        
        PaymentStatus status = voucher.validateVoucher();
        
        assertEquals(PaymentStatus.SUCCESS, status);
    }

    @Test
    void testInvalidVoucherMissingEshop() {
        PaymentVoucher voucher = new PaymentVoucher("INVALID1234ABC5678");
        
        PaymentStatus status = voucher.validateVoucher();
        
        assertEquals(PaymentStatus.REJECTED, status);
    }

    @Test
    void testInvalidVoucherInsufficientNumbers() {
        PaymentVoucher voucher = new PaymentVoucher("ESHOP123ABC567");
        
        PaymentStatus status = voucher.validateVoucher();
        
        assertEquals(PaymentStatus.REJECTED, status);
    }

    @Test
    void testInvalidVoucherShortLength() {
        PaymentVoucher voucher = new PaymentVoucher("ESHOP123");
        
        PaymentStatus status = voucher.validateVoucher();
        
        assertEquals(PaymentStatus.REJECTED, status);
    }

    @Test
    void testInvalidVoucherLongLength() {
        PaymentVoucher voucher = new PaymentVoucher("ESHOP1234567890ABCDEF");
        
        PaymentStatus status = voucher.validateVoucher();
        
        assertEquals(PaymentStatus.REJECTED, status);
    }
    
    @Test
    void testSetVoucherCode() {
        PaymentVoucher voucher = new PaymentVoucher("INVALID");
        voucher.setVoucherCode("ESHOP1234ABC5678");
        
        PaymentStatus status = voucher.validateVoucher();
        
        assertEquals(PaymentStatus.SUCCESS, status);
        assertEquals("ESHOP1234ABC5678", voucher.getVoucherCode());
    }
}