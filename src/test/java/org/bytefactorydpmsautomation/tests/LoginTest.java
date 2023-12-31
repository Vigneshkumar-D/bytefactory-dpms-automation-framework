package org.bytefactorydpmsautomation.tests;

import org.bytefactorydpmsautomation.PageObjects.LandingPage;
import org.bytefactorydpmsautomation.TestComponents.BaseTest;
import org.bytefactorydpmsautomation.TestComponents.CustomListeners;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

@Listeners(CustomListeners.class)
public class LoginTest {
    public static WebDriver driver;
    BaseTest baseTest;
    @BeforeTest
    public void setUp() throws IOException {
        baseTest = new BaseTest();
        driver = baseTest.initializeDriver();
    }

    @Test(priority = 1)
    public void loginTest() throws InterruptedException {
        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        landingPage.loginApplication("Administrator", "123456");
        Thread.sleep(1);
        landingPage.loginMainApplication("Administrator", "123456");
        WebDriverWait wait = new WebDriverWait(baseTest.driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe("http://192.168.0.189:4000/dashboard"));
        String expectedUrlAfterLogin = "http://192.168.0.189:4000/dashboard";
        String actualUrlAfterLogin = baseTest.driver.getCurrentUrl();
        Assert.assertEquals(actualUrlAfterLogin, expectedUrlAfterLogin);
    }
}
