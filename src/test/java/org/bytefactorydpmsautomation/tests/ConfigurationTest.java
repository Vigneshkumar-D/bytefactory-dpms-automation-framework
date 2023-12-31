package org.bytefactorydpmsautomation.tests;

import org.bytefactorydpmsautomation.PageObjects.ConfigurationMasterRolePage;
import org.bytefactorydpmsautomation.PageObjects.ConfigurationMasterUserAccessPage;
import org.bytefactorydpmsautomation.PageObjects.ConfigurationMasterUserPage;
import org.bytefactorydpmsautomation.TestComponents.CustomListeners;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(CustomListeners.class)
public class ConfigurationTest{
    WebDriver driver;
    ConfigurationMasterRolePage configurationMasterRolePage;
    ConfigurationMasterUserPage configurationMasterUserPage;
    ConfigurationMasterUserAccessPage configurationMasterUserAccessPage;

//    String userName, String role, String email, String mobileNum, String password

    @BeforeTest
    public void setUp(){
        driver = LoginTest.driver;
        configurationMasterRolePage = new ConfigurationMasterRolePage(driver);
        configurationMasterUserPage = new ConfigurationMasterUserPage(driver);
        configurationMasterUserAccessPage = new ConfigurationMasterUserAccessPage(driver);
    }

    @Test(priority = 1)
    public void superUserRoleCreationTest() throws InterruptedException {
        Thread.sleep(1000);
        configurationMasterRolePage.selectConfiguration();
        configurationMasterRolePage.createRole("Test SuperUser Role");
        boolean isRoleCreated = configurationMasterRolePage.validateRole("Test SuperUser Role");
        Assert.assertTrue(isRoleCreated);
    }

    @Test(priority = 2)
    public void searchRoleTest()throws InterruptedException {
        Thread.sleep(1000);
        configurationMasterRolePage.searchRole("Test SuperUser Role");
        boolean isRoleFound = configurationMasterRolePage.validateRole("Test SuperUser Role");
        Assert.assertTrue(isRoleFound);
    }

    @Test(priority = 3)
    public void superUserCreationTest(){
        configurationMasterUserPage.createUser("Test User(S10)","Test SuperUser Role", "avsf@example.com", "9876543210", "123456");
        boolean isUserAdded = configurationMasterUserPage.validateUser("Test User(S10)");
        Assert.assertTrue(isUserAdded);
    }

    @Test(priority = 4)
    public void superUserAccessTest() throws InterruptedException {
        String successMessage = configurationMasterUserAccessPage.provideUserAccess("Test SuperUser Role");
        Assert.assertEquals("UserAccess provided successfully", successMessage);
    }

    @Test(priority = 5)
    public void logOutAdminTest(){
        configurationMasterRolePage.logOut("Administrator");
        String expectedUrlAfterLogin = "http://192.168.0.189:4000/login";
        String actualUrlAfterLogin = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrlAfterLogin, actualUrlAfterLogin);
    }

    @Test(priority = 6)
    public void loginSuperUserTest(){
        configurationMasterRolePage.login("Test SuperUser(S10)", "123456");
        String expectedUrlAfterLogin = "http://192.168.0.189:4000/dashboard";
        String actualUrlAfterLogin = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrlAfterLogin, actualUrlAfterLogin);
    }

    @Test(priority = 7)
    public void managerRoleCreationTest(){
        configurationMasterRolePage.selectConfiguration();
        configurationMasterRolePage.createRole("Test Manager Role");
        boolean isRoleCreated = configurationMasterRolePage.validateRole("Test Manager Role");
        Assert.assertTrue(isRoleCreated);
    }

    @Test(priority = 8)
    public void technicianRoleCreationTest(){
        configurationMasterRolePage.selectConfiguration();
        configurationMasterRolePage.createRole("Test Technician Role");
        boolean isRoleCreated = configurationMasterRolePage.validateRole("Test Technician Role");
        Assert.assertTrue(isRoleCreated);
    }

    @Test(priority = 9)
    public void managerUserCreationTest(){
        configurationMasterUserPage.createUser("Test User(M10)","Test Manager Role", "avsf@example.com", "9876543210", "123456");
        boolean isUserAdded = configurationMasterUserPage.validateUser("Test User(M10)");
        Assert.assertTrue(isUserAdded);
    }
    @Test(priority = 10)
    public void technicianUserCreationTest(){
        configurationMasterUserPage.createUser("Test User(T10)","Test Technician Role", "avsf@example.com", "9876543210", "123456");
        boolean isUserAdded = configurationMasterUserPage.validateUser("Test User(T10)");
        Assert.assertTrue(isUserAdded);
    }

    @Test(priority = 11)
    public void managerUserAccessTest() throws InterruptedException {
        String successMessage = configurationMasterUserAccessPage.provideUserAccess("Test Manager Role");
        Assert.assertEquals("UserAccess provided successfully", successMessage);
    }

    @Test(priority = 12)
    public void technicianUserAccessTest() throws InterruptedException {
        String successMessage = configurationMasterUserAccessPage.provideUserAccess("Test Technician Role");
        Assert.assertEquals("UserAccess provided successfully", successMessage);
    }

    @Test(priority = 13)
    public void superUserLogout(){
        configurationMasterRolePage.logOut("Test SuperUser Role");
        String expectedUrlAfterLogin = "http://192.168.0.189:4000/login";
        String actualUrlAfterLogin = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrlAfterLogin, actualUrlAfterLogin);
    }
}
