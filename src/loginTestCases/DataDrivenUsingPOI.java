package loginTestCases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class DataDrivenUsingPOI {

	static List<String> userNameList=new ArrayList<String>();
	static List<String> passwordList=new ArrayList<String>();
	public void readExcel() throws IOException {
		
		FileInputStream excel=new FileInputStream("D:\\CTS\\Book2.xlsx");
		
		Workbook workbook=new XSSFWorkbook(excel);
		
		Sheet sheet=workbook.getSheetAt(0);
		
		Iterator<Row> rowIterator=sheet.iterator();
		
		while(rowIterator.hasNext()) {
			Row rowValue = rowIterator.next();
			
			Iterator<Cell> columnIterator= rowValue.iterator();
			int i=2;
			while(columnIterator.hasNext()) {
				if(i%2==0) {
					userNameList.add(columnIterator.next().getStringCellValue());
				}
				else {
					passwordList.add(columnIterator.next().getStringCellValue());
				}
			i++;	
			}
		}
	}
	
	public void login(String usr,String pwd) {
		System.setProperty("webdriver.chrome.driver", "D:\\CTS\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://opensource-demo.orangehrmlive.com/index.php/");
		
		WebElement username=driver.findElement(By.id("txtUsername"));
		username.sendKeys(usr);
		
		WebElement password=driver.findElement(By.id("txtPassword"));
		password.sendKeys(pwd);
		
		WebElement loginButton=driver.findElement(By.id("btnLogin"));
		loginButton.click();
		
		driver.quit();
	}
	
	public void executeTest() {
		for(int i=0;i<userNameList.size();i++) {
			login(userNameList.get(i),passwordList.get(i));
		}
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		DataDrivenUsingPOI usingPOI = new DataDrivenUsingPOI();
		usingPOI.readExcel();
		
		System.out.println("Usernames"+userNameList);
		System.out.println("passwords"+passwordList);
		usingPOI.executeTest();
	}

}
