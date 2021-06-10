package utilities;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;



public class utils 
{
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static String sModalPopup = "//div[@class='PopUp']";
	
	
	public static WebDriver Invoke_Browser() throws Exception 
	{
		System.setProperty("webdriver.gecko.driver","geckodriver.exe");
		FirefoxProfile firefoxProfile =  new FirefoxProfile();
		firefoxProfile.setPreference("security.mixed_content.block_active_content", false); // Mixed  content in FF23 not shown - Bug 18313193
		FirefoxOptions options = new FirefoxOptions();
		options.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile); 
		driver = new FirefoxDriver(options);
		Thread.sleep(5000);
		return driver;
	}
	public static void invoke_Application()throws Exception
	{
		driver.get("http://advantageonlineshopping.com/#/");	
		Thread.sleep(3000);
		driver.findElement(By.xpath("//span[text()='dvantage']")).isDisplayed();
		driver.manage().window().maximize();
	}
	public static void elementClick(String sElement) throws Exception
	{
		WebElement EleEdit= FindElement(sElement);
		if (EleEdit!=null)
		{
			EleEdit.click();
			Sync();
		}	
		else
		{
			System.out.println("Element is not present");
		}		
	}
	public static void Sync() throws Exception
	{
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		Thread.sleep(4000);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}
	public static void SignOut() throws Exception
	{
		elementClick("//*[@id='hrefUserIcon']");
		elementClick("//div[@id='loginMiniTitle']/label[text()='Sign out']");
	}
	public static void IncrementProduct() throws Exception
	{	
		Thread.sleep(3000);
		driver.switchTo().defaultContent();
		utils.buttonClick("//a[text()='CONTINUE SHOPPING']");
		driver.switchTo().defaultContent();
		driver.switchTo().defaultContent();
		utils.elementClick("//a[text()='SPECIAL OFFER']");
		utils.Sync();
		utils.buttonClick("see_offer_btn");
		utils.Sync();
		utils.driver.switchTo().defaultContent();
		utils.elementClick("//div[@class='plus' and @increment-value-attr='+']");
		utils.elementClick("save_to_cart");
		driver.switchTo().defaultContent();
		utils.elementClick("menuCart");
		utils.Sync();
		driver.switchTo().defaultContent();
		String sQuantity2=utils.GetText("//td[@class='smollCell quantityMobile']//label[@class='ng-binding']");
		if(sQuantity2.equals("2"))
		{
			System.out.println("Expected quantity is seen");
		}
		else
		{
			System.out.println("Expected quantity is not seen. Actual quantity:"+sQuantity2);
		}
	}
	public static void clickOnUser(String sUserName)throws Exception
	{
		Sync();
		elementClick("//*[@id='hrefUserIcon']");
		driver.switchTo().defaultContent();
		elementClick(sModalPopup+"//a[text()='CREATE NEW ACCOUNT']");
		driver.switchTo().defaultContent();
		
	}
	public static void verify_InvalidUser(String sUserName,String sPwd)throws Exception
	{
		Sync();
		Thread.sleep(3000);
		elementClick("//*[@id='hrefUserIcon']");
		driver.switchTo().defaultContent();
		
		editSet(sModalPopup+"//input[@name='username']",sUserName);
		editSet(sModalPopup+"//input[@name='password']",sPwd);
		
		buttonClick(sModalPopup+"//*[@id='sign_in_btnundefined' and @type='button']");
		
		Thread.sleep(2000);
		
		if(GetText(sModalPopup+"//*[@id='signInResultMessage']").contains("Incorrect user name or password"))
		{
			System.out.println(sUserName+" User does not exist as expected");
		}
		else
		{
			System.out.println(sUserName+" User not supposed to exist");
		}
		elementClick(sModalPopup+"//*[@class='closeBtn loginPopUpCloseBtn']");		
	}
	public static void Click_Create_NewAccount()throws Exception
	{
		Sync();
		Thread.sleep(3000);
		elementClick("//*[@id='hrefUserIcon']");
		driver.switchTo().defaultContent();
		elementClick(sModalPopup+"//a[text()='CREATE NEW ACCOUNT']");
		driver.switchTo().defaultContent();	
	}
	public static void RegisterUser(String sUserName,String sPwd,String sEmailID)throws Exception
	{
		editSet("usernameRegisterPage",sUserName);
		editSet("emailRegisterPage",sEmailID);
		editSet("passwordRegisterPage",sPwd);
		editSet("confirm_passwordRegisterPage",sPwd);
	
		elementClick("//input[@name='i_agree']");
		buttonClick("//*[@id='register_btnundefined' and @type='button']");	
		Sync();
		if(FindElement("//a[text()='ALREADY HAVE AN ACCOUNT?']")!=null)
		{
			elementClick("//a[text()='ALREADY HAVE AN ACCOUNT?']");
			driver.switchTo().defaultContent();
	
			ClearTextBoxValues();
			
			editSet(sModalPopup+"//input[@name='username']",sUserName);
			editSet(sModalPopup+"//input[@name='password']",sPwd);
			buttonClick(sModalPopup+"//*[@id='sign_in_btnundefined' and @type='button']");			
		}
		else
		{
			System.out.println("User registeration is successful");
		}		
			
	}
	public static void Existing_User(String sUserName)throws Exception
	{
		elementClick("//*[@id='hrefUserIcon']");
		driver.switchTo().defaultContent();
		Thread.sleep(2000);
		editSet(sModalPopup+"//input[@name='username']",sUserName);
		editSet(sModalPopup+"//input[@name='password']","TestPwd123");
		buttonClick(sModalPopup+"//*[@id='sign_in_btnundefined' and @type='button']");
		Thread.sleep(2000);
	}
	public static void ClearTextBoxValues() throws Exception
	{
		int iCount= driver.findElements(By.xpath(sModalPopup+"//input[@type='text']")).size();
		List<WebElement> listElements= driver.findElements(By.xpath(sModalPopup+"//input[@type='text']"));
		for(int i=0; i<iCount;i++)
		{
			if(listElements.get(i).isDisplayed())
				listElements.get(i).clear();
		}
		System.out.println("Clear all check boxes count : "+iCount);
		iCount= driver.findElements(By.xpath(sModalPopup+"//input[@type='password']")).size();
		List<WebElement> listElements1= driver.findElements(By.xpath(sModalPopup+"//input[@type='password']"));
		for(int i=0; i<iCount;i++)
		{
			if(listElements1.get(i).isDisplayed())
				listElements1.get(i).clear();
		}
		System.out.println("Clear all password check boxes count : "+iCount);
	}
	public static void Add_Product(String[] sProduct)throws Exception
	{
		driver.switchTo().defaultContent();
		elementClick("//a[text()='SPECIAL OFFER']");
		Sync();
		buttonClick("see_offer_btn");
		Sync();
		driver.switchTo().defaultContent();
		elementClick("save_to_cart");
		elementClick("checkOutPopUp");
		Sync();
		for(int i=0;i<sProduct.length;i++)
		{
			elementClick("search");
			editSet("autoComplete",sProduct[i]);
			driver.switchTo().defaultContent();
			elementClick("//div[@class='searchPopUp']//a[text()='View All']");
			driver.switchTo().defaultContent();
			elementClick("//a[text()='" + sProduct[i] + "']");
			elementClick("save_to_cart");
		}
		driver.switchTo().defaultContent();
		elementClick("menuCart");
		driver.switchTo().defaultContent();
		buttonClick("checkOutButton");
		if(FindElement("next_btn")!=null)
		{
			System.out.println("User is already logged in proceed with order Confirmation");
		}
		else
		{
			UserEntry_OrderPage();
		}
		Thread.sleep(4000);
		buttonClick("next_btn");
		driver.findElement(By.name("safepay_username")).clear();
		driver.findElement(By.name("safepay_password")).clear();
		editSet("safepay_username","Tester");
		editSet("safepay_password","Test123");
		buttonClick("pay_now_btn_SAFEPAY");
		if(GetText("orderNumberLabel").isEmpty()&& GetText("trackingNumberLabel").isEmpty())
			System.out.println("Order not successful");
		else
			System.out.println("Order is successful");
	}
	public static void deleteOfferProduct()throws Exception
	{
		driver.switchTo().defaultContent();
		utils.elementClick("menuCart");
		driver.switchTo().defaultContent();
		utils.buttonClick("//a[text()='CONTINUE SHOPPING']");
		
		driver.switchTo().defaultContent();
		elementClick("//a[text()='SPECIAL OFFER']");
		Sync();
		buttonClick("see_offer_btn");
		Sync();
		driver.switchTo().defaultContent();
		elementClick("save_to_cart");
		Sync();
		elementClick("menuCart");
		Sync();
		driver.switchTo().defaultContent();
		elementClick("//a[text()='REMOVE']");
		driver.switchTo().defaultContent();
		String sActualLabel=GetText("//label[contains(text(),'Your shopping cart is empty') and contains(@class,'roboto-bold')]");
		if(sActualLabel.contains("Your shopping cart is empty"))
		{
			System.out.println(" Cart is Empty as expected");
		}
		else
		{
			System.out.println(" Cart supposed to be Empty");
		}		
	}
	
	public static void UserEntry_OrderPage()throws Exception
	{
		editSet("usernameInOrderPayment","Test123");
		editSet("passwordInOrderPayment","Test123");
		elementClick("login_btnundefined");
	}
	
	public static String GetText(String sValue)throws Exception
	{
		String sLabelText=FindElement(sValue).getText();
		return sLabelText;
	}
	public static void editSet(String sElement,String sValue)throws Exception
	{
		WebElement EleEdit= FindElement(sElement);
		if (EleEdit!=null)
		{
			EleEdit.sendKeys(sValue);
			Sync();
		}	
		else
		{
			System.out.println("Element is not present");
		}
	}
	public static void buttonClick(String sElement)throws Exception
	{
		WebElement EleEdit= FindElement(sElement);
		if (EleEdit!=null)
		{
			EleEdit.click();
			Sync();
		}	
		else
		{
			System.out.println("Element is not present");
		}
	}
	public static WebElement FindElement(String sElement)throws Exception
	{
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		WebElement Element=null;
		try
		{
			Element = driver.findElement(By.id(sElement));
		}
		catch (NoSuchElementException e1) 
		{
			try
			{
				Element = driver.findElement(By.name(sElement));
			}
			catch (Exception e2)
			{
				try
				{
					Element = driver.findElement(By.xpath(sElement));
					
				}
				catch (Exception e3)
				{
					Element = null;
					return Element;
				}
			}
		}
		return Element;
	}
	
	public static void KillProcess(String sProcessName)
	{
		String sTester = System.getProperty("user.name");
		try
		{
			Runtime r = Runtime.getRuntime();
			Process p;
			System.out.println("Killing local process '" + sProcessName + "' user leve");
			p = r.exec("taskkill /FI \"USERNAME eq " + sTester + "\" /IM " + sProcessName + " /F");
			p.waitFor();
		} catch (Exception e) 
		{
			e.printStackTrace();
		} finally {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
