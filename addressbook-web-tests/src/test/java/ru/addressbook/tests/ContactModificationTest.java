package ru.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.addressbook.model.ContactData;
import ru.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTest extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        if (app.getDbHelper().contacts().size() == 0) {
            app.getNavigationHelper().goToContactCreationPage();
            app.getContactHelper().contactCreation(new ContactData().withFirstName("first name").withLastName("last name")
                    .withAddress("address").withMobileNumber("123").withEmail("234@mail.ru").withGroup("groupName"));
        }
    }

    @Test
    public void testContactModification() {
        Contacts before = app.getDbHelper().contacts();
        ContactData contact = before.iterator().next();
        ContactData newContact = new ContactData().withFirstName("first name" + System.currentTimeMillis()).withLastName("last name1")
                .withAddress("address1").withMobileNumber("+7(123)").withWorkNumber("222-222").withEmail("2334@mail.ru").withEmail2("ff")
                .withId(contact.getId()).withPhoto(new File("src/test/resources/img.jpg"));

        app.getNavigationHelper().goToHomepage();
        app.getContactHelper().modifyContact(newContact);
        assertThat(app.getContactHelper().getContactsCount(), equalTo(before.size()));

        Contacts after = app.getDbHelper().contacts();
        assertThat(after, equalTo(before.without(contact).withAdded(newContact)));
    }
}