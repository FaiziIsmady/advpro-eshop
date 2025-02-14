package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
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
class EditProductFunctionalTest {
    @LocalServerPort
    private Integer portNumber;
    private WebDriver webClient;
    private String baseAddress;

    @BeforeAll
    static void prepareDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void initializeSession() {
        ChromeOptions settings = new ChromeOptions();
        settings.addArguments("--window-size=1920,1080");
        settings.addArguments("--no-sandbox");
        webClient = new ChromeDriver(settings);
        baseAddress = "http://localhost:" + portNumber;
    }

    @AfterEach
    void terminateSession() {
        if (webClient != null) {
            webClient.close();
        }
    }

    private void generateTestProduct(String itemName, String stockCount) {
        webClient.navigate().to(baseAddress + "/product/create");
        webClient.findElement(By.cssSelector("#nameInput")).sendKeys(itemName);
        webClient.findElement(By.cssSelector("input.form-control[id='quantityInput']")).sendKeys(stockCount);
        webClient.findElement(By.cssSelector("form button.btn-primary")).click();
    }

    @Test
    void verifyItemUpdateProcess() {
        generateTestProduct("Initial Item", "15");

        webClient.navigate().to(baseAddress + "/product/list");
        webClient.findElement(By.xpath("//tr[td[text()='Initial Item']]//a[contains(@href,'edit')]")).click();

        WebElement nameField = webClient.findElement(By.cssSelector("[name='productName']"));
        WebElement stockField = webClient.findElement(By.cssSelector("input[type='number']"));
        nameField.clear();
        nameField.sendKeys("Modified Item");
        stockField.clear();
        stockField.sendKeys("25");
        webClient.findElement(By.xpath("//button[contains(.,'Update')]")).click();

        WebElement updatedStock = webClient.findElement(
                By.xpath("//td[text()='Modified Item']/following-sibling::td[1]"));
        assertEquals("25", updatedStock.getText(),
                "Inventory count should reflect updated value");
    }
}