import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FirstSeleniumTest {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        // Cấu hình để chạy được trên Jenkins (Headless Mode)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // Chạy ngầm, không mở cửa sổ trình duyệt
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
    }

    @Test
    public void checkGoogleTitle() {
        driver.get("https://www.google.com");
        String title = driver.getTitle();
        System.out.println("Title cua trang là: " + title);
        System.out.println("Jenkins dang tu dong chay bai test nay!");
        System.out.println("Jenkins dang tu dong chay bai test nay hahaha!");

        // Kiểm tra xem tiêu đề có chứa chữ Google không
        Assert.assertTrue(title.contains("Google"), "Tiêu đề không khớp!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}