# Module 1: Coding Standard
## Reflection 1
You already implemented two new features using Spring Boot. Check again your source code and evaluate the coding standards that you have learned in this module. Write clean code principles and secure coding practices that have been applied to your code. If you find any mistake in your source code, please explain how to improve your code.

## Evaluating Coding Standards

### 1. Meaningful Names
In my code, none of the variables I named have vague meaning. I intentionally wrote it to be clear and concise.
Here is a snippet code example from `repository/ProductRepository.java`

```java
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

### 2. Function
I made my functions modular by giving each of them specific task. As shown in the example `repository/ProductRepository.java`

### 3. Comment
In this assignment I barely used any comments as I deemed it unecessary since the code already have meaningful names which makes it intuitive (easy) to understand what the code is trying to achieve.

### 4. Objects and Data Structure
In this assignment, I adhere by OOP principles by ensuring minimal dependencies between the variables. I do that by setting most of the variables as private.
Here is an example in my snippet of code in model/Product.java

```java
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

### 5. Error Handling
I added error handling on some important parts of the code. Here is an example of null error handling in `repository/ProductRepository.java`

```java
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

## Secure Coding Practices Applied

### 1. Encapsulation (OOP)
Fields in `Product.java` are private which prevents direct access.

### 2. UUID for unique productId
Generated unique ID in `Product.java` by writing the line `UUID.randomUUID().toString()`

### 3. Input Validation
`findById(String id)` in `ProductRepository.java` checks if id is null and throws an IllegalArgumentException, preventing Null Pointer Exceptions.

## If you find any mistake in your source code, please explain how to improve your code.

### 1. Lack of validation in current `delete()` method, it doesnt check if product exist before deletion attempt
```java
public void delete(String id) {
        productData.removeIf(p -> p.getProductId().equals(id));
    }
```

### 2. Lack of validation in current `update()` method, it allows for negative quantities or empty names.
```java
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

Now updated to:
```java
@Override    
public Product update(String productId, String newName, int newQuantity) {
   Product product = findById(productId);
   if (product != null) {
      product.setProductName(newName);
      product.setProductQuantity(newQuantity);
      if (newQuantity < 0) {
         product.setProductQuantity(0);
      }
      if ("".equals(newName)) {
         product.setProductName("Product name input is empty");
      }
   }
   return product;
}
```

# Reflection 2

## 1. After writing the unit test, how do you feel? How many unit tests should be made in a class? How to make sure that our unit tests are enough to verify our program?

### How I feel After Writing Unit Test
For me, writing unit test feel daunting but at the same time rewarding. As a novice to intermediate programmer, I feel it is necessary to learn not only to write good code logic but also to be able to think how to "counter" that logic by testing it with edgecases. Tests help ensure the written code works as expected and catch potential bugs early.

### How Many Unit Test Should Be in a Class and How to Ensure We Have Enough Tests
There is no fixed rule, but if we abide by the best practices:
- One test per scenario: Each test should verify a specific functionality or edge case.
- Aim for high coverage: At least 80% of code should be covered by tests.
- Test All Core Features: Each major function should have at least one test.
- Include Edge Cases: Test invalid inputs, empty fields, and boundary values.

### If you have 100% code coverage, does that mean your code has no bugs or errors?
Code coverage measures how much of the source code is being executed during a test. Eventhough high coverage correlates to minimum bugs, 100% code coverage doesn't exactly mean our code will have no bugs at all. Bugs can still exist due to missing edge cases, incorrect logic that isn't caught by QA (Quality Assurance), External dependencies not properly tested, etc.

## 2. Suppose that after writing the CreateProductFunctionalTest.java along with the corresponding test case, you were asked to create another functional test suite that verifies the number of items in the product list.

You decided to create a new Java class similar to the prior functional test suites with the same setup procedures and instance variables. What do you think about the cleanliness of the code of the new functional test suite? Will the new code reduce the code quality? Identify the potential clean code issues, explain the reasons, and suggest possible improvements to make the code cleaner!

If I were to create another functional test suite which is essentially similar to the CreateProductFunctionalTest. Then it would certainly go against clean code principles.

First, it would violate DRY (Don't Repeat Yourself) principle. Since we would have duplicated webdriver setup/teardown, port configuration, base URL handling, helper methods, etc. Besides that having multiple files with similar purposes only increases the maintenance burden, e.g. if we need to change how test are initialized or torned down we'd need to edit multiple files, configuration changes would need to be implemented to many more files, changes to helper methods would need to be replicated. Lastly, it would raise inconsistency risk as different test classes might need to be changed differently overtime.

### possible improvements to make the code cleaner would be:

#### 1. Create a Base Test Class
```java
@SpringBootTest(webEnvironment = RANDOM_PORT)
public abstract class FunctionalTestBase {
    @LocalServerPort
    protected Integer port;

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String baseUrl;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }
    ...
}
```

#### 2. Create Test Classes Which Inherits the Base Class
```java
class CreateProductFunctionalTest extends FunctionalTestBase {
    @Test
    void testCreateProduct_Success() {
        // Test implementation
    }
}

class ProductListFunctionalTest extends FunctionalTestBase {
    @Test
    void testProductListCount() {
        // Test implementation
    }
}
```

#### 3. Create Page Object Pattern
```java
class ProductListPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public ProductListPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public int getProductCount() {
        List<WebElement> products = driver.findElements(By.xpath("//tr[contains(@class, 'product-row')]"));
        return products.size();
    }

    public boolean isProductPresent(String productName) {
        // Implementation
    }
}
```

### Benefits of these improvements:
1. Single (modular) responsibility, each class focus on testing specific function
2. Readability, test become easier to understand
3. Reusability, as each class become more modular it is easier to use them for multiple purposes

# Module 2: CI/CD and DevOps
## Reflection
### Deployment Link:
- https://domestic-noelani-university-of-indonesia-fe0c0846.koyeb.app/

### Code Coverage:
![Image](https://github.com/user-attachments/assets/59728d50-3326-493a-aabc-74b1e9c56563)

1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.

There are several code quality issues which I found and fixed during the exercise, they include:
1. Unused import methods
There are some imports which I didn't realize have no use in the code file.

Example:
```java
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
```

Fixed to:
```java
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
```

2. Empty Method Head Body
As the name suggest, it means there are some methods were defined but didn't perform any operations.

Example in `ProductRepositoryTest.java`:
```java
@Test
void contextLoads() {}
```

Fixed to:
```java
@Test
void contextLoads() {
    // Setting up for loading context
}
```

3. Unused / unnecessary `public` modifier
This problem arise when I added `public` identifier to an interface, since an interface is public final by default, the problem arise.

Example:
```java
public interface ProductService {
    public Product create(Product product);
    public List<Product> findAll();
    public void deleteById(String productId);
    Product findById(String productId);
    Product update(String productId, String newName, int newQuantity);
}
```

Fixed to:
```java
public interface ProductService {
    Product create(Product product);
    List<Product> findAll();
    void deleteById(String productId);
    Product findById(String productId);
    Product update(String productId, String newName, int newQuantity);
}
```

4. Naming of variables which doesnt follow Java's naming convention (camelCase)
Some function names in my files uses inconsistent naming convention (snake case)
Example in `HomePageFunctionalTest.java`:
```java
@Test
void pageTitle_isCorrect(ChromeDriver driver) throws Exception {

// Exercise
driver.get(baseUrl);
String pageTitle = driver.getTitle();
```

Fixed to:
```java
@Test
void pageTitleCorrect(ChromeDriver driver) throws Exception {
    // Exercise
    driver.get(baseUrl);
    String pageTitle = driver.getTitle();

    // Verify
    assertEquals("ADV Shop", pageTitle);
}
```

2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!

In my opinion, the current implementation of my advanced programming eshop project has indeed met the definition of CI/CD. The reasons are:
- I use Github Actions to run my workflows like pmd and scorecard code scanner inside `.github/workflows` and by doing that it 
ensures that every push and pull request triggers automated checks for code quality and security. The pipeline includes automated testing and security scans, which help identify issues early in the development process, fulfilling the Continuous Integration (CI) principle.
The workflow enforces best practices such as branch protection, dependency updates, and security policy validation, which contribute to a more reliable and maintainable codebase.


- I use Koyeb for deployment, which means that every time I push changes to my repository, the application is automatically deployed. 
This ensures that the latest version is always live, fulfilling the requirements of Continuous Deployment (CD).

By integrating these, I think my projects does implement CI/CD.

# Module 3: OOP Principles
## Reflection

1) Explain what principles you apply to your project!

1.1. Single Responsibility Principle (SRP) <br>
The Single Responsibility Principle states that a class should have only one reason to change, meaning it should encapsulate only one aspect of the software's functionality. Here are my files and their purpose.
- - CarController: Handles HTTP requests and responses.
- - CarServiceImpl: Contains business logic related to cars.
- - CarRepository: Manages data storage and retrieval. 
- - CarIdGenerator: Responsible for generating unique car IDs.
- - Car: Represents the car entity as a data model.
Each class in the project has a clear, singular responsibility, following SRP correctly.


1.2. Open-Closed Principle (OCP)<br>
The Open-Closed Principle states that software entities (classes, modules, functions, etc.) should be open for extension but closed for modification.
New behaviors can be added without modifying existing classes.

For example, if we need to introduce a new way of filtering cars (e.g., by color or brand), we can extend CarService without modifying CarServiceImpl.

The CarRepository class is designed in a way that we can add additional query methods with no need to change the existing ones.

Thus, I think my code follows OCP correctly by allowing future extensions without modifying core logic.

1.3. Liskov Substitution Principle (LSP)<br>
The Liskov Substitution Principle states that objects of a subclass should be able to replace objects of the superclass without breaking functionality.

CarServiceImpl correctly implements CarService, meaning any reference to CarService (either read or write) can be replaced by an instance of CarServiceImpl.

1.4. Interface Segregation Principle (ISP)<br>
The Interface Segregation Principle states that large interfaces should be split into smaller, more specific ones so that clients only need to depend on the methods relevant to them. 
Since CarService and ProductService is separated, I think it my code already implements ISP. But I also split CarService into CarReadService and CarWriteService.

1.5. Dependency Inversion Principle (DIP)<br>
The Dependency Inversion Principle states that high-level modules should not depend on low-level modules. Both should depend on abstractions. I think my project applies this, here are the reasons:
- CarController depends on the abstraction CarReadService and CarWriteService, not directly on CarServiceImpl.
- CarServiceImpl depends on the abstraction CarRepository, not directly on the data source.
- Dependencies are injected using Spring’s @Autowired, ensuring abstraction reliance.

2) Explain the advantages of applying SOLID principles to your project with examples.

- Better Testability<br>
Since components are loosely coupled,  tests like test CarServiceImpl independently of CarRepository by using mock objects (DIP).

- Scalability<br>
If we introduce a new vehicle type or anything in that matter, we can create VariableService without modifying CarService (OCP).

3) Explain the disadvantages of not applying SOLID principles to your project with examples.
- If CarController depended directly on CarServiceImpl, switching implementations would be hard, making testing difficult. Hence I made CarController depended on CarService instead.
```java
@Controller
@RequestMapping("/car")
class CarController {
    private final CarService carservice;

    @Autowired
    public CarController(CarService carservice) {
        this.carservice = carservice;
    }
```

- If CarService and ProductService had all methods together (violating ISP), future updates would force changes in all implementing classes, even if they don’t need certain methods.
I also split CarService into two (read and write) to adhere to ISP.

```java
package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;

public interface CarWriteService {
    Car create(Car car);
    void update(String carId, Car car);
    void deleteCarById(String carId);
}
```

```java
package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import java.util.List;

public interface CarReadService {
    List<Car> findAll();
    Car findById(String carId);
}
```

# Module 4: TDD and Refactoring
## Reflection

1. Reflect based on Percival (2017) proposed self-reflective questions (in “Principles and Best Practice of Testing” submodule, chapter “Evaluating Your Testing Objectives”), 
whether this TDD flow is useful enough for you or not. If not, explain things that you need to do next time you make more tests.<br>

Reflecting on Percival’s principles, I found the Test-Driven Development (TDD) flow to be useful for the following reasons:

- Improved Code Confidence
Writing tests first ensured that the implementation met the expected behavior, reducing the risk of bugs.

- Better Code Structure
Since I focused on writing minimal code to pass each test, the final implementation was cleaner and more modular.

- Edge Case Coverage
The TDD process forced me to think about different edge cases (e.g., empty inputs, null values) before writing the implementation, making the code more robust.

However, there were also some challenges:
- Slower Initial Development: Writing tests before implementing the functionality felt slower compared to writing code directly.
- Refactoring Needed More Planning: After getting tests to pass, I found areas for improvement but had to ensure refactoring didn’t break anything.

2. Reflect based on Percival (2017) proposed self-reflective questions (in “Principles and Best Practice of Testing” submodule, chapter “Evaluating Your Testing Objectives”), 
whether this TDD flow is useful enough for you or not. If not, explain things that you need to do next time you make more tests.<br>

In my opinion, TDD is beneficial. But, eventhough the TDD flow was beneficial, I feel like I can improve the process by:
- More Modular Test Cases
Some test cases could be broken down further to isolate specific conditions (e.g., distinguishing between missing vs. empty fields).

- Mocking Dependencies Where Needed
If the class interacts with external dependencies (e.g., a database), using mocks could improve test efficiency.

- Better Test Naming
Using descriptive test names (e.g., testRejectPaymentWhenAddressIsEmpty) would make test failures easier to debug.

- Using Parameterized Tests
Instead of writing multiple similar test cases, using JUnit’s parameterized tests could make the test suite more maintainable.