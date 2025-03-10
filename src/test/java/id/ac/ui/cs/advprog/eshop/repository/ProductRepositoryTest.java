package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        // Setting up test
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testUpdate() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Edit the product with new values
        Product updatedProduct = productRepository.update(
                "eb558e9f-1c39-460e-8860-71af6af63bd6",
                "Sampo Cap Bambang Updated",
                200
        );

        // Check if values are updated
        assertNotNull(updatedProduct);
        assertEquals("Sampo Cap Bambang Updated", updatedProduct.getProductName());
        assertEquals(200, updatedProduct.getProductQuantity());
    }

    @Test
    void testUpdateNegativeQuantity() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Edit the product with new values
        Product updatedProduct = productRepository.update(
                "eb558e9f-1c39-460e-8860-71af6af63bd6",
                "Sampo Cap Bambang Updated",
                -200
        );

        // Check if values are updated
        assertNotNull(updatedProduct);
        assertEquals("Sampo Cap Bambang Updated", updatedProduct.getProductName());
        assertEquals(0, updatedProduct.getProductQuantity());
    }

    @Test
    void testDeleteById() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Delete the product by ID
        productRepository.deleteById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        // Ensure the repository is now empty
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext(), "Product list should be empty after deletion");
    }

    @Test
    void testCreateValidation() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("");
        product.setProductQuantity(-1);

        Product savedProduct = productRepository.create(product);

        assertEquals("Product name input is empty", savedProduct.getProductName(), "Product name should not be empty");
        assertEquals(0, savedProduct.getProductQuantity(),"Product quantity should be 0 if input is negative");
    }

    @Test
    void testUpdateValidation() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Edit the product with new values
        Product updatedProduct = productRepository.update(
                "eb558e9f-1c39-460e-8860-71af6af63bd6",
                "",
                -1
        );

        // Check if values are updated
        assertNotNull(updatedProduct);
        assertEquals("Product name input is empty", updatedProduct.getProductName());
        assertEquals(0, updatedProduct.getProductQuantity());
    }

    @Test
    void testFindByIdWithNullId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productRepository.findById(null);
        });

        assertEquals("Product ID is null", exception.getMessage());
    }

    @Test
    void testFindByIdWithNonExistingId() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            productRepository.findById("non-existing-id");
        });

        assertEquals("No product found with ID: non-existing-id", exception.getMessage());
    }

    @Test
    void testFindByIdWithExistingId() {
        Product product1 = new Product();
        product1.setProductId("id1");
        product1.setProductName("Product 1");
        product1.setProductQuantity(10);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("id2");
        product2.setProductName("Product 2");
        product2.setProductQuantity(20);
        productRepository.create(product2);

        // Searching for the second product to ensure full loop execution
        Product foundProduct = productRepository.findById("id2");

        assertNotNull(foundProduct);
        assertEquals("id2", foundProduct.getProductId());
    }

    @Test
    void testUpdateNonExistentProduct() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            productRepository.update("non-existent-id", "Invalid Update", 50);
        });

        assertEquals("No product found with ID: non-existent-id", exception.getMessage());
    }
}
