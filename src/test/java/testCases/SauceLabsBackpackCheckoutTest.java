package testCases;

import base.DriverFactory;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import testBase.BaseTest;
import utilities.TestDataProvider;

/**
 * SauceLabsBackpackCheckoutTest
 * Test case to verify end-to-end checkout flow with Sauce Labs Backpack
 * 
 * Flow:
 * 1. Login with standard user
 * 2. Add 'Sauce Labs Backpack' to cart
 * 3. Click on cart
 * 4. Click on checkout
 * 5. Enter First name, last name, and zip code from custDetails (2nd row)
 * 6. Click continue
 * 7. Verify checkout details
 * 8. Click finish
 */
public class SauceLabsBackpackCheckoutTest extends BaseTest {

    @Test(description = "End-to-end checkout test with Sauce Labs Backpack using data from Excel")
    public void testSauceLabsBackpackCheckout() {
        // Initialize page objects
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        ProductsPage productsPage = new ProductsPage(DriverFactory.getDriver());
        CartPage cartPage = new CartPage(DriverFactory.getDriver());
        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
        CheckoutOverviewPage checkoutOverviewPage = new CheckoutOverviewPage(DriverFactory.getDriver());
        CompletePage completePage = new CompletePage(DriverFactory.getDriver());
        
        // Initialize Excel data provider
        TestDataProvider testDataProvider = new TestDataProvider();
        
        try {
            // Step 1: Login with standard user
            String standardUser = ConfigReader.getProperty("standard_user");
            String standardPassword = ConfigReader.getProperty("standard_password");
            
            Assert.assertTrue(loginPage.isLoginPageDisplayed(), 
                "Login page should be displayed");
            
            loginPage.login(standardUser, standardPassword);
            
            // Verify login successful
            Assert.assertTrue(productsPage.isProductsPageDisplayed(), 
                "Products page should be displayed after successful login");
            
            // Step 2: Add 'Sauce Labs Backpack' to cart
            productsPage.addProductToCartByName("Sauce Labs Backpack");
            
            // Verify product added to cart
            int cartBadgeCount = productsPage.getCartBadgeCount();
            Assert.assertEquals(cartBadgeCount, 1, 
                "Cart badge should show 1 item after adding Sauce Labs Backpack");
            
            // Step 3: Click on shopping cart
            productsPage.clickShoppingCart();
            
            // Verify cart page displayed
            Assert.assertTrue(cartPage.isCartPageDisplayed(), 
                "Cart page should be displayed");
            
            // Verify product is in cart
            Assert.assertEquals(cartPage.getCartItemCount(), 1, 
                "Cart should contain 1 item");
            
            // Step 4: Click on checkout
            cartPage.clickCheckout();
            
            // Verify checkout page displayed
            Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed(), 
                "Checkout page should be displayed");
            
            // Step 5: Get data from 2nd row (index 1) of Excel file
            // Assuming Excel has columns: firstName, lastName, postalCode
            String firstName = testDataProvider.getCellValue(1, 0);    // Row 1, Col 0
            String lastName = testDataProvider.getCellValue(1, 1);     // Row 1, Col 1
            String postalCode = testDataProvider.getCellValue(1, 2);   // Row 1, Col 2
            
            System.out.println("Test Data - FirstName: " + firstName + 
                             ", LastName: " + lastName + 
                             ", PostalCode: " + postalCode);
            
            // Verify all input fields are displayed
            Assert.assertTrue(checkoutPage.isFirstNameFieldDisplayed(), 
                "First name field should be displayed");
            Assert.assertTrue(checkoutPage.isLastNameFieldDisplayed(), 
                "Last name field should be displayed");
            Assert.assertTrue(checkoutPage.isPostalCodeFieldDisplayed(), 
                "Postal code field should be displayed");
            
            // Fill checkout information
            checkoutPage.fillCheckoutInformation(firstName, lastName, postalCode);
            
            // Step 6: Click continue
            checkoutPage.clickContinue();
            
            // Verify checkout overview page displayed
            Assert.assertTrue(checkoutOverviewPage.isCheckoutOverviewPageDisplayed(), 
                "Checkout overview page should be displayed");
            
            // Step 7: Verify checkout details
            // Verify product is in overview
            Assert.assertEquals(checkoutOverviewPage.getItemCount(), 1, 
                "Overview should contain 1 item");
            
            // Verify Sauce Labs Backpack is in the overview
            String itemName = checkoutOverviewPage.getItemNames().get(0).getText();
            Assert.assertTrue(itemName.contains("Backpack"), 
                "Product name should contain 'Backpack'");
            
            // Verify financial details are displayed
            String subtotal = checkoutOverviewPage.getSubtotal();
            String tax = checkoutOverviewPage.getTax();
            String total = checkoutOverviewPage.getTotal();
            
            Assert.assertNotNull(subtotal, "Subtotal should be displayed");
            Assert.assertNotNull(tax, "Tax should be displayed");
            Assert.assertNotNull(total, "Total should be displayed");
            
            System.out.println("Subtotal: " + subtotal);
            System.out.println("Tax: " + tax);
            System.out.println("Total: " + total);
            
            // Verify finish button is displayed
            Assert.assertTrue(checkoutOverviewPage.isFinishButtonDisplayed(), 
                "Finish button should be displayed");
            
            // Step 8: Click finish
            checkoutOverviewPage.clickFinish();
            
            // Verify order completed
            Assert.assertTrue(completePage.isOrderCompletePageDisplayed(), 
                "Order complete page should be displayed");
            
            // Verify checkmark is displayed (order confirmation)
            Assert.assertTrue(completePage.isCheckmarkDisplayed(), 
                "Checkmark should be displayed on completion page");
            
            // Verify completion message
            String completeHeader = completePage.getCompleteHeader();
            System.out.println("Order Confirmation Message: " + completeHeader);
            Assert.assertNotNull(completeHeader, 
                "Completion header message should be displayed");
            
            System.out.println("✓ Test completed successfully!");
            
        } finally {
            // Close test data provider
            testDataProvider.closeTestData();
        }
    }
}

