package ru.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.addressbook.model.ContactData;

import java.util.Set;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {
        ContactData contactData = new ContactData().withFirstName("first name").withLastName("last name")
                .withAddress("address").withMobileNumber("123").withEmail("234@mail.ru").withGroup("groupName");

        app.getNavigationHelper().goToHomepage();
        Set<ContactData> before = app.getContactHelper().getContacts();
        app.getNavigationHelper().goToPersonCreationPage();
        app.getContactHelper().contactCreation(contactData);
        Set<ContactData> after = app.getContactHelper().getContacts();
        Assert.assertEquals(after.size(), before.size() + 1);

        contactData.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
        before.add(contactData);
        Assert.assertEquals(after, before);
    }
}
