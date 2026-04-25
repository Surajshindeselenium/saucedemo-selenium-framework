package testCases;

import base.DriverFactory;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import testBase.BaseTest;

public class SauceLabsLockedUser extends BaseTest {
    @Test(description = "Verify that login fails with locked credentials")
    public void testLockedUserLogin() {
        // Initialize page objects
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());

        // Attempt to login with locked user credentials
        String lockedUser = ConfigReader.getProperty("locked_user");
        String lockedPassword = ConfigReader.getProperty("locked_password");

        loginPage.login(lockedUser, lockedPassword);

        // Verify that error message is displayed for locked user
        String expectedErrorMessage = "Epic sadface: Sorry, this user has been locked out.";
        String actualErrorMessage = loginPage.getLockedUserErrorMessage();

        Assert.assertEquals(actualErrorMessage, expectedErrorMessage,
            "Error message should indicate that the user is locked out");
    }
}
