package webDriver;

import org.openqa.selenium.WebDriver;
import webDriver.browserConfig.Browser;

import java.util.concurrent.TimeUnit;

import static webDriver.Configuration.getConfigValue;

public final class DriverFactory {
    private static WebDriver driver;
    private static DriverFactory instance;
    private static Browser browser;



    public DriverFactory() {
        browser = new BrowserConfiguration().getConfiguredBrowser();
        driver = browser.setUpDriver();
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(getConfigValue("timeout")), TimeUnit.SECONDS);
    }

    public static DriverFactory getInstance() {
        if (instance == null) {
            instance = new DriverFactory();
        }
        return instance;
    }

    public static WebDriver getDriver() {
        getInstance();
        return driver;
    }

    public static void teardown() {
        getInstance();
        getDriver().quit();
        instance = null;
    }

    public static Browser getBrowser(){
        getInstance();
        return browser;
    }
}