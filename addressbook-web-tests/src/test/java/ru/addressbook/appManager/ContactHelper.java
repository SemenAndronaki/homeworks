package ru.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.addressbook.model.ContactData;
import ru.addressbook.model.GroupData;

import java.util.ArrayList;
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

    public void selectContact(int i) {
        wd.findElements(By.name("selected[]")).get(i).click();
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

    public void modifyContact(int index, ContactData newContactData) {
        clickEditContactButton(index);
        fillContactData(newContactData, false);
        submitContactUpdate();
        returnToHomePage();
    }

    public void clickEditContactButton(int i) {
        wd.findElements(By.xpath("//img[@alt='Edit']")).get(i).click();
    }

    public boolean isContactHere() {
        return isElementPresent(By.xpath("//img[@alt='Edit']"));
    }

    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<>();
        List<WebElement> elements = wd.findElements(By.cssSelector("#maintable > tbody > tr"));
        elements.remove(0);
        for (WebElement element : elements) {
            List<WebElement> elementCell = element.findElements(By.cssSelector("td"));
            int id = Integer.parseInt(elementCell.get(0).findElement(By.tagName("input")).getAttribute("id"));
            contacts.add(new ContactData(elementCell.get(2).getText(), elementCell.get(1).getText(), elementCell.get(3).getText(),
                    elementCell.get(5).getText(), elementCell.get(4).getText(), id));
        }
        return contacts;
    }
}
