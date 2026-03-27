package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class LoginTest {
    WebDriver driver;
    LoginPage loginPage;

//    @BeforeMethod
//    public void setup() {
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless=new"); // Bắt buộc để chạy trên Jenkins
//        driver = new ChromeDriver(options);
//        driver.get("https://the-internet.herokuapp.com/login");
//        loginPage = new LoginPage(driver);
//    }
    @BeforeMethod
    public void setup() {
        // 1. Đọc biến BROWSER và HEADLESS
        String browser = System.getProperty("BROWSER", "chrome"); // Mặc định là chrome
        String headlessMode = System.getProperty("HEADLESS", "false");

        // 2. Khởi tạo Driver dựa trên lựa chọn
        if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions fOptions = new FirefoxOptions();
            if (headlessMode.equalsIgnoreCase("true")) fOptions.addArguments("-headless");
            driver = new FirefoxDriver(fOptions);
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions eOptions = new EdgeOptions();
            if (headlessMode.equalsIgnoreCase("true")) eOptions.addArguments("--headless=new");
            driver = new EdgeDriver(eOptions);
        } else {
            // Mặc định chạy Chrome
            ChromeOptions cOptions = new ChromeOptions();
            if (headlessMode.equalsIgnoreCase("true")) cOptions.addArguments("--headless=new");
            cOptions.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(cOptions);
        }

        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/login");
        loginPage = new LoginPage(driver);
    }
    @Test
    public void testSuccessfulLogin() {
        loginPage.login("tomsmith", "SuperSecretPassword!");
        String message = loginPage.getResultMessage();
        Assert.assertTrue(message.contains("You logged into a secure area!"), "Đăng nhập thất bại rồi!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}