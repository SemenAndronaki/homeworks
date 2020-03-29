package ru.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.addressbook.model.ContactData;

import java.util.Set;

public class ContactDeletionTest extends TestBase {
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
    public void testContactDeletion() {
        Set<ContactData> before = app.getContactHelper().getContacts();
        ContactData contact = before.iterator().next();
        app.getContactHelper().deleteContact(contact);
        app.getNavigationHelper().goToHomepage();
        Set<ContactData> after = app.getContactHelper().getContacts();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(contact);
        Assert.assertEquals(after, before);
    }
}
