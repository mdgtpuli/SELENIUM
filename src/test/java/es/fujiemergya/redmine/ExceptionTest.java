package es.fujiemergya.redmine;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class ExceptionTest {
    private WebDriver driver;

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    private void setUpChrome(@Optional("chrome") String browser) {
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

    @Test(priority = 1)
    public void notVisibleTest() {
        String URL = "http://the-internet.herokuapp.com/dynamic_loading/1";
        String expectedResult = "Hello World!";

        // Open URL
        driver.get(URL);
        driver.navigate();

        //Click on Start button
        WebElement startButton = driver.findElement(By.xpath("//div[@id='start']/button"));
        startButton.click();

        //Wait for the finish element to appear
        WebElement finishElement = driver.findElement(By.id("finish"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(finishElement));

        //Verify the finish message
        String actualResult = finishElement.getText();
        Assert.assertEquals(actualResult, expectedResult, "The system could not find the expected message. ");

    }

    @Test(priority = 2)
    public void timeoutTest() {
        String URL = "http://the-internet.herokuapp.com/dynamic_loading/1";
        String expectedResult = "Hello World!";

        // Open URL
        driver.get(URL);
        driver.navigate();

        //Click on Start button
        WebElement startButton = driver.findElement(By.xpath("//div[@id='start']/button"));
        startButton.click();

        //Wait for the finish element to appear
        WebElement finishElement = driver.findElement(By.id("finish"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        try {
            wait.until(ExpectedConditions.visibilityOf(finishElement));
        } catch (TimeoutException e) {
            System.out.println("Exception catched: " + e.getMessage());
            sleep(3000);

        }

        //Verify the finish message
        String actualResult = finishElement.getText();
        Assert.assertEquals(actualResult, expectedResult, "The system could not find the expected message. ");

    }

    @Test(priority = 3)
    public void noSuchElementTest() {
        String URL = "http://the-internet.herokuapp.com/dynamic_loading/2";
        String expectedResult = "Hello World!";

        // Open URL
        driver.get(URL);
        driver.navigate();

        //Click on Start button
        WebElement startButton = driver.findElement(By.xpath("//div[@id='start']/button"));
        startButton.click();

        //Wait for the finish element to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement finishElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("finish")));


        //Verify the finish message
        String actualResult = finishElement.getText();
        Assert.assertEquals(actualResult, expectedResult, "The system could not find the expected message. ");

    }

    @Test(priority = 4)
    public void staleElementTest() {
        String URL = "http://the-internet.herokuapp.com/dynamic_controls";

        //Open URL
        driver.get(URL);

        //Find checkbox
        WebElement checkbox = driver.findElement(By.id("checkbox"));

        //Find button
        WebElement removeButton = driver.findElement(By.xpath("//button[contains(text(), 'Remove')]"));

        //Create new wait element
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //Click on remove button
        removeButton.click();


        //Verify that checkbox is not displayed
//        Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOf(checkbox)), "Checkbox is visible, but it" +
//                "should not be. ");

        //Another way to verify that the element is not displayed
        Assert.assertTrue(wait.until(ExpectedConditions.stalenessOf(checkbox)), "Checkbox is visible, but it" +
                "should not be. ");

        //Click add button
        WebElement addButton = driver.findElement(By.xpath("//button[contains(text(), 'Add')]"));
        addButton.click();

        //Verify that the checkbox is back
        checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkbox")));
        Assert.assertTrue(checkbox.isDisplayed(), "Checkbox should be displayed, but it is not. ");


    }

    @Test(priority = 5)
    public void disabledElementTest() {
        String URL = "http://the-internet.herokuapp.com/dynamic_controls";

        //Access URL
        driver.get(URL);

        //Locate enable button
        WebElement enableButton = driver.findElement(By.xpath("//button[contains(text(), 'Enable')]"));
        WebElement textField = driver.findElement(By.xpath("//input[@type='text']"));


        //Click enable button
        enableButton.click();

        //Verify that textfield is enabled
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(textField));

        //Write something in the textField
        textField.sendKeys("Hello, I am Puli. ");
        Assert.assertEquals(textField.getAttribute("value"), "Hello, I am Puli. ");

    }

    @AfterMethod(alwaysRun = true)
    private void closeDriver() {
        driver.quit();
    }

    @BeforeTest(alwaysRun = true)
    private void printMessageBeforeTest() {
        System.out.println("----------TEST STARTED----------");
    }

    @AfterTest(alwaysRun = true)
    private void printMessageAfterTest() {
        System.out.println("----------TEST FINISHED----------");
    }

    private void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
