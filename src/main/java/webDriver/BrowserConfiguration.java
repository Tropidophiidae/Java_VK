package webDriver;

import webDriver.browserConfig.Browser;
import webDriver.browserConfig.Chrome;
import webDriver.browserConfig.Firefox;

import static webDriver.Configuration.getConfigValue;

public class BrowserConfiguration {
    private Browser browser;
    private final String browserName;

    public BrowserConfiguration(){
        browserName = getConfigValue("browser");
        switch (browserName.toLowerCase()){
            case "chrome":{
                browser = new Chrome();
                break;
            }
            case "firefox":{
                browser = new Firefox();
                break;
            }
        }
    }
    public Browser getConfiguredBrowser() {
        return browser;
    }
}
