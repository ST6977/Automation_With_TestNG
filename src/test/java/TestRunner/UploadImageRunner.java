package TestRunner;

import Pages.AdminPage;
import Pages.UploadImage;
import config.Setup;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

public class UploadImageRunner extends Setup {
  AdminPage adminPage;

    @BeforeTest
    public void doLogin() throws IOException, ParseException {
       AdminPage adminPage =new   AdminPage(driver);
        JSONParser parser=new JSONParser();
        JSONArray jsonArray= (JSONArray) parser.parse(new FileReader("./src/test/resources/users.json"));

        JSONObject userObj= (JSONObject) jsonArray.get(jsonArray.size()-1);
        String email= (String) userObj.get("email");
        String password= (String) userObj.get("password");



        if(System.getProperty("username")!=null && System.getProperty("password")!=null){
           adminPage.doLogin(System.getProperty("username"),System.getProperty("password"));
        }
        else{
            adminPage.doLogin(email,password);
        }



    }


@Test(priority = 1, description = "Upload a Image to User Profile")
public void uploadImage() throws InterruptedException {



        UploadImage uploadImage = new UploadImage(driver);

        // Click on the profile icon
        uploadImage.btnProfileIcon.click();




// Add an explicit wait to ensure the profile menu is visible
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.visibilityOfAllElements(uploadImage.btnProfileMenuItems));

    // Now, select the appropriate menu item
    uploadImage.btnProfileMenuItems.get(0).click(); // Adjust index as necessary




    List<WebElement> EditList = driver.findElements(By.cssSelector("[type=button]"));
    EditList.get(1).click();


    // Wait for the file upload interface to appear
    wait.until(ExpectedConditions.visibilityOf(uploadImage.chooseFile));


    // Upload the image
    String filePath = Paths.get("src/test/resources/images.jpg").toAbsolutePath().toString();
    uploadImage.chooseFile.sendKeys(filePath);
  //  uploadImage.uploadImage(filePath);

    List<WebElement> buttonList = driver.findElements(By.cssSelector("[type=button]"));
    buttonList.get(1).click();


    // Wait for the upload button to be clickable and then click it
    wait.until(ExpectedConditions.elementToBeClickable(uploadImage.btnUpload)).click();

    testAlert();


    }





    public void testAlert() throws InterruptedException {



        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());

        // Switch to the alert
        Alert alert = driver.switchTo().alert();


        alert.accept();
       Thread.sleep(2000);
    }



    @AfterClass
    public void tearDown() {
        // Close the browser
        driver.quit();
    }

}
