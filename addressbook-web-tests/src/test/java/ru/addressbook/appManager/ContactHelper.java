package ru.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.addressbook.model.ContactData;

import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
        click(By.xpath("(//input[@name='submit'])[2]"));
    }

    public void fillContactData(ContactData contactData, boolean creation) {
        type(contactData.getFirstName(), By.name("firstname"));
        type(contactData.getLastName(), By.name("lastname"));
        if (creation) {
            List<WebElement> groups = wd.findElement(By.name("new_group")).findElements(By.linkText(contactData.getGroup()));
            if (groups.size() != 0) {
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
        type(contactData.getAddress(), By.name("address"));
        type(contactData.getMobileNumber(), By.name("mobile"));
        type(contactData.getEmail(), By.name("email"));
    }

    public void submitContactUpdate() {
        click(By.xpath("(//input[@name='update'])[2]"));
    }

    public void selectContact() {
        click(By.name("selected[]"));
    }

    public void clickDeleteContacts() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void closePopupWindow() {
        wd.switchTo().alert().accept();
    }

    public void returnToHomePage() {
        click(By.linkText("home page"));
    }

    public void contactCreation(ContactData contactData) {
        fillContactData(contactData, true);
        submitContactCreation();
        returnToHomePage();
    }

    public void clickEditContactButton() {
        click(By.xpath("//img[@alt='Edit']"));
    }

    public boolean isContactHere() {
        return isElementPresent(By.xpath("//img[@alt='Edit']"));
    }
}
