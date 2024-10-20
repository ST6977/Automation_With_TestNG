package TestRunner;

import Pages.AdminPage;
import config.Setup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class UserLoginTestRunner extends Setup {
    AdminPage adminPage;


 @Test(priority = 1, description = "User can login with correct creds")
    public void userLogin() throws ParseException, IOException {
        adminPage = new AdminPage(driver);
        JSONParser parser=new JSONParser();
        JSONArray jsonArray= (JSONArray) parser.parse(new FileReader("./src/test/resources/users.json"));
        JSONObject userObj= (JSONObject) jsonArray.get(jsonArray.size()-1);
        String email= (String) userObj.get("email");
        String password= (String) userObj.get("password");

        adminPage.doLogin(email,password);
        Assert.assertTrue(adminPage.btnProfileIcon.isDisplayed());
        adminPage.doLogout();
       // Assert.assertTrue(adminPage.btnProfileIcon.isDisplayed());
    }




    @AfterClass
    public void tearDown() {
        // Close the browser
        driver.quit();
    }
}
