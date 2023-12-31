package org.bytefactorydpmsautomation.tests;

import org.bytefactorydpmsautomation.TestComponents.BaseTest;
import org.bytefactorydpmsautomation.data.DataProvider;
import org.openqa.selenium.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;

public class ResolutionWorkOrderTest {
    DataProvider dataProvider = new DataProvider();
    WebDriver driver;
    BaseTest baseTest;
    @BeforeTest
    public void setUp() throws IOException {
        driver = LoginTest.driver;
    }
    @Test(priority = 1, dataProvider = "ResolutionWorkOrderData", dataProviderClass = DataProvider.class)
    public void testResolutionWorkOrder(String assignedTo, String priority, String dueDate, String rca, String ca, String pa)  {

    }
}
