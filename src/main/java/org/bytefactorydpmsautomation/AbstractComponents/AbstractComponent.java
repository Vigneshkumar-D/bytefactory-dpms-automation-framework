package org.bytefactorydpmsautomation.AbstractComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AbstractComponent {
    public WebDriver driver;

    public AbstractComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void waitForElementToAppear(By findBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));

    }

    public void waitForWebElementToAppear(WebElement findBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(findBy));
    }

    @FindBy(xpath = "//a[normalize-space()='Logout']")
    WebElement logoutButtonEle;
    public void logOut(String userName){
        driver.findElement(By.xpath("//strong[normalize-space()='" + userName + "']")).click();
        waitForWebElementToAppear(logoutButtonEle);
        logoutButtonEle.click();
    }

    @FindBy(id="username")
    WebElement userNameEle1;
    @FindBy(id="password")
    WebElement passwordEle1;
    @FindBy(css=".btn.btn-lg.btn-primary.btn-block")
    WebElement submitEle1;
    @FindBy(id="basic_userName")
    WebElement userNameEle2;
    @FindBy(id="basic_password")
    WebElement passwordEle2;
    @FindBy(css=".ant-btn.css-ru2fok.ant-btn-primary.ant-btn-block")
    WebElement submitEle2;
    public void login(String userName, String password){
        userNameEle1.sendKeys(userName);
        passwordEle1.sendKeys(password);
        submitEle1.click();
        userNameEle2.sendKeys(userName);
        passwordEle2.sendKeys(password);
        submitEle2.click();
    }

    @FindBy(xpath = "//ul[@role='menu']//li[1]")
    WebElement dashBoardEle;
    @FindBy(xpath = "(//button[@class=\"ant-pagination-item-link\"])[2]")
    WebElement nextPageButtonEle;
    @FindBy(xpath = "//tbody//tr")
    List<WebElement> tableDataEle;
    public List<Integer> checkListExecutionStatus(String xpath) throws InterruptedException {
        waitForWebElementToAppear(dashBoardEle);
        dashBoardEle.click();
        WebElement statusCountEle = driver.findElement(By.xpath(xpath));
        waitForWebElementToAppear(statusCountEle);
        String scheduledCount = statusCountEle.getText();
        Actions actions = new Actions(driver);
        actions.moveToElement(statusCountEle).click().perform();
        List<Integer> scheduledCountList = new ArrayList<>();
        scheduledCountList.add(Integer.parseInt(scheduledCount));
        int checklistExecutionScheduled = 0;

        Thread.sleep(3000);

        boolean hasNextPage = true;
        while (hasNextPage) {
            if(tableDataEle.size() == 1){
                checklistExecutionScheduled = 0;
            }else{
                checklistExecutionScheduled += tableDataEle.size()-1;
            }
            try {
                if (nextPageButtonEle.isEnabled()) {
                    nextPageButtonEle.click();
                } else {
                    hasNextPage = false;
                }
            } catch (org.openqa.selenium.NoSuchElementException e) {
                System.out.println("No next page button found. Exiting loop.");
                hasNextPage = false;
            }
        }
        scheduledCountList.add(checklistExecutionScheduled);
        return scheduledCountList;
    }
}
