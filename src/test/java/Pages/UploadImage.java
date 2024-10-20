package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class UploadImage {

    @FindBy(css = "[data-testid=AccountCircleIcon]")
    public WebElement btnProfileIcon;

    @FindBy(css = "[role=menuitem]")
    public List<WebElement> btnProfileMenuItems;



    @FindBy(css ="[type = file]")
    public WebElement chooseFile;



    @FindBy(css ="[type = button]")
   public WebElement btnUpload;


    public UploadImage (WebDriver driver){
        PageFactory.initElements(driver,this);
    }


    public void uploadImage(String filePath){
        btnProfileIcon.click();
        //chooseFile.click();


        chooseFile.sendKeys(filePath);
        btnUpload.click();

    }

}
