package es.fujiemergya.redmine;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest {

    @Test
    public void loginTest() {
        String usernameSt = "mdolores.garcia";
        String passwordSt = "26086420";
        String URL = "https://redmine-sf.fujiemergya.es/login?back_url=http%3A%2F%2Fredmine-sf.fujiemergya.es%2F";

        System.out.println("Test started: Login Test");

        //Create driver
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        //Open test page
        driver.get(URL);
        driver.navigate(); 
        System.out.println("Browser page is opened.");

        System.out.println("Page open");

        //Maximize driver's window
        driver.manage().window().maximize();

        //enter username
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys(usernameSt);


        //enter password
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(passwordSt);

        //Check autologin box
        WebElement autologinButton = driver.findElement(By.id("autologin"));
        autologinButton.click();

        //Click login button
        WebElement loginButton = driver.findElement(By.cssSelector("input[name='login']"));
        loginButton.click();

        System.out.println("Start verifications");

        //Verifications
        String expectedURL= "https://redmine-sf.fujiemergya.es/";
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL, expectedURL, "Incorrect URL found after logging in. ");


       WebElement logoutButton = driver.findElement(By.xpath("//a[@class='logout']"));
       Assert.assertTrue(logoutButton.isDisplayed(), "Content bar not found after login. ");

       String expectedLoginMessage = "Conectado como " + usernameSt;
       WebElement loginMessage = driver.findElement(By.id("loggedas"));
       String actualLoginMessage = loginMessage.getText();
       Assert.assertEquals(actualLoginMessage, expectedLoginMessage, "Expected login message not found after login. ");

        //Close browser
        driver.quit();

        System.out.println("End of the test");
    }


}
