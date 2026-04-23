package testCases;

import base.DriverFactory;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import testBase.BaseTest;
import utilities.TestDataProvider;
import java.util.Map;

/**
 * SauceLabsBackpackCheckoutTestWithHeaders
 * Test case to verify end-to-end checkout flow with Sauce Labs Backpack
 * Uses header-based Excel data (recommended approach)
 * 
 * Flow:
 * 1. Login with standard user
 * 2. Add 'Sauce Labs Backpack' to cart
 * 3. Click on cart
 * 4. Click on checkout
 * 5. Enter First name, last name, and zip code from custDetails (2nd row with headers)
 * 6. Click continue
 * 7. Verify checkout details
 * 8. Click finish
 * 
 * Expected Excel Structure (custDetails.xlsx):
 * | firstName | lastName | postalCode |
 * |-----------|----------|-----------|
 * | John      | Doe      | 12345     |  <- 2nd row (index 1)
 * | Jane      | Smith    | 54321     |
 */
public class SauceLabsBackpackCheckoutTestWithHeaders extends BaseTest {

    @Test(description = "End-to-end checkout test with header-based Excel data")
    public void testSauceLabsBackpackCheckoutWithHeaders() {
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
            // GET TEST DATA FROM 2ND ROW (INDEX 1) OF EXCEL
            // This assumes the Excel file has headers: firstName, lastName, postalCode
            Map<String, String> testData = testDataProvider.getTestDataByRow(1);  // 2nd row
            
            Assert.assertNotNull(testData, "Test data should not be null");
            
            String firstName = testData.get("firstName");
            String lastName = testData.get("lastName");
            String postalCode = testData.get("postalCode");
            
            System.out.println("═══════════════════════════════════════");
            System.out.println("Test Data from Excel (2nd Row):");
            System.out.println("FirstName: " + firstName);
            System.out.println("LastName: " + lastName);
            System.out.println("PostalCode: " + postalCode);
            System.out.println("═══════════════════════════════════════");
            
            // Step 1: Login with standard user
            String standardUser = ConfigReader.getProperty("standard_user");
            String standardPassword = ConfigReader.getProperty("standard_password");
            
            Assert.assertTrue(loginPage.isLoginPageDisplayed(), 
                "Login page should be displayed");
            
            System.out.println("\n1. Logging in with standard user...");
            loginPage.login(standardUser, standardPassword);
            
            // Verify login successful
            Assert.assertTrue(productsPage.isProductsPageDisplayed(), 
                "Products page should be displayed after successful login");
            System.out.println("   ✓ Login successful");
            
            // Step 2: Add 'Sauce Labs Backpack' to cart
            System.out.println("\n2. Adding 'Sauce Labs Backpack' to cart...");
            productsPage.addProductToCartByName("Sauce Labs Backpack");
            
            // Verify product added to cart
            int cartBadgeCount = productsPage.getCartBadgeCount();
            Assert.assertEquals(cartBadgeCount, 1, 
                "Cart badge should show 1 item after adding Sauce Labs Backpack");
            System.out.println("   ✓ Product added to cart");
            
            // Step 3: Click on shopping cart
            System.out.println("\n3. Clicking on shopping cart...");
            productsPage.clickShoppingCart();
            
            // Verify cart page displayed
            Assert.assertTrue(cartPage.isCartPageDisplayed(), 
                "Cart page should be displayed");
            
            // Verify product is in cart
            Assert.assertEquals(cartPage.getCartItemCount(), 1, 
                "Cart should contain 1 item");
            System.out.println("   ✓ Cart page displayed with correct item");
            
            // Step 4: Click on checkout
            System.out.println("\n4. Clicking on checkout...");
            cartPage.clickCheckout();
            
            // Verify checkout page displayed
            Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed(), 
                "Checkout page should be displayed");
            System.out.println("   ✓ Checkout page displayed");
            
            // Step 5: Fill checkout information with Excel data
            System.out.println("\n5. Filling checkout information...");
            Assert.assertTrue(checkoutPage.isFirstNameFieldDisplayed(), 
                "First name field should be displayed");
            Assert.assertTrue(checkoutPage.isLastNameFieldDisplayed(), 
                "Last name field should be displayed");
            Assert.assertTrue(checkoutPage.isPostalCodeFieldDisplayed(), 
                "Postal code field should be displayed");
            
            checkoutPage.fillCheckoutInformation(firstName, lastName, postalCode);
            System.out.println("   ✓ Checkout information filled from Excel data");
            
            // Step 6: Click continue
            System.out.println("\n6. Clicking continue button...");
            checkoutPage.clickContinue();
            
            // Verify checkout overview page displayed
            Assert.assertTrue(checkoutOverviewPage.isCheckoutOverviewPageDisplayed(), 
                "Checkout overview page should be displayed");
            System.out.println("   ✓ Checkout overview page displayed");
            
            // Step 7: Verify checkout details
            System.out.println("\n7. Verifying checkout details...");
            Assert.assertEquals(checkoutOverviewPage.getItemCount(), 1, 
                "Overview should contain 1 item");
            
            // Verify Sauce Labs Backpack is in the overview
            String itemName = checkoutOverviewPage.getItemNames().get(0).getText();
            Assert.assertTrue(itemName.contains("Backpack"), 
                "Product name should contain 'Backpack'");
            System.out.println("   ✓ Product verified: " + itemName);
            
            // Verify financial details are displayed
            String subtotal = checkoutOverviewPage.getSubtotal();
            String tax = checkoutOverviewPage.getTax();
            String total = checkoutOverviewPage.getTotal();
            
            Assert.assertNotNull(subtotal, "Subtotal should be displayed");
            Assert.assertNotNull(tax, "Tax should be displayed");
            Assert.assertNotNull(total, "Total should be displayed");
            
            System.out.println("   ✓ Subtotal: " + subtotal);
            System.out.println("   ✓ Tax: " + tax);
            System.out.println("   ✓ Total: " + total);
            
            // Verify finish button is displayed
            Assert.assertTrue(checkoutOverviewPage.isFinishButtonDisplayed(), 
                "Finish button should be displayed");
            
            // Step 8: Click finish
            System.out.println("\n8. Clicking finish button to complete order...");
            checkoutOverviewPage.clickFinish();
            
            // Verify order completed
            Assert.assertTrue(completePage.isOrderCompletePageDisplayed(), 
                "Order complete page should be displayed");
            
            // Verify checkmark is displayed (order confirmation)
            Assert.assertTrue(completePage.isCheckmarkDisplayed(), 
                "Checkmark should be displayed on completion page");
            
            // Verify completion message
            String completeHeader = completePage.getCompleteHeader();
            System.out.println("   ✓ Order Confirmation: " + completeHeader);
            Assert.assertNotNull(completeHeader, 
                "Completion header message should be displayed");
            
            System.out.println("\n═══════════════════════════════════════");
            System.out.println("✓ TEST COMPLETED SUCCESSFULLY!");
            System.out.println("═══════════════════════════════════════");
            
        } finally {
            // Close test data provider
            testDataProvider.closeTestData();
        }
    }
}

