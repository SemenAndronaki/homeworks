package ru.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.addressbook.model.ContactData;
import ru.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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
        Contacts before = app.getContactHelper().getContacts();
        ContactData contact = before.iterator().next();
        ContactData newContact = new ContactData().withFirstName("first name" + System.currentTimeMillis()).withLastName("last name1")
                .withAddress("address1").withMobileNumber("123").withEmail("2334@mail.ru").withId(contact.getId());

        app.getContactHelper().modifyContact(newContact);
        assertThat(app.getContactHelper().getContactsCount(), equalTo(before.size()));

        Contacts after = app.getContactHelper().getContacts();
        assertThat(after, equalTo(before.without(contact).withAdded(newContact)));
    }
}