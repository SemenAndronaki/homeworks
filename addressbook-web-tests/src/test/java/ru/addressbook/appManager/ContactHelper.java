package ru.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.addressbook.model.ContactData;
import ru.addressbook.model.Contacts;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public void selectContact(int id) {
        wd.findElement(By.cssSelector(String.format("input[value='%s']", id))).click();
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

    public void modifyContact(ContactData contact) {
        clickEditContactButton(contact.getId());
        fillContactData(contact, false);
        submitContactUpdate();
        returnToHomePage();
    }

    public void deleteContact(ContactData contact) {
        selectContact(contact.getId());
        clickDeleteContacts();
        closePopupWindow();
    }

    public void clickEditContactButton(int id) {
        List<WebElement> elements = wd.findElements(By.cssSelector("#maintable > tbody > tr"));
        elements.remove(0);
        for (WebElement element : elements) {
            List<WebElement> rowCells = element.findElements(By.cssSelector("td"));
            int contactId = Integer.parseInt(rowCells.get(0).findElement(By.tagName("input")).getAttribute("id"));
            if (contactId == id) {
                rowCells.get(7).click();
                return;
            }
        }
    }

    public boolean isContactHere() {
        return isElementPresent(By.xpath("//img[@alt='Edit']"));
    }

    public Contacts getContacts() {
        Contacts contacts = new Contacts();
        List<WebElement> elements = wd.findElements(By.cssSelector("#maintable > tbody > tr"));
        elements.remove(0);
        for (WebElement element : elements) {
            List<WebElement> elementCell = element.findElements(By.cssSelector("td"));
            int id = Integer.parseInt(elementCell.get(0).findElement(By.tagName("input")).getAttribute("id"));
            String firstName = elementCell.get(2).getText();
            String lastName = elementCell.get(1).getText();
            String address = elementCell.get(3).getText();
            String email = elementCell.get(4).getText();
            String mobileNumber = elementCell.get(5).getText();
            contacts.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName)
                    .withAddress(address).withEmail(email).withMobileNumber(mobileNumber));
        }
        return contacts;
    }
}
