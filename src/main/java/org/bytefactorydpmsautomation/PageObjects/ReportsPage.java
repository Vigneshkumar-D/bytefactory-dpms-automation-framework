package org.bytefactorydpmsautomation.PageObjects;

import org.bytefactorydpmsautomation.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class ReportsPage extends AbstractComponent {
    WebDriver driver;
    public ReportsPage(WebDriver driver){
        super(driver);
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//ul[@role='menu']//li[6]")
    WebElement reportsEle;
    public boolean reportsStatus(String status, String woNum){
        System.out.println(woNum);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForWebElementToAppear(reportsEle);
        Actions actions = new Actions(driver);
        actions.moveToElement(reportsEle).click().perform();
        reportsEle.click();
        boolean hasNextPage = true;
        boolean foundText = false;

        while (hasNextPage) {
            List<WebElement> tableData =  driver.findElements(By.xpath("//tbody//tr"));
            try {
                for (int i = 0; i < tableData.size(); i++) {
                    WebElement rowData = tableData.get(i);
                    WebElement woNumElement;
                    WebElement statusElement;

                    try {
                        woNumElement = rowData.findElement(By.xpath(".//td[2]"));
                        statusElement = rowData.findElement(By.xpath(".//td[6]"));
                    } catch (StaleElementReferenceException e) {
                        System.out.println("Stale element reference encountered");
                        tableData = driver.findElements(By.xpath("//tbody//tr"));
                        rowData = tableData.get(i);
                        try {
                            Thread.sleep(3000);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        woNumElement = rowData.findElement(By.xpath(".//td[2]"));
                        statusElement = rowData.findElement(By.xpath(".//td[6]"));
                    }
                    boolean isValidWoNum = woNumElement.getText().equalsIgnoreCase(woNum);
                    boolean isValidStatus = statusElement.getText().equalsIgnoreCase(status);
//                    System.out.println("Element: "+woNumElement.getText() + " " + statusElement.getText());
                    if (isValidWoNum && isValidStatus) {
                        foundText = true;
                        hasNextPage = false;
                        break;
                    }
                }
                hasNextPage = false;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return  foundText;
    }
    public boolean checkReportOpenedStatus(String status, String woNum) throws InterruptedException {
        Thread.sleep(1000);
        return reportsStatus(status, woNum);
    }
    public boolean checkReportAssignedStatus(String status, String woNum) throws InterruptedException {
        Thread.sleep(1000);
        return reportsStatus(status, woNum);
    }
    public boolean checkResolvedAssignedStatus(String status, String woNum) throws InterruptedException {
        Thread.sleep(1000);
        return reportsStatus(status, woNum);
    }
    public boolean checkRejectedAssignedStatus(String status, String woNum) throws InterruptedException {
        Thread.sleep(1000);
        return reportsStatus(status, woNum);
    }
    public boolean checkCompletedAssignedStatus(String status, String woNum) throws InterruptedException {
        Thread.sleep(1000);
        return reportsStatus(status, woNum);
    }
}
