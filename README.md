Module 1: Coding Standard <br>
Reflection 1 <br>
You already implemented two new features using Spring Boot. Check again your source code and evaluate the coding standards that you have 
learned in this module. Write clean code principles and secure coding practices that have been applied to your code.  If you find any 
mistake in your source code, please explain how to improve your code. <br>

Evaluating Coding Standards
1. Meaningful Names <br>
In my code, none of the variables I named have vague meaning. I intentionally wrote it to be clear and concise.
Here is a snippet code example from `repository/ProductRepository.java`
```aiignore
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
        for (Product product : productData) {
            if (product.getProductId().equals(id)) {
                return product;
            }
        }
        return null; // If not found return null for now
    }
}

```

2. Function <br>
I made my functions modular by giving each of them specific task. As shown in the example `repository/ProductRepository.java`

3. Comment <br>
In this assignment I barely used any comments as I deemed it unecessary since the code already have meaningful names which makes
it intuitive (easy) to understand what the code is trying to achieve.

4. Objects and Data Structure <br>
In this assignment, I adhere by OOP principles by ensuring minimal dependencies between the variables. I do that by setting most of the variables as private.
Here is an example in my snippet of code in model/Product.java
```aiignore
@Getter @Setter
public class Product {
    private String productId;
    private String productName;
    private int productQuantity;

    public Product() {
        this.productId = UUID.randomUUID().toString(); // Generate unique ID
    }
}
```
5. Error Handling <br>
I added error handling on some important parts of the code. Here is an example of null error handling in `repository/ProductRepository.java`
```aiignore
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
```

Secure Coding Practices Applied.<br>
1. Encapsulation (OOP)<br>
Fields in `Product.java` are private which prevents direct access.


2. UUID for unique productId. <br>
Generated unique ID in `Product.java` by writing the line `UUID.randomUUID().toString()`


3. Input Validation <br>
   `findById(String id)` in `ProductRepository.java` checks if id is null and throws an IllegalArgumentException, 
preventing Null Pointer Exceptions.

If you find any mistake in your source code, please explain how to improve your code. <br>

1. Lack of validation in current `delete()` method, it doesnt check if product exist before deletion attempt
```aiignore
public void delete(String id) {
        productData.removeIf(p -> p.getProductId().equals(id));
    }
```

2. Lack of validation in current `update()` method, it allows for negative quantities or empty names.
```aiignore
@Override
    public Product update(String productId, String newName, int newQuantity) {
        Product product = findById(productId);
        if (product != null) {
            product.setProductName(newName);
            product.setProductQuantity(newQuantity);
        }
        return product;
    }
```

