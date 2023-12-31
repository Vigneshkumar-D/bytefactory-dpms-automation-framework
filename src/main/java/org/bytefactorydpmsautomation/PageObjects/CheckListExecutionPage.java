package org.bytefactorydpmsautomation.PageObjects;

import lombok.Getter;
import org.bytefactorydpmsautomation.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CheckListExecutionPage extends AbstractComponent {
    WebDriver driver;
    @Getter
    String execNum;
    int ticketCount = 0;
    DashBoardPage dashBoardPage;

    public CheckListExecutionPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
        dashBoardPage = new DashBoardPage(driver);
    }

    @FindBy(xpath = "//ul[@role='menu']//li[1]")
    WebElement dashboardEle;
    @FindBy(xpath = "//ul[@role='menu']//li[4]")
    WebElement schedulerEle;
    @FindBy(xpath = "//tbody//tr")
    List<WebElement> tableDataEle;

    public boolean validateChecklistExecution(String assertName, String checkList, String frequency,
                                              String assignedToUser, String startDate, String endDate,
                                              String startTime, String endTime, String description) {
        waitForWebElementToAppear(schedulerEle);
        schedulerEle.click();
        return this.checkTableData(assignedToUser, assertName, description);
    }

    @FindBy(xpath = ".//td[5]")
    WebElement descriptionEle;
    @FindBy(xpath = ".//td[6]")
    WebElement assertNameEle;
    @FindBy(xpath = ".//td[7]")
    WebElement assignedToEle;
    @FindBy(xpath = ".//td[2]")
    WebElement exeNumEle;

    public Boolean checkTableData(String assignedToUser, String assertName, String description) {
        boolean hasNextPage = true;
        boolean foundText = false;
        while (hasNextPage) {
            for (int i = 0; i < tableDataEle.size(); i++) {
//                WebElement rowEle = tableDataEle.get(i);
//                WebElement assignedToElement;
//                WebElement descriptionElement;
//                WebElement assertNameElement;
//                try {
//                    descriptionElement = rowEle.findElement(By.xpath(".//td[5]"));
//                    assertNameElement = rowEle.findElement(By.xpath(".//td[6]"));
//                    assignedToElement = rowEle.findElement(By.xpath(".//td[7]"));
//                } catch (StaleElementReferenceException e) {
//                    System.out.println("Stale element reference encountered");
//                    try {
//                        Thread.sleep(3000);
//                    }catch (Exception e1){
//                        e1.printStackTrace();
//                    }
//                    tableDataEle = driver.findElements(By.xpath("//tbody//tr"));
//                    rowEle = tableDataEle.get(i);
//                    descriptionElement = rowEle.findElement(By.xpath(".//td[5]"));
//                    assertNameElement = rowEle.findElement(By.xpath(".//td[6]"));
//                    assignedToElement = rowEle.findElement(By.xpath(".//td[7]"));
//
//                }
                Boolean isValidRole = assignedToEle.getText().equalsIgnoreCase(assignedToUser);
                Boolean isValidDes = descriptionEle.getText().equalsIgnoreCase(description);
                Boolean isValidAssert = assertNameEle.getText().equalsIgnoreCase(assertName);
                if (isValidRole && isValidDes && isValidAssert) {
//                    execNum = rowEle.findElement(By.xpath(".//td[2]")).getText();
                    execNum = exeNumEle.getText();
                    foundText = true;
                    hasNextPage = false;
                    break;
                }
            }
//            try {
//                WebElement nextPageButton = driver.findElement(By.xpath("(//button[@class=\"ant-pagination-item-link\"])[2]"));
//                if (nextPageButton.isEnabled()) {
//                    nextPageButton.click();
//                } else {
//                    hasNextPage = false;
//                }
//            } catch (NoSuchElementException e) {
//                System.out.println("No next page button found. Exiting loop.");
//                hasNextPage = false;
//              }
        }
        return foundText;
    }

    @FindBy(xpath = "//ul[@role='menu']//li[4]")
    WebElement checkListExeButtonEle;
    @FindBy(xpath = "//tbody//tr")
    List<WebElement> tableDataList;
    @FindBy(xpath = "//span[contains(text(),'Open')]")
    WebElement openButtonEle;
    @FindBy(xpath = "//span[normalize-space()='Start']")
    WebElement startButtonEle;

    public void updateCheckListExecution(String assertName, String assignedToUser, String description) throws InterruptedException {
        waitForWebElementToAppear(checkListExeButtonEle);
        checkListExeButtonEle.click();
        boolean hasNextPage = true;

        while (hasNextPage) {
            for (WebElement rowDataEle : tableDataList) {
                WebElement assignedToElement;
                WebElement descriptionElement;
                WebElement assertNameElement;
                WebElement openElement;
                Thread.sleep(3000);
                try {
                    descriptionElement = rowDataEle.findElement(By.xpath(".//td[5]"));
                    assertNameElement = rowDataEle.findElement(By.xpath(".//td[6]"));
                    assignedToElement = rowDataEle.findElement(By.xpath(".//td[7]"));
                    openElement = rowDataEle.findElement(By.xpath("//span[contains(text(),'Open')]"));
                } catch (StaleElementReferenceException | NoSuchElementException e) {
                    System.out.println("Stale element reference encountered");
                    descriptionElement = rowDataEle.findElement(By.xpath(".//td[5]"));
                    assertNameElement = rowDataEle.findElement(By.xpath(".//td[6]"));
                    assignedToElement = rowDataEle.findElement(By.xpath(".//td[7]"));
                    openElement = driver.findElement(By.xpath("//span[contains(text(),'Open')]"));
                }

                Boolean isValidRole = assignedToElement.getText().equalsIgnoreCase(assignedToUser);
                Boolean isValidDes = descriptionElement.getText().equalsIgnoreCase(description);
                Boolean isValidAssert = assertNameElement.getText().equalsIgnoreCase(assertName);

                if (isValidRole && isValidDes && isValidAssert) {
                    openElement.click();
                    break;
                }
            }
//            try {
//                WebElement nextPageButton = driver.findElement(By.xpath("(//button[@class=\"ant-pagination-item-link\"])[2]"));
//                if (nextPageButton.isEnabled()) {
//                    nextPageButton.click(); // Click the next page button
//
//                } else {
//                    hasNextPage = false; // Set hasNextPage to false if there's no next page
//                }
//            } catch (NoSuchElementException e) {
//                System.out.println("No next page button found. Exiting loop.");
//                hasNextPage = false; // Exit loop if there's no next page button
//            }
            hasNextPage = false;
        }
        waitForWebElementToAppear(startButtonEle);
        startButtonEle.click();
        try {
            Thread.sleep(3000);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void logOut(String userName) {
        super.logOut(userName);
    }

    public void login(String userName, String password) {
        super.login(userName, password);
        ticketCount = dashBoardPage.actualTicketCount();
    }

    public void schedulerExecutionInProgress(String assertName, String assignedToUser, String description) throws InterruptedException {
        updateCheckListExecution(assertName, assignedToUser, description);
    }

    public void checkListExecutionClosed(String assertName, String assignedToUser, String description, List<String> remarkData) throws InterruptedException {
        updateCheckListExecution(assertName, assignedToUser, description);
        updateCheckList(remarkData);
    }

    @FindBy(xpath = "//table[@class='table table-stripped']//tbody/tr")
    List<WebElement> checkList;
    @FindBy(xpath = "//span[normalize-space()='Save Preview']")
    WebElement savePreviewEle;
    @FindBy(xpath = "//span[normalize-space()='Raise Ticket(s)']")
    WebElement raiseTicketEle;

    public void updateCheckList(List<String> remarkData) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < checkList.size(); i++) {
            String xpathExpressionStatus = "//div[contains(@id, 'checks_" + i + "_status')]//span[contains(text(),'Yes')]";
            String xpathExpressionRemark = ".//textarea[@id='checks_" + i + "_remark']";
            if (i == checkList.size() - 1) {
                xpathExpressionStatus = "//div[contains(@id, 'checks_" + i + "_status')]//span[contains(text(),'No')]";
                driver.findElement(By.xpath(xpathExpressionStatus)).click();
                driver.findElement(By.xpath(xpathExpressionRemark)).sendKeys(remarkData.get(i));
            } else {
                driver.findElement(By.xpath(xpathExpressionStatus)).click();
                driver.findElement(By.xpath(xpathExpressionRemark)).sendKeys(remarkData.get(i));
            }
        }
        savePreviewEle.click();
        raiseTicketEle.click();
    }

}
