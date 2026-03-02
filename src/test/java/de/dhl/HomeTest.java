package de.dhl;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class HomeTest {
    private AndroidDriver driver;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        // Настройка параметров (Capabilities) для подключения к устройству
        String appPath = new File("src/test/resources/login-demo-app.apk").getAbsolutePath();
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setDeviceName("emulator-5554")
                .setApp(appPath)
                .setAutomationName("UiAutomator2");

        // Инициализация драйвера и подключение к Appium Server
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
    }

    @Test
    public void testScreenOpened() {
        String titleMyAppLocator = "//android.view.View[@content-desc=\"Моё приложение\"]";

        //Thread.sleep(4000);

        WebElement titleMyApp = driver.findElement(By.xpath(titleMyAppLocator));
        String actualContentDescForTitleMyApp = titleMyApp.getAttribute("content-desc");

        Assertions.assertEquals("Моё приложение", actualContentDescForTitleMyApp);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Завершение сессии после каждого теста
        }
    }
}
