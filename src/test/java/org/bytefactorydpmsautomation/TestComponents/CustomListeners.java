package org.bytefactorydpmsautomation.TestComponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.bytefactorydpmsautomation.resources.ExtentReportNG;
import org.bytefactorydpmsautomation.tests.LoginTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;

public class CustomListeners extends BaseTest implements ITestListener {
    ExtentTest test;
    WebDriver driver;
    ExtentReports extent = ExtentReportNG.getReportObject();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

    @Override
    public void onTestStart(ITestResult result) {
        LoginTest loginTest = new LoginTest();
        driver = LoginTest.driver;
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if(test !=null){
            test.log(Status.PASS, "Testcase Passed");
        }

    }

    @Override
    public void onTestFailure(ITestResult result) {
//        if(test !=null){
//            test.fail(result.getThrowable());
//            TakesScreenshot ts = (TakesScreenshot)driver;
//            File source = ts.getScreenshotAs(OutputType.FILE);
//            File file = new File(System.getProperty("user.dir")+"//reports//"+result.getMethod().getMethodName()+".png");
//            try {
//                FileUtils.copyFile(source, file);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            test.addScreenCaptureFromPath(String.valueOf(file));
//        }
        extentTest.get().fail(result.getThrowable());//
        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
                    .get(result.getInstance());
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String filePath = null;
        try {
            filePath = getScreenshot(result.getMethod().getMethodName(),driver);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        } else {
            System.out.println("ExtentReports object is not initialized.");
        }
    }
}
