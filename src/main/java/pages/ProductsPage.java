package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * ProductsPage - Page Object Model for Products/Inventory Page
 * Handles product listing and product interactions on SauceDemo
 */
public class ProductsPage extends BasePage {
    // Locators
    private final By pageTitle = By.cssSelector("[data-test='title']");
    private final By productContainer = By.cssSelector(".inventory_list");
    private final By productItems = By.cssSelector(".inventory_item");
    private final By productNames = By.cssSelector(".inventory_item_name ");
    private final By productPrices = By.cssSelector(".inventory_item_price");
    private final By addToCartButtons = By.cssSelector("[data-test*='add-to-cart']");
    private final By removeButtons = By.cssSelector("[data-test*='remove']");
    private final By cartBadge = By.cssSelector("[data-test='shopping-cart-badge']");
    private final By cartLink = By.cssSelector("[data-test='shopping-cart-link']");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By sortDropdown = By.cssSelector("[data-test='product-sort-container']");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Get page title
     */
    public String getPageTitle() {
        return getText(pageTitle);
    }

    /**
     * Check if products page is displayed
     */
    public boolean isProductsPageDisplayed() {
        return isElementDisplayed(pageTitle);
    }

    /**
     * Get number of products displayed
     */
    public int getProductCount() {
        return driver.findElements(productItems).size();
    }

    /**
     * Get list of product names
     */
    public List<WebElement> getProductNames() {
        return driver.findElements(productNames);
    }

    /**
     * Get list of product prices
     */
    public List<WebElement> getProductPrices() {
        return driver.findElements(productPrices);
    }

    /**
     * Add product to cart by index
     */
    public void addProductToCart(int productIndex) {
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        if (productIndex < buttons.size()) {
            buttons.get(productIndex).click();
        }
    }

    /**
     * Add product to cart by name
     */
    public void addProductToCartByName(String productName) {
        List<WebElement> products = driver.findElements(productItems);
        for (WebElement product : products) {
            if (product.getText().contains(productName)) {
                WebElement addButton = product.findElement(By.cssSelector("[data-test*='add-to-cart']"));
                addButton.click();
                break;
            }
        }
    }

    /**
     * Remove product from cart by index
     */
    public void removeProductFromCart(int productIndex) {
        List<WebElement> buttons = driver.findElements(removeButtons);
        if (productIndex < buttons.size()) {
            buttons.get(productIndex).click();
        }
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
     * Click on shopping cart
     */
    public void clickShoppingCart() {
        clickElement(cartLink);
    }

    /**
     * Click on menu button
     */
    public void clickMenuButton() {
        clickElement(menuButton);
    }

    /**
     * Select sorting option
     */
    public void selectSortOption(String sortOption) {
        clickElement(sortDropdown);
        By optionLocator = By.cssSelector("option[value='" + sortOption + "']");
        clickElement(optionLocator);
    }

    /**
     * Check if add to cart button exists for a product
     */
    public boolean isAddToCartButtonDisplayed(int productIndex) {
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        return productIndex < buttons.size() && buttons.get(productIndex).isDisplayed();
    }
}

