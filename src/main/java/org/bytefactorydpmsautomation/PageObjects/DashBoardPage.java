package org.bytefactorydpmsautomation.PageObjects;

import org.bytefactorydpmsautomation.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DashBoardPage extends AbstractComponent {
    WebDriver driver;
    int ticketCount = 0;
    CheckListExecutionPage checkListExecutionPage;
    public DashBoardPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
        checkListExecutionPage = new CheckListExecutionPage(driver);
    }

    public List<Integer> checkListExecutionScheduledStatus() throws InterruptedException {
        String scheduledXpath = "(//*[contains(@class, 'apexcharts-data-labels') and contains(@id, 'Svg')])[8]";
        return checkListExecutionStatus(scheduledXpath);
    }

    public List<Integer> checkListExecutionInProgressStatus() throws InterruptedException {
        String inProgressXpath = "(//*[contains(@class, 'apexcharts-data-labels') and contains(@id, 'Svg')])[9]";
        return checkListExecutionStatus(inProgressXpath);
    }

    public List<Integer> checkListExecutionClosedStatus() throws InterruptedException {
        String inProgressXpath = "(//*[contains(@class, 'apexcharts-data-labels') and contains(@id, 'Svg')])[10]";
        return checkListExecutionStatus(inProgressXpath);
    }

    @FindBy(xpath = "(//*[contains(@class, 'apexcharts-data-labels') and contains(@id, 'Svg')])[2]")
    WebElement ticketCountEle;
    @FindBy(xpath = "//ul[@role='menu']//li[1]")
    WebElement dashboardEle;
    public List<Integer> checkUpdatedTicketCount() {
        waitForWebElementToAppear(dashboardEle);
        dashboardEle.click();
        waitForWebElementToAppear(ticketCountEle);
        String ticketCountText = ticketCountEle.getText();
        int updatedTicketCount = Integer.parseInt(ticketCountText);
        List<Integer> ticketList = new ArrayList<>();
        ticketList.add(ticketCount);
        ticketList.add(updatedTicketCount);
        return ticketList;
    }

    public int actualTicketCount() {
        waitForWebElementToAppear(dashboardEle);
        dashboardEle.click();
        waitForWebElementToAppear(ticketCountEle);
        String ticketCountText = ticketCountEle.getText();
        ticketCount = Integer.parseInt(ticketCountText);
        return ticketCount;
    }

    public boolean resolutionWorkOrderStatus() throws InterruptedException {
        boolean hasNextPage = true;
        boolean foundText = false;
        while (hasNextPage) {
            List<WebElement> tableData =  driver.findElements(By.xpath("//tbody//tr"));
            for (int i = 0; i < tableData.size(); i++) {
                WebElement webElement = tableData.get(i);
                WebElement execNumElement;
                try {
                    execNumElement = webElement.findElement(By.xpath(".//td[2]"));
                } catch (StaleElementReferenceException e) {
                    System.out.println("Stale element reference encountered");
                    Thread.sleep(3000);
                    tableData = driver.findElements(By.xpath("//tbody//tr"));
                    webElement = tableData.get(i);
                    execNumElement = webElement.findElement(By.xpath(".//td[2]"));
                }
                boolean isValidExecNum = execNumElement.getText().equalsIgnoreCase(checkListExecutionPage.getExecNum());
                if (isValidExecNum) {
                    foundText = true;
                    break;
                }
            }
            hasNextPage = false;
        }
        return foundText;
    }

    @FindBy(xpath = "//span[normalize-space()='Opened']")
    WebElement openedButtonEle;
    public boolean dashBoardRWOOpenedStatus() throws InterruptedException {
        waitForWebElementToAppear(dashboardEle);
        dashboardEle.click();
        Thread.sleep(1000);
        waitForWebElementToAppear(openedButtonEle);
        openedButtonEle.click();
        return resolutionWorkOrderStatus();
    }
    @FindBy(xpath = "//span[normalize-space()='Assigned']")
    WebElement assignedButtonEle;
    public boolean dashBoardRWOAssignedStatus() throws InterruptedException {
        waitForWebElementToAppear(dashboardEle);
        dashboardEle.click();
        Thread.sleep(1000);
        waitForWebElementToAppear(assignedButtonEle);
        assignedButtonEle.click();
        return resolutionWorkOrderStatus();
    }

    @FindBy(xpath = "//span[normalize-space()='Resolved']")
    WebElement resolvedButtonEle;
    public boolean dashBoardRWOResolvedStatus() throws InterruptedException {
        waitForWebElementToAppear(dashboardEle);
        dashboardEle.click();
        Thread.sleep(1000);
        waitForWebElementToAppear(resolvedButtonEle);
        resolvedButtonEle.click();
        return resolutionWorkOrderStatus();
    }

    @FindBy(xpath = "//span[normalize-space()='Rejected']")
    WebElement rejectedButtonEle;
    public boolean dashBoardRWORejectedStatus() throws InterruptedException {
        waitForWebElementToAppear(dashboardEle);
        dashboardEle.click();
        Thread.sleep(1000);
        waitForWebElementToAppear(rejectedButtonEle);
        rejectedButtonEle.click();
        return resolutionWorkOrderStatus();
    }

    @FindBy(xpath = "//span[normalize-space()='Completed']")
    WebElement completedButtonEle;
    public boolean dashBoardRWOCompletedStatus() throws InterruptedException {
        waitForWebElementToAppear(dashboardEle);
        dashboardEle.click();
        Thread.sleep(1000);
        waitForWebElementToAppear(completedButtonEle);
        completedButtonEle.click();
        return resolutionWorkOrderStatus();
    }

}
