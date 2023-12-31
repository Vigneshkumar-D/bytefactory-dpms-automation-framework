package org.bytefactorydpmsautomation.PageObjects;

import org.bytefactorydpmsautomation.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import java.util.NoSuchElementException;

public class ConfigurationMasterRolePage extends AbstractComponent {
    WebDriver driver;
    public ConfigurationMasterRolePage(WebDriver driver) {
        super(driver);
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//ul[@role='menu']//li[2]")
    WebElement configurationEle;
    @FindBy(xpath = "(//div[@class='ant-row ant-row-space-between css-ru2fok'])//div[2]")
    WebElement addButton;
    @FindBy(xpath = "//input[@id='roleName']")
    WebElement roleNameEle;
    @FindBy(xpath = "//button[@type='submit']")
    WebElement submitButton;
    public void selectConfiguration(){
        waitForWebElementToAppear(configurationEle);
        configurationEle.click();
    }

    public void createRole(String roleName){
        addButton.click();
        roleNameEle.sendKeys(roleName);
        submitButton.click();
        configurationEle.click();
    }

    @FindBy(xpath = "//tr[@data-testId='row']//td[2]")
    List<WebElement> roleList;
    @FindBy(xpath = "(//button[@class='ant-pagination-item-link'])[2]")
    WebElement nextPageButton;
    public Boolean validateRole(String role){
        boolean hasNextPage = true;
        boolean foundText = false;
        while (hasNextPage) {
            for (WebElement webElement : roleList) {
                if (webElement.getText().trim().equalsIgnoreCase(role)) {
                    foundText = true;
                    hasNextPage = false;
                    break;
                }
            }
            try {
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
        return foundText;
    }

    @FindBy(xpath = "//input[@placeholder='Search...']")
    WebElement searchEle;
    public void searchRole(String roleName){
        waitForWebElementToAppear(searchEle);
        searchEle.sendKeys(roleName);
    }

    @FindBy(xpath = "//input[@id='roleName']")
    WebElement searchViewEle;
    public String viewRole(){
        configurationEle.click();
        return searchViewEle.getText();
    }

    public void validateViewRole(String role){
        boolean hasNextPage = true;
        boolean foundText = false;

        while (hasNextPage) {
            List<WebElement> elementList = driver.findElements(By.xpath("//tr[@data-testId='row']//td[2]"));
            for (WebElement webElement : elementList) {
                if (webElement.getText().trim().equalsIgnoreCase(role)) {
                    foundText = true;
                    searchViewEle.click();
                    break;
                }
            }

            try {
                WebElement nextPageButton = driver.findElement(By.xpath("(//button[@class='ant-pagination-item-link'])[2]"));
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
    }

}
