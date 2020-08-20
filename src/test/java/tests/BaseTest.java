package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static webDriver.Configuration.getConfigValue;
import static webDriver.DriverFactory.getDriver;
import static webDriver.DriverFactory.teardown;

public class BaseTest {

    public void goToStartPage(){
        getDriver().get(getConfigValue("startUrl"));
    }

    @BeforeMethod
    public void beforeTest(){
        getDriver().manage().window().maximize();
    }

    @AfterMethod
    public void afterTest(){
        teardown();
    }
}
