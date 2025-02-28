package id.ac.ui.cs.advprog.eshop.utils;

import java.util.UUID;

// Made CarIdGenerator to adhere to SRP
public class CarIdGenerator {
    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}
