package Firewall;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    @Test(priority = 1, description = "Kiểm tra giao diện trang chủ tải thành công sau khi login")
    public void testHomePageLoadsSuccessfully() {
        HomePage homePage = new HomePage(driver);

        // Xác nhận icon user hiển thị (chứng tỏ đã vào được trang chủ)
        Assert.assertTrue(homePage.isUserProfileDisplayed(), "Lỗi: Không tìm thấy User Profile, có thể chưa vào được trang chủ!");
    }

    @Test(priority = 2, description = "Kiểm tra điều hướng tới các menu chính của hệ thống")
    public void testNavigationMenus() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        // Click thử các menu
        homePage.clickDashboard();
        // Assert ví dụ: Đảm bảo không bị văng ra trang login sau khi click các menu
        Assert.assertFalse(driver.getCurrentUrl().contains("login"), "Lỗi: Click menu bị văng ra trang đăng nhập!");
    }
}