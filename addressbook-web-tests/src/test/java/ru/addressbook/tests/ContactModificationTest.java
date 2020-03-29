package ru.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.addressbook.model.ContactData;

import java.util.Set;

public class ContactModificationTest extends TestBase {
    @BeforeMethod
    public void ensurePrecondition() {
        app.getNavigationHelper().goToHomepage();
        if (!app.getContactHelper().isContactHere()) {
            app.getNavigationHelper().goToPersonCreationPage();
            app.getContactHelper().contactCreation(new ContactData().withFirstName("first name").withLastName("last name")
                    .withAddress("address").withMobileNumber("123").withEmail("234@mail.ru").withGroup("groupName"));
        }
    }

    @Test
    public void testContactModification() {
        Set<ContactData> before = app.getContactHelper().getContacts();
        ContactData contact = before.iterator().next();
        ContactData newContact = new ContactData().withFirstName("first name" + System.currentTimeMillis()).withLastName("last name1")
                .withAddress("address1").withMobileNumber("123").withEmail("2334@mail.ru").withId(contact.getId());

        app.getContactHelper().modifyContact(newContact);

        Set<ContactData> after = app.getContactHelper().getContacts();
        Assert.assertEquals(after.size(), before.size());

        before.remove(contact);
        before.add(newContact);
        Assert.assertEquals(after, before);
    }
}