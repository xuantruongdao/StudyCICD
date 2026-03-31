package Firewall;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // 1. Khai báo các locators (Thay đổi giá trị By.id/xpath cho khớp với HTML thực tế của Firewall)
    private By menuDashboard = By.xpath("//span[contains(text(),'i t') and contains(text(),'ng')]");
    private By userProfileIcon = By.xpath("//span[normalize-space()='truongdao']");

    // 2. Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // 3. Các hàm hành động (Actions)
    public void clickDashboard() {
        wait.until(ExpectedConditions.elementToBeClickable(menuDashboard)).click();
        System.out.println("Đã click vào menu Dashboard.");
    }
    public boolean isUserProfileDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(userProfileIcon)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}