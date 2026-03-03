package de.dhl;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class HomeTest {
    private AndroidDriver driver;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        String appPath = new File("src/test/resources/login-demo-app.apk").getAbsolutePath();

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setDeviceName("emulator-5554")
                .setApp(appPath)
                .setAutomationName("UiAutomator2");

        // Ультра-таймауты для GitHub Actions
        options.setCapability("appium:uiautomator2ServerLaunchTimeout", 180000); // 3 минуты
        options.setCapability("appium:uiautomator2ServerInstallTimeout", 180000);
        options.setCapability("appium:androidInstallTimeout", 180000);
        options.setCapability("appium:adbExecTimeout", 120000);
        options.setCapability("appium:deviceReadyTimeout", 180000);

        // Отключаем лишние проверки при запуске
        options.setCapability("appium:skipServerInstallation", false);
        options.setCapability("appium:noSign", true); // Не переподписывать APK (ускоряет запуск)

        // Если приложение тяжелое, даем больше времени на парсинг манифеста
        options.setCapability("appium:appWaitDuration", 60000);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
    }

    @Test
    public void testScreenOpened() {
        String titleMyAppLocator = "//android.view.View[@content-desc=\"Моё приложение\"]";

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement titleMyApp = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath(titleMyAppLocator))
        );

        String actualContentDescForTitleMyApp = titleMyApp.getAttribute("content-desc");
        Assertions.assertEquals("Моё приложение", actualContentDescForTitleMyApp);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
