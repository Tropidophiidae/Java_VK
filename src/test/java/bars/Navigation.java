package bars;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static webDriver.DriverFactory.getDriver;
import static webDriver.Configuration.getConfigValue;

public class Navigation {
    private final By xpathMyProfileLink = By.id("l_pr");
    private final By xpathMyProfile = By.id("profile_edit_act");
    private final int openMyProfileTimeout = Integer.parseInt(getConfigValue("timeout"));

    public void goToMyProfile(){
        getDriver().findElement(xpathMyProfileLink).click();
        WebDriverWait wait = new WebDriverWait(getDriver(),openMyProfileTimeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(xpathMyProfile));
    }
}
