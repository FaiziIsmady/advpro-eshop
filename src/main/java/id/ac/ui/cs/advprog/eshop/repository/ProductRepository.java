package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public void delete(String id) {
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
}
