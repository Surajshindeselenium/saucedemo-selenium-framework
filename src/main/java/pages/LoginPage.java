package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * LoginPage - Page Object Model for Login Page
 * Handles login functionality for SauceDemo
 */
public class LoginPage extends BasePage {
    // Locators
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");
    private final By sauceLogo = By.cssSelector(".login_logo");
    private final By locked_user_error = By.xpath("//h3[contains(text(),'Sorry, this user has been locked out.')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Enter username in username field
     */
    public void enterUsername(String username) {
        enterText(usernameField, username);
    }

    /**
     * Enter password in password field
     */
    public void enterPassword(String password) {
        enterText(passwordField, password);
    }

    /**
     * Click on login button
     */
    public void clickLoginButton() {
        clickElement(loginButton);
    }

    /**
     * Login with username and password
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    /**
     * Get error message text
     */
    public String getLockedUserErrorMessage() {
        return getText(locked_user_error);
    }

    /**
     * Check if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    /**
     * Check if login page is displayed
     */
    public boolean isLoginPageDisplayed() {
        return isElementDisplayed(sauceLogo);
    }

    /**
     * Check if login button is enabled
     */
    public boolean isLoginButtonEnabled() {
        return driver.findElement(loginButton).isEnabled();
    }
}

