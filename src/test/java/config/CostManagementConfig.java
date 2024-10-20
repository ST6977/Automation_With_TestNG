package config;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CostManagementConfig extends Setup{

    WebDriver driver;
@Test
    @DataProvider(name = "CSVDataSet")
    public Object[][] readCSV() throws Exception {
        String filePath = "./src/test/resources/expenditures.csv";
        List<Object[]>  data= new ArrayList<>();
        CSVParser csvParser = new CSVParser(new FileReader(filePath), CSVFormat.DEFAULT.withFirstRecordAsHeader() );


  //  int count = 0;
    for(CSVRecord csvRecord: csvParser){
       // if (count >= 5) break;
      String itemName=csvRecord.get("Item Name");
       String amount=csvRecord.get("Amount");
        String quantity =csvRecord.get("Quantity");
      String purchaseDate=csvRecord.get("Purchase Date");
   String month = csvRecord.get("Month");
        String remarks = csvRecord.get("Remarks");
        data.add(new Object[]{itemName, amount, quantity,purchaseDate,month ,remarks});
       // count++;
    }
    return data.toArray(new Object[0][]);
    }







}
