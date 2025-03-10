package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        if (product.getProductQuantity() < 0) {
            product.setProductQuantity(0);
        }
        if ("".equals(product.getProductName())) {
            product.setProductName("Product name input is empty");
        }
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public void deleteById(String id) {
        productData.removeIf(p -> p.getProductId().equals(id));
    }

    public Product findById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID is null");
        }

        for (Product product : productData) {
            if (product.getProductId().equals(id)) {
                return product;
            }
        }
        throw new NoSuchElementException("No product found with ID: " + id);
    }

    public Product update(String productId, String newName, int newQuantity) {
        Product product = findById(productId);

        product.setProductName(newName);
        product.setProductQuantity(newQuantity);
        if (newQuantity < 0) {
            product.setProductQuantity(0);
        }
        if ("".equals(newName)) {
            product.setProductName("Product name input is empty");
        }
        return product;
    }
}
