package ru.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.addressbook.model.ContactData;
import ru.addressbook.model.Contacts;
import ru.addressbook.model.GroupData;

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
        attach(contactData.getPhoto(), By.name("photo"));
        type(contactData.getHomeNumber(), By.name("home"));
        type(contactData.getWorkNumber(), By.name("work"));
        type(contactData.getAddress(), By.name("address"));
        type(contactData.getMobileNumber(), By.name("mobile"));
        type(contactData.getEmail(), By.name("email"));
        type(contactData.getEmail3(), By.name("email3"));
        type(contactData.getEmail2(), By.name("email2"));
        type(contactData.getSecondaryAddress(), By.name("address2"));

        if (creation) {
            if (contactData.getGroups().size() > 0) {
                Assert.assertTrue(contactData.getGroups().size() == 1);
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getGroupName());
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void submitContactModification() {
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
        contactCache = null;
        returnToHomePage();
    }

    public void modifyContact(ContactData contact) {
        clickEditContactButton(contact.getId());
        fillContactData(contact, false);
        submitContactModification();
        contactCache = null;
        returnToHomePage();
    }

    public void deleteContact(ContactData contact) {
        selectContact(contact.getId());
        clickDeleteContacts();
        contactCache = null;
        closePopupWindow();
    }

    public void clickEditContactButton(int id) {
        wd.findElement(By.xpath(String.format("//a[@href=\"edit.php?id=%s\"]", id))).click();
    }

    public boolean isContactHere() {
        return isElementPresent(By.xpath("//img[@alt='Edit']"));
    }

    private Contacts contactCache = null;

    public Contacts getContacts() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        }
        contactCache = new Contacts();
        List<WebElement> elements = wd.findElements(By.cssSelector("#maintable > tbody > tr"));
        elements.remove(0);
        for (WebElement element : elements) {
            List<WebElement> elementCell = element.findElements(By.cssSelector("td"));
            int id = Integer.parseInt(elementCell.get(0).findElement(By.tagName("input")).getAttribute("id"));
            String firstName = elementCell.get(2).getText();
            String lastName = elementCell.get(1).getText();
            String addresses = elementCell.get(3).getText();
            String emails = elementCell.get(4).getText();
            String phones = elementCell.get(5).getText();
            contactCache.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName)
                    .withAllAddresses(addresses).withAllEmails(emails).withAllPhones(phones));
        }
        return new Contacts(contactCache);
    }

    public int getContactsCount() {
        return wd.findElements(By.cssSelector("#maintable > tbody > tr")).size() - 1;
    }

    public ContactData getInfoFromEditForm(ContactData contact) {
        clickEditContactButton(contact.getId());
        String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String email1 = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withFirstName(firstName).withLastName(lastName).withHomeNumber(home)
                .withWorkNumber(work).withMobileNumber(mobile).withEmail(email1).withEmail2(email2)
                .withEmail3(email3).withAddress(address);
    }

    public void addContactToGroup(int id, GroupData group) {
        selectContact(id);
        selectGroup(group.getGroupId());
        click(By.name("add"));
        returnToHomepageWithSelectedGroup(group.getGroupName());
    }

    public void returnToHomepageWithSelectedGroup(String groupName){
        click(By.linkText(String.format("group page \"%s\"", groupName)));
    }

    private void selectGroup(int groupId) {
        new Select(wd.findElement(By.name("to_group"))).selectByValue(String.valueOf(groupId));
    }
    
    public void selectAddressbookForGroup(GroupData group) {
        new Select(wd.findElement(By.name("group"))).selectByValue(String.valueOf(group.getGroupId()));
    }

    public void clickRemoveContact() {
        click(By.name("remove"));
    }
}
