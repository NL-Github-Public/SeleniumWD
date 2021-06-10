package script;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;

import utilities.utils;

import junit.framework.TestCase;

public class advantage_WD extends TestCase {
	public WebDriver driver;

	public void setUp() throws Exception {

		utils.KillProcess("firefox.exe");
		driver = utils.Invoke_Browser();
	}

	public void tearDown() throws Exception {
		try {
			driver.quit();
		} catch (Exception e) {
		}
	}

	public void testadvantage_WD() throws Exception {

		utils.invoke_Application();
		
		// Search using Invalid User
		utils.verify_InvalidUser("Test@User@123", "Testuser123");

		utils.Click_Create_NewAccount();

		String datetime = "";
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
		datetime = ft.format(dNow);
		String sRandomValue = datetime.substring(datetime.length() - 4);

		// Create new user with unique value
		String sUser = "TestUser" + sRandomValue;
		String sExistingUser = sUser; // For future reference

		utils.RegisterUser(sUser, "TestPwd123", "Test@gmail.com");
		
		// Add Multiple products to Cart ..and order confirmation
		String[] sMultipProducts = { "HP H2310 In-ear Headset", "HP Roar Mini Wireless Speaker","Logitech G502 Proteus Core" };
		utils.Add_Product(sMultipProducts);

		// Add and Delete offer product
		utils.deleteOfferProduct();

		// Logout and relogin with existing User
		utils.SignOut();
		utils.Existing_User(sExistingUser);
		utils.IncrementProduct();
		utils.SignOut();
	}

}
