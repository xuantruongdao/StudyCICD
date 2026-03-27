package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    WebDriver driver;

    By usernameField = By.id("username");
    By passwordField = By.id("password");
    By loginButton = By.cssSelector("button[type='submit']");
    By successMessage = By.id("flash");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String user, String pass) {
        driver.findElement(usernameField).sendKeys(user);
        driver.findElement(passwordField).sendKeys(pass);
        driver.findElement(loginButton).click();
    }

    public String getResultMessage() {
        // Thêm Explicit Wait: Đợi tối đa 10 giây cho đến khi thông báo xuất hiện
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).getText();
    }
}