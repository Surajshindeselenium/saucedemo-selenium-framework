package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * CartPage - Page Object Model for Shopping Cart Page
 * Handles shopping cart operations on SauceDemo
 */
public class CartPage extends BasePage {
    // Locators
    private final By pageTitle = By.cssSelector("[data-test='title']");
    private final By cartItems = By.cssSelector(".cart_item");
    private final By cartItemNames = By.cssSelector(".inventory_item_name");
    private final By cartItemPrices = By.cssSelector(".inventory_item_price");
    private final By removeButtons = By.cssSelector("[data-test*='remove']");
    private final By continueShoppingButton = By.id("continue-shopping");
    private final By checkoutButton = By.id("checkout");
    private final By cartBadge = By.cssSelector("[data-test='shopping-cart-badge']");
    private final By emptyCartMessage = By.cssSelector(".complete-header");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Get page title
     */
    public String getPageTitle() {
        return getText(pageTitle);
    }

    /**
     * Check if cart page is displayed
     */
    public boolean isCartPageDisplayed() {
        return isElementDisplayed(pageTitle);
    }

    /**
     * Get number of items in cart
     */
    public int getCartItemCount() {
        return driver.findElements(cartItems).size();
    }

    /**
     * Get list of cart item names
     */
    public List<WebElement> getCartItemNames() {
        return driver.findElements(cartItemNames);
    }

    /**
     * Get list of cart item prices
     */
    public List<WebElement> getCartItemPrices() {
        return driver.findElements(cartItemPrices);
    }

    /**
     * Remove item from cart by index
     */
    public void removeItemFromCart(int itemIndex) {
        List<WebElement> buttons = driver.findElements(removeButtons);
        if (itemIndex < buttons.size()) {
            buttons.get(itemIndex).click();
        }
    }

    /**
     * Remove all items from cart
     */
    public void removeAllItemsFromCart() {
        List<WebElement> buttons = driver.findElements(removeButtons);
        // Remove in reverse order to avoid index issues
        for (int i = buttons.size() - 1; i >= 0; i--) {
            buttons.get(i).click();
            // Refresh buttons list after each removal
            buttons = driver.findElements(removeButtons);
        }
    }

    /**
     * Click continue shopping button
     */
    public void clickContinueShopping() {
        clickElement(continueShoppingButton);
    }

    /**
     * Click checkout button
     */
    public void clickCheckout() {
        clickElement(checkoutButton);
    }

    /**
     * Get cart badge count
     */
    public int getCartBadgeCount() {
        try {
            return Integer.parseInt(getText(cartBadge));
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Check if cart is empty
     */
    public boolean isCartEmpty() {
        return getCartItemCount() == 0;
    }

    /**
     * Check if continue shopping button is displayed
     */
    public boolean isContinueShoppingButtonDisplayed() {
        return isElementDisplayed(continueShoppingButton);
    }

    /**
     * Check if checkout button is displayed
     */
    public boolean isCheckoutButtonDisplayed() {
        return isElementDisplayed(checkoutButton);
    }
}

