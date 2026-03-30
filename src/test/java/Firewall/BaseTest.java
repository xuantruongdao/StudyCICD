package Firewall;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected String browserName;
    // Chuyển từ @BeforeMethod sang @BeforeTest để chạy 1 lần cho cả chu trình
    @BeforeTest
    public void setup() {
        String browser = System.getProperty("BROWSER", "chrome");
        String headlessMode = System.getProperty("HEADLESS", "false");

        System.out.println("--- Khởi tạo chu trình test trên: " + browser.toUpperCase() + " ---");

        if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions fOptions = new FirefoxOptions();
            if (headlessMode.equalsIgnoreCase("true")) fOptions.addArguments("-headless");
            // Vượt lỗi bảo mật HTTPS cho Firefox
            fOptions.setAcceptInsecureCerts(true);
            driver = new FirefoxDriver(fOptions);

        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions eOptions = new EdgeOptions();
            if (headlessMode.equalsIgnoreCase("true")) eOptions.addArguments("--headless=new");
            // Vượt lỗi bảo mật HTTPS cho Edge
            eOptions.addArguments("--ignore-certificate-errors");
            driver = new EdgeDriver(eOptions);

        } else {
            ChromeOptions cOptions = new ChromeOptions();
            if (headlessMode.equalsIgnoreCase("true")) cOptions.addArguments("--headless=new");
            cOptions.addArguments("--remote-allow-origins=*");
            // Vượt lỗi bảo mật HTTPS cho Chrome
            cOptions.addArguments("--ignore-certificate-errors");
            cOptions.addArguments("--allow-insecure-localhost");
            driver = new ChromeDriver(cOptions);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // --- BƯỚC ĐĂNG NHẬP CHUNG CHO CẢ CHU TRÌNH ---
        loginToFirewall();
    }
    @BeforeMethod
    public void updateAllureName() {
        // Lúc này browserName đã có giá trị và không còn bị báo đỏ nữa
        io.qameta.allure.Allure.getLifecycle().updateTestCase(result -> {
            result.setName(result.getName() + " [" + browserName.toUpperCase() + "]");
            result.setHistoryId(result.getHistoryId() + "-" + browserName.toUpperCase());
        });
    }

    private void loginToFirewall() {
        System.out.println("--- Đang thực hiện đăng nhập hệ thống ---");
        driver.get("https://192.168.142.57/vi/login/");

        // Điền thông tin login (Thay ID/Name đúng với FW của bạn)
        try {
            driver.findElement(By.name("username")).sendKeys("truongdao");
            driver.findElement(By.name("password")).sendKeys("Testing@12345678");
            driver.findElement(By.xpath("//button[contains(text(),'ĐĂNG NHẬP')]")).click();
        } catch (Exception e) {
            System.out.println("Lỗi đăng nhập hoặc đã ở trong hệ thống: " + e.getMessage());
        }
    }

    @AfterTest // Chuyển sang @AfterTest để đóng trình duyệt khi xong cả chu trình
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}