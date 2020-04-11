package es.fujiemergya.redmine;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogarDia {

    @Test
    public void logarDia(){
        String usernameSt = "mdolores.garcia";
        String passwordSt = "26086420";
        String URL = "https://redmine-sf.fujiemergya.es/my/page";
        String petition = "163994";
        String hours = "8.5";
        String activity = "";

        System.out.println("Test started: Access Mi página");

        //Create driver
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        //Open URL
        driver.get(URL);
        driver.navigate();
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

        System.out.println("Login completed");

        //Click on "Tiempo dedicado"
        WebElement tiempoDedicado = driver.findElement(By.xpath("//a[@class='icon icon-add']"));
        tiempoDedicado.click();

        //Select proyect
        WebElement proyectArray = driver.findElement(By.id("time_entry_project_id"));
        proyectArray.findElement(By.xpath("//option[@value='878']")).click();

        //Introduce petiton number
        WebElement petitionNumber = driver.findElement(By.id("time_entry_issue_id"));
        petitionNumber.sendKeys(petition);


        //Introduce number of hours
        WebElement hoursInbox = driver.findElement(By.id("time_entry_hours"));
        hoursInbox.sendKeys(hours);

        //Select activity
        WebElement activityArray = driver.findElement(By.id("time_entry_activity_id"));
        activityArray.click();
        activityArray.findElement(By.xpath("//option[@value='20']")).click();

        //Click submit
        WebElement submitButton = driver.findElement(By.xpath("//input[@value='Crear']"));
        submitButton.click();

        //Verifications
        String expectedSuccessMessage ="Creación correcta.";
        WebElement successMessage = driver.findElement(By.id("flash_notice"));
        String actualSuccessMessage = successMessage.getText();
        Assert.assertEquals(actualSuccessMessage,expectedSuccessMessage, "Successful action message was not found. " );


        driver.quit();
    }
}
