package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
    WebDriver driver;
    LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // Bắt buộc để chạy trên Jenkins
        driver = new ChromeDriver(options);
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