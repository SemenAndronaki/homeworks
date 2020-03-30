package ru.addressbook.tests;

import org.testng.annotations.Test;
import ru.addressbook.model.ContactData;
import ru.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.testng.Assert;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {
        ContactData contactData = new ContactData().withFirstName("first name").withLastName("last name")
                .withAddress("address").withMobileNumber("123").withEmail("234@mail.ru").withGroup("groupName");

        app.getNavigationHelper().goToHomepage();
        Contacts before = app.getContactHelper().getContacts();
        app.getNavigationHelper().goToPersonCreationPage();
        app.getContactHelper().contactCreation(contactData);

        assertThat(app.getContactHelper().getContactsCount(), equalTo(before.size() + 1));
        Contacts after = app.getContactHelper().getContacts();
        assertThat(after, equalTo(before.withAdded(contactData.withId((after.stream().mapToInt((c) -> c.getId()).max().getAsInt())))));
    }
}
