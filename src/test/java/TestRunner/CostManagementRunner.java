package TestRunner;



import Pages.AdminPage;
import Pages.CostManagement;
import Pages.RegistrationPage;
import com.github.javafaker.Faker;
import config.CostManagementConfig;
import config.UserModel;
import net.bytebuddy.build.Plugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import config.Setup;
import utils.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CostManagementRunner extends Setup {

    AdminPage adminPage;



    @BeforeTest(groups = "smoke")
    public void NewUserCreate() throws InterruptedException, IOException, ParseException {
        driver.get("https://dailyfinance.roadtocareer.net/");
        RegistrationPage userReg = new RegistrationPage(driver);

      Faker faker = new Faker();
        userReg.btnRegister.get(1).click();
        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName();
       String email = "sumaiyat610+" + (int)(Math.random() * 500) + "@gmail.com";
       //String email = "sumaiyat610+103@gmail.com";
        String password = "6111";
        String phonenumber = "01756" + Utils.generateRandomNumber(100000, 999999);
        String address = faker.address().fullAddress();
        //UserModel Setup
        UserModel userModel = new UserModel();
        userModel.setFirstname(firstname);
        userModel.setLastname(lastname);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhonenumber(phonenumber);
        userModel.setAddress(address);
        userReg.doRegistration(userModel);

        //AssertionMethodCall
        //doRegAssertion();


        //Save to JSONArray
        JSONObject userObj = new JSONObject();
        userObj.put("firstName", firstname);
        userObj.put("lastName", lastname);
        userObj.put("email", email);
        userObj.put("password", password);
        userObj.put("phoneNumber", phonenumber);
        userObj.put("address", address);


        Utils.SaveAllInfo("./src/test/resources/users.json", userObj);
        Thread.sleep(5000);

    }
        @Test(priority = 1,description ="Login with last registered user for adding item", groups="smoke" )
        public void doLogin () throws IOException, ParseException, InterruptedException {
            Thread.sleep(7000);
            AdminPage adminPage = new AdminPage(driver);
            adminPage.btnLogin.click();
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("./src/test/resources/users.json"));

            JSONObject userObj = (JSONObject) jsonArray.get(jsonArray.size() - 1);


            String email = (String) userObj.get("email");
            String password = (String) userObj.get("password");

            if (System.getProperty("username") != null && System.getProperty("password") != null) {
                adminPage.doLogin(System.getProperty("username"), System.getProperty("password"));

            } else {
                adminPage.doLogin(email, password);
                //Thread.sleep(7000);
            }

        }


     @Test(priority = 2, dataProvider = "CSVDataSet",description ="Add Cost Item",groups = "smoke", dataProviderClass = CostManagementConfig.class)
        public void testAddExpenditure (String itemName, String amount, String quantity, String purchaseDate, String
        month, String remarks)
            throws InterruptedException {
            // double totalCost = 0.0;
            System.out.println("Item Name: " + itemName);
            System.out.println("Amount: " + amount);
            System.out.println("Quantity: " + quantity);
            System.out.println("Purchase Date: " + purchaseDate);
            System.out.println("Month: " + month);
            System.out.println("Remarks: " + remarks);


            CostManagement costManagement = new CostManagement(driver);

            costManagement.addExpenditure(itemName, amount, quantity, purchaseDate, month, remarks);



            Thread.sleep(5000);
            driver.switchTo().alert().accept();



    }







@Test(priority = 3,description = "Print the total cost and asserted it against my expected total sum of the amounts", groups = "smoke")
public void testTotalCost() throws InterruptedException {
    // Wait for the total cost element to be visible
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    WebElement totalCostFind = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Total Cost')]")));

    String totalCostText = totalCostFind.getText().replace("Total Cost:", "").replace("TK", "").trim();

    // Handle parsing exceptions
    double totalCost;
    try {
        totalCost = Double.parseDouble(totalCostText);
    } catch (NumberFormatException e) {
        System.out.println("Error parsing total cost: " + e.getMessage());
        totalCost = 0.0; // Default to 0.0 in case of error
    }

    // Calculate the expected total based on the amounts added from CSV
    double expectedTotal = calculateExpectedTotal("./src/test/resources/expenditures.csv"); // Adjust the path accordingly

    System.out.println("Parsed Total Cost: " + totalCost);
    System.out.println("Expected Total: " + expectedTotal);

    // Assert the total cost against the expected total
    Thread.sleep(5000);
    Assert.assertEquals(totalCost, expectedTotal, 0.01, "Total cost does not match expected value.");
}

    private double calculateExpectedTotal(String filePath) {
        double total = 0.0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Assuming the CSV has a structure where amounts are in a specific column
                String[] values = line.split(","); // Adjust delimiter if needed
                if (values.length > 1) { // Ensure there is at least one amount present
                    try {
                        total += Double.parseDouble(values[1].trim()); // Assuming amounts are in the second column (index 1)
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing amount from CSV: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }

        return total;
    }







@Test(priority = 4, description = "Search Item from the list and asserted that the total cost matches the item's price", groups = "smoke")
    public void searchItem() throws InterruptedException {
        String itemName = "Banana";  // The item you are searching for
        double expectedPrice = 50.0;  // The expected price of the item

        // Locate the search box and enter the item name
        WebElement searchBox = driver.findElement(By.className("search-input"));
        searchBox.clear();
        searchBox.sendKeys(itemName);
        searchBox.sendKeys(Keys.RETURN); // Submit the search

        // Wait for the price element to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Total Cost')]")));

        // Get the price text and clean it
        String priceText = priceElement.getText();
        priceText = priceText.replace("Total Cost: ", "").replace("TK", "").trim(); // Remove "Total Cost: " and "TK"

        // Debug: Print the priceText before parsing
        System.out.println("Price Text: " + priceText);

        try {
            // Parse the price
            double actualPrice = Double.parseDouble(priceText);

            // Debug: Print the actual price
            System.out.println("Actual Price: " + actualPrice);

            // Assert that the actual price matches the expected price
            Assert.assertEquals(actualPrice, expectedPrice, "The price for " + itemName + " does not match!");
        } catch (NumberFormatException e) {
            System.err.println("Error parsing price: " + e.getMessage());
            Assert.fail("Price could not be parsed.");
        }
    }



    @AfterClass(groups = "smoke")
    public void tearDown() {
        // Close the browser
        driver.quit();
    }



}












