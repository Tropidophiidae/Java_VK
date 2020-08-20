package webDriver.browserConfig;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

import static logger.log4j2.log;

public class Chrome extends Browser{
    private final String browserName = "chrome";
    private final String downloadDirectoryKey = "download.default_directory";

    public WebDriver setUpDriver() {
        setBrowserName(browserName);
        setDownloadDirectoryKey(downloadDirectoryKey);
        Map<String, String> configMap = readConfigFile();
        setBrowserDownloadDirectory(configMap.get(downloadDirectoryKey));
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("prefs", configMap);
        WebDriverManager.chromedriver().setup();
        log().debug("WebDriver initialization");
        driver = new ChromeDriver(chromeOptions);
        return driver;
    }
}