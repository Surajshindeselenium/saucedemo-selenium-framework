package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            
            // Disable Chrome password manager and breach warning popups
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("profile.default_content_setting_values.notifications", 2); // Block notifications
            prefs.put("profile.default_content_setting_values.popups", 0); // Block popups
            options.setExperimentalOption("prefs", prefs);
            
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--disable-password-manager-reauthentication");
            options.addArguments("--disable-features=PasswordManager,PasswordLeakDetection");
            options.addArguments("--no-first-run");
            options.addArguments("--no-default-browser-check");
            options.addArguments("--incognito");
            options.addArguments("--user-data-dir=" + System.getProperty("java.io.tmpdir") + "/chromeProfile_" + System.currentTimeMillis());
            
            // Add headless mode for CI/CD environments
            String headlessMode = System.getProperty("HEADLESS", System.getenv("HEADLESS"));
            if ("true".equalsIgnoreCase(headlessMode)) {
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.addArguments("--disable-extensions");
            }
            
            driver.set(new ChromeDriver(options));
        }
        getDriver().manage().window().maximize();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        getDriver().quit();
        driver.remove();
    }
}