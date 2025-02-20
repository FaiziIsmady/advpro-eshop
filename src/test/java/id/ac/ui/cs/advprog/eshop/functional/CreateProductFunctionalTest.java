package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CreateProductFunctionalTest {
    @LocalServerPort
    private Integer serverPort;
    private WebDriver browser;
    private String appRoot;

    @BeforeAll
    static void initializeDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void prepareBrowser() {
        ChromeOptions config = new ChromeOptions();
        config.addArguments("--no-display");
        browser = new ChromeDriver(config);
        appRoot = "http://localhost:" + serverPort;
    }

    @AfterEach
    void closeBrowser() {
        if (browser != null) {
            browser.close();
        }
    }

    @Test
    void verifyProductCreationWorksCorrectly() {
        browser.navigate().to(appRoot + "/product/create");

        WebElement itemNameField = browser.findElement(By.id("nameInput"));
        WebElement stockField = browser.findElement(By.id("quantityInput"));
        itemNameField.sendKeys("Demo Item");
        stockField.sendKeys("75");

        browser.findElement(By.cssSelector("button[type='submit']")).click();

        // Verify redirection to the product list page
        assertTrue(browser.getCurrentUrl().contains("/product/list"),
                "Should redirect to product listing after successful submission");

        // Verify the created product is displayed in the list
        WebElement createdEntry = browser.findElement(
                By.xpath("//td[text()='Demo Item']"));
        WebElement storedQuantity = createdEntry.findElement(
                By.xpath("./following-sibling::td[1]"));

        assertEquals("75", storedQuantity.getText(),
                "Submitted quantity should match stored value");
    }

    @Test
    void checkBlankProductNameHandling() {
        browser.get(appRoot + "/product/create");

        WebElement countField = browser.findElement(By.name("productQuantity"));
        countField.sendKeys("3");

        browser.findElement(By.xpath("//button[text()='Submit']")).click();

        WebElement defaultNameItem = browser.findElement(By.cssSelector("table tr:last-child td:first-child"));
        assertEquals("Product name input is empty", defaultNameItem.getText(),
                "System should assign default name when empty");
    }

    @Test
    void validateInterfaceNavigation() {
        browser.get(appRoot + "/");

        WebElement catalogLink = browser.findElement(By.partialLinkText("Buy Products"));
        catalogLink.click();

        WebElement addNewButton = browser.findElement(By.linkText("Create Product"));
        addNewButton.click();

        String currentPath = browser.getCurrentUrl().replace(appRoot, "");
        assertTrue(currentPath.startsWith("/product/create"),
                "Should reach product creation page through navigation");
    }

    @Test
    void examineMissingQuantityHandling() {
        browser.get(appRoot + "/product/create");

        WebElement nameField = browser.findElement(By.id("nameInput"));
        nameField.sendKeys("Quantityless Item");

        browser.findElement(By.cssSelector("form [type='submit']")).click();

        WebElement quantityCell = browser.findElement(
                By.xpath("//td[contains(.,'Quantityless Item')]/following-sibling::td"));
        assertEquals("0", quantityCell.getText(),
                "Empty quantity should default to zero");
    }
}