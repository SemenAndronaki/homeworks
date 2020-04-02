package ru.addressbook.tests;

import org.testng.annotations.Test;
import ru.addressbook.model.ContactData;
import ru.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {
        File photo = new File("src/test/resources/img.jpg");
        ContactData contactData = new ContactData().withFirstName("first name").withLastName("last name")
                .withAddress("address").withMobileNumber("123").withEmail("234@mail.ru").withGroup("groupName").withPhoto(photo);

        app.getNavigationHelper().goToHomepage();
        Contacts before = app.getContactHelper().getContacts();
        app.getNavigationHelper().goToContactCreationPage();
        app.getContactHelper().contactCreation(contactData);

        assertThat(app.getContactHelper().getContactsCount(), equalTo(before.size() + 1));
        Contacts after = app.getContactHelper().getContacts();
        assertThat(after, equalTo(before.withAdded(contactData.withId((after.stream().mapToInt((c) -> c.getId()).max().getAsInt())))));
    }
}
