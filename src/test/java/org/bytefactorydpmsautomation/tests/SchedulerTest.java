package org.bytefactorydpmsautomation.tests;

import org.bytefactorydpmsautomation.PageObjects.ConfigurationMasterRolePage;
import org.bytefactorydpmsautomation.PageObjects.ConfigurationMasterUserAccessPage;
import org.bytefactorydpmsautomation.PageObjects.ConfigurationMasterUserPage;
import org.bytefactorydpmsautomation.PageObjects.SchedulerPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SchedulerTest {
    WebDriver driver;

    SchedulerPage schedulerPage;
    @BeforeTest
    public void setUp(){
        driver = LoginTest.driver;
        schedulerPage = new SchedulerPage(driver);
    }

    @Test(priority = 1)
    public void managerLoginTest(){
        schedulerPage.login("Test Manager User", "123456");
        String expectedUrlAfterLogin = "http://192.168.0.189:4000/dashboard";
        String actualUrlAfterLogin = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrlAfterLogin, actualUrlAfterLogin);
    }

    @Test(priority = 2)
    public void schedulerCreationTest(){

    }
}
