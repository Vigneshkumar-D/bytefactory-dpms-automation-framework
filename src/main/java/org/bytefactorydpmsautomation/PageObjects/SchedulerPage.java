package org.bytefactorydpmsautomation.PageObjects;

import org.bytefactorydpmsautomation.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class SchedulerPage extends AbstractComponent {
    WebDriver driver;
    public SchedulerPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//ul[@role='menu']//li[3]")
    WebElement schedulerEle;

    @FindBy(xpath = "//input[@id='userId']")
    WebElement assignedToEle;
    @FindBy(xpath = "//div[@class='ant-select-selection-overflow']")
    WebElement checkListEle;
    @FindBy(xpath = "//input[@id='frequency']")
    WebElement frequencyEle;

    @FindBy(xpath = "//input[@id='scheduleDate']")
    WebElement schedulerDateEle;
    @FindBy(xpath = "//div[@class='rbc-day-bg rbc-today']")
    WebElement todayEle;
    @FindBy(xpath = "//input[@id='assetId']")
    WebElement assertEle;
    @FindBy(xpath = "//table[@class='ant-picker-content']")
    WebElement startDateEle;
    @FindBy(xpath = "//input[@id='startTime']")
    WebElement startTimeEle;
    @FindBy(xpath = "//a[@class='ant-picker-now-btn'][normalize-space()='Now']")
    WebElement nowEle;
    @FindBy(xpath = "//input[@id='endTime']")
    WebElement endTimeEle;
    @FindBy(xpath = "//textarea[@id='description']")
    WebElement descriptionEle;
    @FindBy(xpath = "//span[normalize-space()='Save']")
    WebElement saveButtonEle;
    @FindBy(xpath = "//li[@class='ant-picker-ok'][normalize-space()='OK']")
    WebElement okButtonEle;
    public void createScheduler(String assertName, String checkList, String frequency, String assignedToUser, String startDate, String endDate, String startTime, String endTime, String description){
        WebElement assertNameElement = driver.findElement(By.xpath("//div[contains(text(),'" + assertName + "')]"));
        assertNameElement.click();

        checkListEle.click();
        WebElement checkListInput = driver.findElement(By.xpath("//div[contains(text(),'" + checkList + "')]"));
        waitForWebElementToAppear(checkListInput);
        checkListInput.click();

        frequencyEle.click();
        WebElement frequencyInput = driver.findElement(By.xpath("//div[@class='ant-select-item-option-content'][normalize-space()='" + frequency + "']"));
        waitForWebElementToAppear(frequencyInput);
        frequencyInput.click();

        assignedToEle.click();
        assignedToEle.sendKeys(assignedToUser);

        WebElement assignedToUserInput = driver.findElement(By.xpath("//div[contains(text(),'" + assignedToUser + "')]"));
        waitForWebElementToAppear(assignedToUserInput);
        assignedToUserInput.click();

        schedulerDateEle.click();
        startDateEle.click();
        nowEle.click();
        startTimeEle.click();
        nowEle.click();
        endTimeEle.sendKeys(endTime);
        okButtonEle.click();

        descriptionEle.sendKeys(description);
        saveButtonEle.click();

    }

    @FindBy(xpath = "//span[normalize-space()='Day']")
    WebElement dayButtonEle;
    @FindBy(xpath = "//div[@class='rbc-events-container']/div")
    List<WebElement> todaySchedulersEle;
    @FindBy(xpath = ".//h3[@style='margin-bottom: 1px;']")
    WebElement subElement;
    public boolean validateScheduler(String assignedToUser){
        waitForWebElementToAppear(dayButtonEle);
        Actions actions = new Actions(driver);
        actions.moveToElement(dayButtonEle).click().perform();
        boolean foundText = false;
        for (WebElement webElement : todaySchedulersEle) {
            try {
                waitForWebElementToAppear(subElement);
                if (subElement.getText().equalsIgnoreCase(assignedToUser)) {
                    foundText = true;
                    break;
                }
            } catch (NoSuchElementException e) {
                System.out.println("h3 element not found within this div");
            }
        }
        return foundText;
    }
}
