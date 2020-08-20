package webDriver.browserConfig;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.Map;

import static logger.log4j2.log;

@SuppressWarnings("ALL")
public class Firefox extends Browser {
    private final String browserName = "firefox";
    private final String downloadDirectoryKey = "browser.download.dir";

    public WebDriver setUpDriver() {
        setBrowserName(browserName);
        setDownloadDirectoryKey(downloadDirectoryKey);
        FirefoxProfile fxProfile = new FirefoxProfile();
        Map<String, String> configMap = readConfigFile();
        for (Map.Entry<String, String> entry : configMap.entrySet()) {
            fxProfile.setPreference(entry.getKey(), entry.getValue());
        }
        fxProfile.setPreference("browser.download.folderList", 2);
        setBrowserDownloadDirectory(configMap.get(downloadDirectoryKey));
        FirefoxOptions firefoxOptions = new FirefoxOptions().setProfile(fxProfile);
        WebDriverManager.firefoxdriver().setup();
        log().debug("WebDriver initialization");
        driver = new FirefoxDriver(firefoxOptions);
        return driver;
    }
}
