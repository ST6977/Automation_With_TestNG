package TestRunner;

import Pages.AdminPage;
import Pages.RegistrationPage;
import com.github.javafaker.Faker;
import config.Setup;
import config.UserModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.Utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class AdminDashboardTestRunner extends Setup {
 AdminPage adminPage;


 @BeforeTest

 public void doLogin() throws IOException, ParseException {
  AdminPage adminPage = new AdminPage(driver);

  if (System.getProperty("username") != null && System.getProperty("password") != null) {
   adminPage.doLogin(System.getProperty("username"), System.getProperty("password"));
  } else {
   adminPage.doLogin("admin@test.com", "admin123");
  }



 }


@Test(priority = 1, description = "checking last registered user is in the list or not" )
 public void UserListCheck() throws IOException, ParseException, InterruptedException {
    AdminPage adminPage = new AdminPage(driver);
    JSONParser parser = new JSONParser();
    JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("./src/test/resources/users.json"));

    JSONObject userObj = (JSONObject) jsonArray.get(jsonArray.size() - 1);
    String jsonEmail = (String) userObj.get("email");
    String jsonPhone = (String) userObj.get("phoneNumber");
    String jsonFirstName = (String) userObj.get("firstName");



    List<WebElement> dataRows = driver.findElements(By.tagName("tr"));
    String dashboardFirstName;
    String dashboardEmail;
    String dashboardPhone;
    if (dataRows.size() > 1) { // Ensure there is at least one data row
        dashboardFirstName = dataRows.get(1).findElements(By.tagName("td")).get(0).getText();
        dashboardEmail = dataRows.get(1).findElements(By.tagName("td")).get(2).getText();
        dashboardPhone = dataRows.get(1).findElements(By.tagName("td")).get(3).getText();

        // Print user details
        System.out.println("First Name: " + dashboardFirstName);
        System.out.println("Email: " + dashboardEmail);
        System.out.println("Phone: " + dashboardPhone);

      Thread.sleep(5000);

     Assert.assertEquals(dashboardFirstName, jsonFirstName, "First names do not match!");
     Assert.assertEquals(dashboardEmail, jsonEmail, "Emails do not match!");
     Assert.assertEquals(dashboardPhone, jsonPhone, "Phone numbers do not match!");
    }
    else{
     System.out.println("No data rows found.");
    }





}




    @AfterClass
    public void tearDown() {
        // Close the browser
        driver.quit();
    }


}








