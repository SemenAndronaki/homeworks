package ru.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {

    public ContactHelper(FirefoxDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
        click(By.xpath("(//input[@name='submit'])[2]"));
    }

    public void fillNewContact(ContactData contactData) {
        type(contactData.getFirstName(), By.name("firstname"));
        type(contactData.getLastName(), By.name("lastname"));
        type(contactData.getAddress(), By.name("address"));
        type(contactData.getMobileNumber(), By.name("mobile"));
        type(contactData.getEmail(), By.name("email"));
    }
}
