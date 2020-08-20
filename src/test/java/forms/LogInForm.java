package forms;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static webDriver.Configuration.getConfigValue;
import static webDriver.DriverFactory.getDriver;

public class LogInForm {

    private final By xpathEmailField = By.id("index_email");
    private final By xpathPasswordField = By.id("index_pass");
    private final By xpathLogInButton = By.id("index_login_button");
    private final By xpathMyProfileLink = By.id("l_pr");
    private final int logInTimeout = Integer.parseInt(getConfigValue("timeout"));
    private final By xpathQuickLogInForm = By.id("quick_login");


    public void LogIn(String username, String password ) {
        getDriver().findElement(xpathEmailField).sendKeys(username);
        getDriver().findElement(xpathPasswordField).sendKeys(password);
        getDriver().findElement(xpathLogInButton).click();
        WebDriverWait wait = new WebDriverWait(getDriver(),logInTimeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(xpathMyProfileLink));
    }

    public boolean isQuickLogInFormDisplayed(){
        return getDriver().findElements(xpathQuickLogInForm).size() > 0;
    }
}
