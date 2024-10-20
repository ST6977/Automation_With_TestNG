package TestRunner;

import Pages.AdminPage;
import config.Setup;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class AdminPageRunner extends Setup {


    AdminPage adminPage;
   @Test(priority = 1, description = "Admin login with valid creds")
    public void AdminLogin(){
        AdminPage adminPage = new AdminPage(driver);
        adminPage.doLogin("admin@test.com", "admin123");

        //Asserrtion

        String headerActual= driver.findElement(By.tagName("h2")).getText();
        String headerExpected="Admin Dashboard";
        Assert.assertTrue(headerActual.contains(headerExpected));
        adminPage.doLogout();


        //clearCreds();
    }



    @AfterClass
    public void tearDown() {
        // Close the browser
        driver.quit();
    }



//    public void clearCreds(){
//        adminPage = new AdminPage(driver);
//        adminPage.txtEmail.sendKeys(Keys.CONTROL,"a");
//        adminPage .txtEmail.sendKeys(Keys.BACK_SPACE);
//        adminPage .txtPassword.sendKeys(Keys.CONTROL,"a");
//        adminPage .txtPassword.sendKeys(Keys.BACK_SPACE);
//    }
}
