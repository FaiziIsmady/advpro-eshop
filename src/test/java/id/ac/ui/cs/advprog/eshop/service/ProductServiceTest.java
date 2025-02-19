package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("123");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
    }

    @Test
    void testCreateProduct() {
        when(productRepository.create(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.create(product);

        assertNotNull(createdProduct);
        assertEquals("123", createdProduct.getProductId());
        assertEquals("Test Product", createdProduct.getProductName());
        assertEquals(10, createdProduct.getProductQuantity());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        Iterator<Product> productIterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(productIterator);

        List<Product> foundProducts = productService.findAll();

        assertEquals(1, foundProducts.size());
        assertEquals(product.getProductId(), foundProducts.get(0).getProductId());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testDeleteProductById() {
        doNothing().when(productRepository).deleteById("123");

        productService.deleteById("123");

        verify(productRepository, times(1)).deleteById("123");
    }

    @Test
    void testFindById() {
        when(productRepository.findById("123")).thenReturn(product);

        Product foundProduct = productService.findById("123");

        assertNotNull(foundProduct);
        assertEquals("123", foundProduct.getProductId());
        verify(productRepository, times(1)).findById("123");
    }

    @Test
    void testUpdateProduct() {
        Product updatedMockProduct = new Product();
        updatedMockProduct.setProductId("123");
        updatedMockProduct.setProductName("Updated Product");
        updatedMockProduct.setProductQuantity(20);

        when(productRepository.update("123", "Updated Product", 20)).thenReturn(updatedMockProduct);

        Product updatedProduct = productService.update("123", "Updated Product", 20);

        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getProductName());
        assertEquals(20, updatedProduct.getProductQuantity());
        verify(productRepository, times(1)).update("123", "Updated Product", 20);
    }
}
