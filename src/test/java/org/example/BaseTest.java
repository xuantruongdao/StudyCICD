package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setup() {
        // Đọc biến từ Jenkins/Maven (mặc định chạy Chrome, UI mode)
        String browser = System.getProperty("BROWSER", "chrome");
        String headlessMode = System.getProperty("HEADLESS", "false");

        System.out.println("--- Đang chạy trên trình duyệt: " + browser.toUpperCase() + " ---");

        if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions fOptions = new FirefoxOptions();
            if (headlessMode.equalsIgnoreCase("true")) fOptions.addArguments("-headless");
            driver = new FirefoxDriver(fOptions);

        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions eOptions = new EdgeOptions();
            if (headlessMode.equalsIgnoreCase("true")) eOptions.addArguments("--headless=new");
            driver = new EdgeDriver(eOptions);

        } else {
            // Mặc định: Chrome
            ChromeOptions cOptions = new ChromeOptions();
            if (headlessMode.equalsIgnoreCase("true")) cOptions.addArguments("--headless=new");
            cOptions.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(cOptions);
        }
        io.qameta.allure.Allure.getLifecycle().updateTestCase(result -> {
            result.setName(result.getName() + " [" + browser.toUpperCase() + "]");
        });
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}