package bars;

import org.openqa.selenium.By;

import static webDriver.DriverFactory.getDriver;

public class Header {
    private final By xpathUserMenu = By.id("top_profile_link");
    private final By xpathUserMenuLogOut = By.id("top_logout_link");

    public void logOut() {
        getDriver().findElement(xpathUserMenu).click();
        getDriver().findElement(xpathUserMenuLogOut).click();
    }
}
