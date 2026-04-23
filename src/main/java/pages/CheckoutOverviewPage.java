package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * CheckoutOverviewPage - Page Object Model for Checkout Overview Page
 * Handles checkout overview/summary on SauceDemo
 */
public class CheckoutOverviewPage extends BasePage {
    // Locators
    private final By pageTitle = By.cssSelector("[data-test='title']");
    private final By cartItems = By.cssSelector(".cart_item");
    private final By itemNames = By.cssSelector(".inventory_item_name");
    private final By itemPrices = By.cssSelector(".inventory_item_price");
    private final By subtotal = By.cssSelector("[data-test='subtotal-label']");
    private final By tax = By.cssSelector("[data-test='tax-label']");
    private final By total = By.cssSelector("[data-test='total-label']");
    private final By finishButton = By.id("finish");
    private final By cancelButton = By.id("cancel");

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Get page title
     */
    public String getPageTitle() {
        return getText(pageTitle);
    }

    /**
     * Check if checkout overview page is displayed
     */
    public boolean isCheckoutOverviewPageDisplayed() {
        return isElementDisplayed(pageTitle);
    }

    /**
     * Get number of items in overview
     */
    public int getItemCount() {
        return driver.findElements(cartItems).size();
    }

    /**
     * Get list of item names
     */
    public List<WebElement> getItemNames() {
        return driver.findElements(itemNames);
    }

    /**
     * Get list of item prices
     */
    public List<WebElement> getItemPrices() {
        return driver.findElements(itemPrices);
    }

    /**
     * Get subtotal amount
     */
    public String getSubtotal() {
        return getText(subtotal);
    }

    /**
     * Get tax amount
     */
    public String getTax() {
        return getText(tax);
    }

    /**
     * Get total amount
     */
    public String getTotal() {
        return getText(total);
    }

    /**
     * Click finish button to complete order
     */
    public void clickFinish() {
        clickElement(finishButton);
    }

    /**
     * Click cancel button
     */
    public void clickCancel() {
        clickElement(cancelButton);
    }

    /**
     * Check if finish button is displayed
     */
    public boolean isFinishButtonDisplayed() {
        return isElementDisplayed(finishButton);
    }

    /**
     * Check if cancel button is displayed
     */
    public boolean isCancelButtonDisplayed() {
        return isElementDisplayed(cancelButton);
    }
}

