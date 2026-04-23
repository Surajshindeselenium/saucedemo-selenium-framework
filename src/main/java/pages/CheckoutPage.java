package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * CheckoutPage - Page Object Model for Checkout Page
 * Handles checkout process on SauceDemo
 */
public class CheckoutPage extends BasePage {
    // Locators for Checkout Step One
    private final By pageTitle = By.cssSelector("[data-test='title']");
    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By cancelButton = By.id("cancel");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Get page title
     */
    public String getPageTitle() {
        return getText(pageTitle);
    }

    /**
     * Check if checkout page is displayed
     */
    public boolean isCheckoutPageDisplayed() {
        return isElementDisplayed(pageTitle);
    }

    /**
     * Enter first name
     */
    public void enterFirstName(String firstName) {
        enterText(firstNameField, firstName);
    }

    /**
     * Enter last name
     */
    public void enterLastName(String lastName) {
        enterText(lastNameField, lastName);
    }

    /**
     * Enter postal code
     */
    public void enterPostalCode(String postalCode) {
        enterText(postalCodeField, postalCode);
    }

    /**
     * Fill checkout information
     */
    public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
    }

    /**
     * Click continue button
     */
    public void clickContinue() {
        clickElement(continueButton);
    }

    /**
     * Click cancel button
     */
    public void clickCancel() {
        clickElement(cancelButton);
    }

    /**
     * Get error message
     */
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    /**
     * Check if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    /**
     * Check if continue button is enabled
     */
    public boolean isContinueButtonEnabled() {
        return driver.findElement(continueButton).isEnabled();
    }

    /**
     * Check if first name field is displayed
     */
    public boolean isFirstNameFieldDisplayed() {
        return isElementDisplayed(firstNameField);
    }

    /**
     * Check if last name field is displayed
     */
    public boolean isLastNameFieldDisplayed() {
        return isElementDisplayed(lastNameField);
    }

    /**
     * Check if postal code field is displayed
     */
    public boolean isPostalCodeFieldDisplayed() {
        return isElementDisplayed(postalCodeField);
    }
}

