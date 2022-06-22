package loginTestCases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class loginClass {

	WebDriver driver;
	String[][] data= null;
		
	
	
	@DataProvider(name="loginData")
	public String[][] loginDataProvider() throws BiffException, IOException {
		//return test data
		data=getExcelData();
		return data;
	}
	
	public String[][] getExcelData() throws BiffException, IOException {
		
		FileInputStream excel=new FileInputStream("D:\\CTS\\Book1.xls");
		
		Workbook workbook=Workbook.getWorkbook(excel);
		
		Sheet sheet=workbook.getSheet(0); //ie. it gets the sheet 1
		
		int rowCount=sheet.getRows();
		int columnCount=sheet.getColumns();
		
		String testData[][]=new String[rowCount-1][columnCount];
		
		for(int i=1; i<rowCount; i++) {
			for(int j=0; j<columnCount; j++) {
				
				testData[i-1][0]=sheet.getCell(j, i).getContents();
			}
		}
		return testData;
		
	}
	
	@BeforeTest
	public void OpenChrome() {
		System.setProperty("webdriver.chrome.driver", "D:\\CTS\\chromedriver.exe");
		
		driver = new ChromeDriver();
	}
	
	@AfterTest
	public void closeBrowser() {
		driver.quit();
	}
	
	
	@Test(dataProvider = "loginData")
	public void loginWithBothCorrect(String usr, String pwd) {
			
			
			driver.get("https://opensource-demo.orangehrmlive.com/index.php/");
			
			WebElement username=driver.findElement(By.id("txtUsername"));
			username.sendKeys(usr);
			
			WebElement password=driver.findElement(By.id("txtPassword"));
			password.sendKeys(pwd);
			
			WebElement loginButton=driver.findElement(By.id("btnLogin"));
			loginButton.click();
			
			
		}
	
}
