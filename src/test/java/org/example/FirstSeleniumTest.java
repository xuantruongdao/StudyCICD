package org.example;

import org.testng.Assert;
import org.testng.annotations.Test;

// 1. Kế thừa từ BaseTest để dùng chung cấu hình Driver
public class FirstSeleniumTest extends BaseTest {

    @Test
    public void testGoogleSearch() {
        // 2. Sử dụng biến 'driver' đã được khởi tạo sẵn ở BaseTest
        driver.get("https://www.google.com");

        // Lấy tiêu đề trang và kiểm tra (Assert)
        String title = driver.getTitle();
        System.out.println("Tieu de trang la : " + title);

        Assert.assertTrue(title.contains("Google"), "Tiêu đề không khớp!");
    }
}