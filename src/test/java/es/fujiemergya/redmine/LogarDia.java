package es.fujiemergya.redmine;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LogarDia {

    private WebDriver driver;

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    private void setUpChrome(@Optional String browser) {
        //Create driver
        switch (browser.toLowerCase()) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "src\\main\\resources\\geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            default:
                System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
                driver = new ChromeDriver();
        }

        //Maximise driver
        driver.manage().window().maximize();
    }

    @Parameters({"usernameSt", "passwordSt", "petition", "hours", "fechaSt"})
    @Test
    public void logarDia(String usernameSt, String passwordSt, String petition, String hours, int fechaSt) {
        String URL = "https://redmine-sf.fujiemergya.es/my/page";

        System.out.println("Test started: Logar horas del día" + fechaSt);

        //Open URL
        driver.get(URL);
        driver.navigate();


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

        //fecha
        WebElement fechaInbox = driver.findElement(By.id("time_entry_spent_on"));
        fechaInbox.sendKeys(calculateDate(fechaSt));

        //Introduce number of hours
        WebElement hoursInbox = driver.findElement(By.id("time_entry_hours"));
        hoursInbox.sendKeys(hours);

        //Select activity
        WebElement activityArray = driver.findElement(By.id("time_entry_activity_id"));
        activityArray.click();
        activityArray.findElement(By.xpath("//option[@value='20']")).click();

//        //Click submit
//        WebElement submitButton = driver.findElement(By.xpath("//input[@value='Crear']"));
//        submitButton.click();
//
//        //Verifications
//        String expectedSuccessMessage ="Creación correcta.";
//        WebElement successMessage = driver.findElement(By.id("flash_notice"));
//        String actualSuccessMessage = successMessage.getText();
//        Assert.assertEquals(actualSuccessMessage,expectedSuccessMessage, "Successful action message was not found. " );
//
//

    }

    private String calculateDate(int dia) {
        //Format today's date in the required format
        String pattern = "EEE, dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);

        //Extract the weekday, the day of the month and month and year information separately
        String weekDay = todayAsString.substring(0, 3);
        String day = todayAsString.substring(5, 7);
        String monthAndYear = todayAsString.substring(7);
        Integer dayInt = Integer.parseInt(day);

        String actualDay = "";

        //Taking into account the weekday and the variable dia sent, we add or extract days to the variable
        // day in order to print the required date
        for (int i = 0; i <= 6; i++) {
            if (weekDay.equalsIgnoreCase("lun")) {
                actualDay = Integer.toString(dayInt + dia);

            }
            if (weekDay.equalsIgnoreCase("mar")) {
                switch (dia) {
                    case 0:
                        actualDay = Integer.toString(dayInt - 1);
                        break;
                    case 1:
                        actualDay = Integer.toString(dayInt);
                        break;
                    case 2:
                        actualDay = Integer.toString(dayInt + 1);
                        break;
                    case 3:
                        actualDay = Integer.toString(dayInt + 2);
                        break;
                    case 4:
                        actualDay = Integer.toString(dayInt + 3);
                        break;
                }
            }
            if (weekDay.equalsIgnoreCase("mie")) {
                switch (dia) {
                    case 0:
                        actualDay = Integer.toString(dayInt - 2);
                        break;
                    case 1:
                        actualDay = Integer.toString(dayInt - 1);
                        break;
                    case 2:
                        actualDay = Integer.toString(dayInt);
                        break;
                    case 3:
                        actualDay = Integer.toString(dayInt + 1);
                        break;
                    case 4:
                        actualDay = Integer.toString(dayInt + 2);
                        break;
                }
            }
            if (weekDay.equalsIgnoreCase("jue")) {
                switch (dia) {
                    case 0:
                        actualDay = Integer.toString(dayInt - 3);
                        break;
                    case 1:
                        actualDay = Integer.toString(dayInt - 2);
                        break;
                    case 2:
                        actualDay = Integer.toString(dayInt - 1);
                        break;
                    case 3:
                        actualDay = Integer.toString(dayInt);
                        break;
                    case 4:
                        actualDay = Integer.toString(dayInt + 1);
                        break;
                }
            }
            if (weekDay.equalsIgnoreCase("vie")) {
                switch (dia) {
                    case 0:
                        actualDay = Integer.toString(dayInt - 4);
                        break;
                    case 1:
                        actualDay = Integer.toString(dayInt - 3);
                        break;
                    case 2:
                        actualDay = Integer.toString(dayInt - 2);
                        break;
                    case 3:
                        actualDay = Integer.toString(dayInt - 1);
                        break;
                    case 4:
                        actualDay = Integer.toString(dayInt);
                        break;
                }
            }
            if (weekDay.equalsIgnoreCase("sáb")) {
                switch (dia) {
                    case 0:
                        actualDay = Integer.toString(dayInt - 5);
                        break;
                    case 1:
                        actualDay = Integer.toString(dayInt - 4);
                        break;
                    case 2:
                        actualDay = Integer.toString(dayInt - 3);
                        break;
                    case 3:
                        actualDay = Integer.toString(dayInt - 2);
                        break;
                    case 4:
                        actualDay = Integer.toString(dayInt - 1);
                        break;
                }
            }
            if (weekDay.equalsIgnoreCase("dom")) {
                switch (dia) {
                    case 0:
                        actualDay = Integer.toString(dayInt - 6);
                        break;
                    case 1:
                        actualDay = Integer.toString(dayInt - 5);
                        break;
                    case 2:
                        actualDay = Integer.toString(dayInt - 4);
                        break;
                    case 3:
                        actualDay = Integer.toString(dayInt - 3);
                        break;
                    case 4:
                        actualDay = Integer.toString(dayInt - 2);
                        break;
                }
            }

        }
        //Return the resulting date to be introduced in the form
        return actualDay + monthAndYear;

    }

    @AfterMethod(alwaysRun = true)
    private void closeDriver() {
        //        driver.quit();
    }
}



