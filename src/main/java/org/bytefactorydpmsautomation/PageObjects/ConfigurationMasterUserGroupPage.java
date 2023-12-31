package org.bytefactorydpmsautomation.PageObjects;

import org.bytefactorydpmsautomation.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ConfigurationMasterUserGroupPage extends AbstractComponent {
    WebDriver driver;

    public ConfigurationMasterUserGroupPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[normalize-space()='User Group']")
    WebElement userGroupCreationEle;
    @FindBy(xpath = "//span[normalize-space()='Add']")
    WebElement addButtonEle;

    @FindBy(xpath = "//input[@id='userGroupName']")
    WebElement userGroupNameEle;
    @FindBy(xpath = "//input[@id='description']")
    WebElement getUserGroupDescriptionEle;
    @FindBy(xpath = "//input[@id='ahid']")
    WebElement getUserGroupSiteEle;
    @FindBy(xpath = "//span[@class='ant-select-tree-title']")
    WebElement BAFLEle;
    @FindBy(xpath = "//input[@id='userIds']")
    WebElement groupUsersDropDownEle;
    @FindBy(xpath = "//button[@type='submit']")
    WebElement saveButtonEle;

    public void createUserGroup(String groupName, String description, String user1, String user2) {
        userGroupCreationEle.click();
        addButtonEle.click();
        userGroupNameEle.sendKeys(groupName);
        getUserGroupSiteEle.click();
        BAFLEle.click();
        groupUsersDropDownEle.click();
        groupUsersDropDownEle.sendKeys(user1);
        getUserGroupDescriptionEle.sendKeys(description);
        WebElement member1 = driver.findElement(By.xpath("//div[contains(text(),'" + user1 + "')]"));
        waitForWebElementToAppear(member1);
        member1.click();

        groupUsersDropDownEle.click();
        WebElement member2 = driver.findElement(By.xpath("//div[contains(text(),'" + user1 + "')]"));
        waitForWebElementToAppear(member2);
        member2.click();
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='none';", groupUsersDropDownEle);
        saveButtonEle.click();
    }

    @FindBy(xpath = "//tr[@data-testid='row']//td[2]")
    List<WebElement> userGroupList;

    @FindBy(xpath = "//li[@title='Next Page']//button")
    WebElement nextPageButton;

    public boolean validateUserGroup(String groupName) {
        boolean hasNextPage = true;
        boolean foundText = false;

        while (hasNextPage) {
//            List<WebElement> elementList = driver.findElements(By.xpath("//tr[@data-testid='row']//td[2]"));
            Iterator<WebElement> iterator = userGroupList.iterator();

            while (iterator.hasNext()) {
                WebElement webElement = iterator.next();

                if (webElement.getText().trim().equalsIgnoreCase(groupName)) {
                    foundText = true;
                    hasNextPage = false;
                    break;
                }
            }

            try {
//                WebElement nextPageButton = driver.findElement(By.xpath("//li[@title='Next Page']//button"));
                if (nextPageButton.isEnabled()) {
                    nextPageButton.click();

                } else {
                    hasNextPage = false;
                }
            } catch (NoSuchElementException e) {
                System.out.println("No next page button found. Exiting loop.");
                hasNextPage = false;
            }
        }
        return  foundText;
    }
}
