package webDriver.browserConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.openqa.selenium.WebDriver;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static logger.log4j2.log;

public abstract class Browser {
    protected final String configPath = "src/test/resources/browser.json";
    protected WebDriver driver;
    protected String browserDownloadDirectory;



    protected String browserLanguage;
    protected String browserName;
    protected String downloadDirectoryKey;

    protected Map<String, String> readConfigFile() {
        log().debug("Reading browser configuration");
        Map<String, String> config = Collections.emptyMap();
        JsonElement root;
        try {
            root = new JsonParser().parse(new FileReader(configPath));
            String jsonString = root.getAsJsonObject().get(browserName).getAsJsonObject().toString();
            config = new ObjectMapper().readValue(jsonString, Map.class);
            if (config.containsKey(downloadDirectoryKey)) {
                config.put(downloadDirectoryKey, System.getProperty("user.dir") + config.get(downloadDirectoryKey));
            }
        } catch (IOException e) {
            log().debug(e);
        }
        setBrowserLanguage(config.get("intl.accept_languages"));
        return config;
    }

    public void setDownloadDirectoryKey(String downloadDirectoryKey) {
        this.downloadDirectoryKey = downloadDirectoryKey;
    }

    public void setBrowserName(String browserName) {
        log().debug("Browser = " + browserName);
        this.browserName = browserName;
    }

    public abstract WebDriver setUpDriver();

    public String getBrowserLanguage(){
        return browserLanguage;
    }

    public String getBrowserDownloadDirectory(){
        return browserDownloadDirectory;
    }

    public void setBrowserDownloadDirectory(String browserDownloadDirectory){
        log().debug("Browser download directory = " + browserDownloadDirectory);
        this.browserDownloadDirectory = browserDownloadDirectory;
    }

    public void setBrowserLanguage(String browserLanguage) {
        log().debug("Browser language = " + browserLanguage);
        this.browserLanguage = browserLanguage;
    }
}
