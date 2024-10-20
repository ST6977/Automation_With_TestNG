package TestRunner;

import Pages.AdminPage;
import Pages.RegistrationPage;
import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import config.Setup;
import config.UserModel;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import utils.Utils;

import java.io.IOException;
import java.sql.Driver;
import java.time.Duration;
import java.util.Random;

public class RegistrationPageRunner extends Setup {



@Test(priority = 1, description = "Registration without mandatory field")
    public  void userRegByMissingMandatoryFields() throws InterruptedException {
        RegistrationPage userReg=new RegistrationPage(driver);
        userReg.btnRegister.get(1).click();
        userReg.btnSubmitReg.click();
        String validationError= userReg.txtFirstname.getAttribute("validationMessage");
        Assert.assertTrue(validationError.contains("Please fill out this field"));
           // AdminPage.doLogin();
       // Thread.sleep(2000);

    }


@Test( priority = 2, description = "User can register by providing all info")
    public void userRegByAllFields() throws InterruptedException, IOException, ParseException {
      driver.get("https://dailyfinance.roadtocareer.net/");
        RegistrationPage userReg=new RegistrationPage(driver);

        Faker faker=new Faker();
        userReg.btnRegister.get(1).click();
        String firstname=faker.name().firstName();
        String lastname=faker.name().lastName();

   String email = "sumaiyat610+" + (int)(Math.random() * 500) + "@gmail.com";
   //String email="sumaiyat610+104@gmail.com";
        String password = "6666";
        String phonenumber = "01756"+ Utils.generateRandomNumber(100000,999999);
        String address=faker.address().fullAddress();


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
   // Thread.sleep(5000);
      doRegAssertion();


         //Save to JSONArray
        JSONObject userObj=new JSONObject();
        userObj.put("firstName",firstname);
        userObj.put("lastName",lastname);
        userObj.put("email",email);
        userObj.put("password",password);
        userObj.put("phoneNumber",phonenumber);
        userObj.put("address",address);


        Utils.SaveAllInfo("./src/test/resources/users.json", userObj);
     Thread.sleep(5000);
    }



@Test(priority = 3, description = "User can register by providing only mandatory info")
    public void userRegByMandatoryFields() throws IOException, ParseException, InterruptedException {
        RegistrationPage userReg = new RegistrationPage(driver);

        Faker faker=new Faker();
        //Thread.sleep(2000);
        userReg.btnRegister.get(1).click();
        String firstname=faker.name().firstName();
        String lastname=faker.name().lastName();

       String email = "sumaiyat610+" + (int)(Math.random() * 500) + "@gmail.com";
       //String email="sumaiyat610+102@gmail.com";
        String password = "6666";
        String phonenumber = "01756"+ Utils.generateRandomNumber(100000,999999);
        String address=faker.address().fullAddress();



        //UserModel Setup
        UserModel userModel = new UserModel();
        userModel.setFirstname(firstname);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhonenumber(phonenumber);
        userReg.doRegistration(userModel);

        //Assertion Method Call
  // Thread.sleep(2000);
      doRegAssertion();

         //Save to JSONArray
        JSONObject userObj=new JSONObject();
        userObj.put("firstName",firstname);
        userObj.put("email",email);
        userObj.put("password",password);
        userObj.put("phoneNumber",phonenumber);



        Utils.SaveAllInfo("./src/test/resources/users.json", userObj);
  //Thread.sleep(5000);
    }









    public void doRegAssertion() throws InterruptedException {
        Thread.sleep(2000);
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("Toastify__toast")));
        String successMessageActual= driver.findElement(By.className("Toastify__toast")).getText();
        String successMessageExpected="successfully";
        System.out.println(successMessageActual);
        Assert.assertTrue(successMessageActual.contains(successMessageExpected));
    }





    @AfterClass
    public void tearDown() {
        // Close the browser
        driver.quit();
    }
}
