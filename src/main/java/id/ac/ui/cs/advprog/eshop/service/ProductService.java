package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface ProductService {
    Product create(Product product);
    List<Product> findAll();
    void deleteById(String productId);
    Product findById(String productId);
    Product update(String productId, String newName, int newQuantity);
}