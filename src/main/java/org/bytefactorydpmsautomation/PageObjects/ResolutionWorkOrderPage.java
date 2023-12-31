package org.bytefactorydpmsautomation.PageObjects;

import com.google.j2objc.annotations.Weak;
import org.bytefactorydpmsautomation.AbstractComponents.AbstractComponent;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class ResolutionWorkOrderPage extends AbstractComponent {
    WebDriver driver;
    String workOrderNum;
    CheckListExecutionPage checkListExecutionPage;
    public ResolutionWorkOrderPage(WebDriver driver){
        super(driver);
        this.driver=driver;
        PageFactory.initElements(driver, this);
        checkListExecutionPage = new CheckListExecutionPage(driver);
    }

    @FindBy(xpath = "//ul[@role='menu']//li[5]")
    WebElement resolutionWorkOrderEle;
    @FindBy(xpath = "//span[normalize-space()='Next']")
    WebElement nextButtonEle;
    @FindBy(xpath = "//ul[@role='menu']//li[2]")
    WebElement ConfigurationEle;
    @FindBy(xpath = "//input[@id='assignedToId']")
    WebElement assignedToEle;
    @FindBy(xpath = "//input[@id='dueDate']")
    WebElement dueDateEle;
    @FindBy(xpath = "//input[@id='priority']")
    WebElement priorityEle;
    @FindBy(xpath = "//a[@class='ant-picker-today-btn']")
    WebElement todayEle;
    @FindBy(xpath = "(//button[@class='ant-pagination-item-link'])[2]")
    WebElement nextPageButtonEle;
    @FindBy(xpath = "//textarea[@id='rca']")
    WebElement rcaInputEle;
    @FindBy(xpath = "//textarea[@id='ca']")
    WebElement caInputEle;
    @FindBy(xpath = "//textarea[@id='pa']")
    WebElement paInputEle;
    @FindBy(xpath = "//span[normalize-space()='Send For Approval']")
    WebElement sendForApprovalEle;
    @FindBy(xpath = "//span[normalize-space()='Approve']")
    WebElement approveButtonEle;
    @FindBy(xpath = "//span[normalize-space()='Go To List']")
    WebElement goToListEle;

    public void findTicket() throws InterruptedException {
        Thread.sleep(3000);
        boolean hasNextPage = true;
        while (hasNextPage) {

            List<WebElement> tableData = driver.findElements(By.xpath("//tbody//tr"));
            System.out.println(tableData.size());

            for (int i = 0; i < tableData.size(); i++) {
                WebElement webElement = tableData.get(i);
                WebElement openElement;
                WebElement execNumElement;

                try {
                    execNumElement = webElement.findElement(By.xpath(".//td[2]"));
                    openElement = driver.findElement(By.xpath("//span[contains(text(),'Open')]"));
                } catch (StaleElementReferenceException e) {
                    System.out.println("Stale element reference encountered");
                    Thread.sleep(3000);
                    tableData = driver.findElements(By.xpath("//tbody//tr"));
                    openElement = driver.findElement(By.xpath("//span[contains(text(),'Open')]"));
                    webElement = tableData.get(i);
                    execNumElement = webElement.findElement(By.xpath(".//td[2]"));
                }

                boolean isValidExecNum = execNumElement.getText().equalsIgnoreCase(checkListExecutionPage.getExecNum());

                if (isValidExecNum) {
                    hasNextPage = false;
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
            Thread.sleep(3000);
        }
    }

    public String getWoNum() throws InterruptedException {
        waitForWebElementToAppear(resolutionWorkOrderEle);
        resolutionWorkOrderEle.click();
        Thread.sleep(3000);
        boolean hasNextPage = true;
        while (hasNextPage) {
            List<WebElement> tableData = driver.findElements(By.xpath("//tbody//tr"));
            for (int i = 0; i < tableData.size(); i++) {
                WebElement rowData = tableData.get(i);
                WebElement execNumElement;

                try {
                    execNumElement = rowData.findElement(By.xpath(".//td[2]"));
                } catch (StaleElementReferenceException e) {
                    System.out.println("Stale element reference encountered");
                    Thread.sleep(3000);
                    tableData = driver.findElements(By.xpath("//tbody//tr"));
                    rowData = tableData.get(i);
                    execNumElement = rowData.findElement(By.xpath(".//td[2]"));

                }
                boolean isValidExecNum = execNumElement.getText().equalsIgnoreCase(checkListExecutionPage.getExecNum());
                if (isValidExecNum) {
                    workOrderNum = rowData.findElement(By.xpath(".//td[3]")).getText();
                    hasNextPage = false;
                    break;
                }
            }
            try {
                if (nextPageButtonEle.isEnabled()) {
                    nextPageButtonEle.click();
                } else {
                    hasNextPage = false;
                }
            } catch (NoSuchElementException e) {
                System.out.println("No next page button found. Exiting loop.");
                hasNextPage = false;
            }
        }
        return workOrderNum;
    }

    public void updateResolutionWorkOrder(String assignedTo, String priority, String dueDate) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForWebElementToAppear(ConfigurationEle);
        ConfigurationEle.click();
        waitForWebElementToAppear(resolutionWorkOrderEle);
        resolutionWorkOrderEle.click();
        this.findTicket();
        assignedToEle.click();

        try {
            WebElement desiredOption = null;
            do {
                Actions actions = new Actions(driver);
                actions.sendKeys(Keys.ARROW_DOWN).build().perform();
                desiredOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'" + assignedTo + "')]")));
            } while (!desiredOption.isDisplayed());
            desiredOption.click();
        } catch (Exception e) {
            e.printStackTrace();
        }

        priorityEle.click();
        WebElement priorityElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'" + priority + "')]")));
        priorityElement.click();
        dueDateEle.click();
//        dueDateEle.sendKeys();
        waitForWebElementToAppear(todayEle);
        todayEle.click();
        nextButtonEle.click();
        Thread.sleep(3000);
    }

    public void completeResolutionWorkOrder(String rca, String ca, String pa) throws InterruptedException {
        waitForWebElementToAppear(resolutionWorkOrderEle);
        resolutionWorkOrderEle.click();
        this.findTicket();
        Thread.sleep(3000);
        rcaInputEle.sendKeys(rca);
        caInputEle.sendKeys(ca);
        paInputEle.sendKeys(pa);
        sendForApprovalEle.click();
        Thread.sleep(3000);
    }

    public void provideApproval() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForWebElementToAppear(resolutionWorkOrderEle);
        resolutionWorkOrderEle.click();
        this.findTicket();
        approveButtonEle.click();
        waitForWebElementToAppear(goToListEle);
        goToListEle.click();
        Thread.sleep(3000);
    }

    public boolean checkRWOTicketStatus() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForWebElementToAppear(resolutionWorkOrderEle);
        resolutionWorkOrderEle.click();
        boolean hasNextPage = true;
        boolean foundText = false;
        while (hasNextPage) {
            List<WebElement> tableData =  driver.findElements(By.xpath("//tbody//tr"));
            for (int i = 0; i < tableData.size(); i++) {
                WebElement webElement = tableData.get(i);
                WebElement statusElement;
                WebElement execNumElement;

                try {
                    statusElement = webElement.findElement(By.xpath(".//td[9]"));
                    execNumElement = webElement.findElement(By.xpath(".//td[2]"));

                } catch (StaleElementReferenceException e) {
                    System.out.println("Stale element reference encountered");
                    Thread.sleep(3000);
                    tableData = driver.findElements(By.xpath("//tbody//tr"));
                    webElement = tableData.get(i);
                    statusElement = webElement.findElement(By.xpath(".//td[9]"));
                    execNumElement = webElement.findElement(By.xpath(".//td[2]"));
                }

                boolean isValidStatus = statusElement.getText().equalsIgnoreCase("Completed");
                boolean isValidExecNum = execNumElement.getText().equalsIgnoreCase(checkListExecutionPage.getExecNum());

                if (isValidExecNum && isValidStatus) {
                    foundText = true;
                    break;
                }
            }
            hasNextPage = false;
        }
        return foundText;
    }

}
