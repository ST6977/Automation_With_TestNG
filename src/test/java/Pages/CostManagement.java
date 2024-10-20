package Pages;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class CostManagement {

    private WebDriver driver;
    @FindBy(className = "add-cost-button")
    public WebElement btnAddCost;
    @FindBy(id = "itemName")
    WebElement txtItemName;
    @FindBy(id="amount")
    WebElement txtAmount;

    @FindBy(css="[type = button]")
    WebElement btnQuantity;
    //List<WebElement>


    @FindBy(id="purchaseDate")
    WebElement txtPurchase;





    @FindBy(id="month")
    WebElement txtMonth;


    @FindBy(id="remarks")
    WebElement txtRemarks;



    @FindBy(className = "submit-button")
    public WebElement btnSubmit;


    public CostManagement(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void addExpenditure(String itemName, String amount, String quantity, String purchaseDate, String month, String remarks) {
        btnAddCost.click(); // Click the button to add cost

        txtItemName.sendKeys(itemName);

        txtAmount.sendKeys(amount);

        btnQuantity.sendKeys(quantity);
       // btnQuantity.click();
       // List<WebElement> quantityButtons = driver.findElements(By.cssSelector("your_quantity_button_selector"));

        //quantityButtons.get(2).click();
       // quantityButtons.get(2).sendKeys("your_quantity_value");

        // Set current date in the purchase date textbox
       setCurrentDateInDatepicker();
       // txtPurchase.sendKeys(purchaseDate);

        // Add waits for month and remarks
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(txtMonth)).sendKeys(month);
        wait.until(ExpectedConditions.visibilityOf(txtRemarks)).sendKeys(remarks);





        // Wait and click the submit button
        wait.until(ExpectedConditions.elementToBeClickable(btnSubmit)).click();


    }


    private void setCurrentDateInDatepicker() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy"); // Correct format
        String formattedDate = currentDate.format(formatter);

        // Use JavaScript to set the value of the disabled input
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + "10-17-2024" + "');", txtPurchase);
    }


}
