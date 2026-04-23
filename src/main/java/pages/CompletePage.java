package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * CompletePage - Page Object Model for Order Complete Page
 * Handles order confirmation on SauceDemo
 */
public class CompletePage extends BasePage {
    // Locators
    private final By completeHeader = By.cssSelector("[data-test='complete-header']");
    private final By completeText = By.cssSelector("[data-test='complete-text']");
    private final By backHomeButton = By.id("back-to-products");
    private final By checkmark = By.cssSelector(".pony_express");

    public CompletePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Get complete header text (order confirmation message)
     */
    public String getCompleteHeader() {
        return getText(completeHeader);
    }

    /**
     * Get complete text (order details)
     */
    public String getCompleteText() {
        return getText(completeText);
    }

    /**
     * Check if order complete page is displayed
     */
    public boolean isOrderCompletePageDisplayed() {
        return isElementDisplayed(completeHeader);
    }

    /**
     * Click back home button
     */
    public void clickBackHome() {
        clickElement(backHomeButton);
    }

    /**
     * Check if checkmark/confirmation icon is displayed
     */
    public boolean isCheckmarkDisplayed() {
        return isElementDisplayed(checkmark);
    }

    /**
     * Check if back home button is displayed
     */
    public boolean isBackHomeButtonDisplayed() {
        return isElementDisplayed(backHomeButton);
    }
}

