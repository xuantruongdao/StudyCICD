package org.example;

import org.testng.Assert;
import org.testng.annotations.Test;

// Sử dụng chữ "extends BaseTest"
public class LoginTest extends BaseTest {

    @Test
    public void testSuccessfulLogin() {
        driver.get("https://the-internet.herokuapp.com/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("tomsmith", "SuperSecretPassword!");
        Assert.assertTrue(loginPage.getResultMessage().contains("You logged into a secure area!"));
    }
}