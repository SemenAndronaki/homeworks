package ru.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.addressbook.model.ContactData;
import ru.addressbook.model.Contacts;

import static org.awaitility.Awaitility.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTest extends TestBase {
    @BeforeMethod
    public void ensurePrecondition() {
        if (app.getDbHelper().contacts().size() == 0) {
            app.getNavigationHelper().goToContactCreationPage();
            app.getContactHelper().contactCreation(new ContactData().withFirstName("first name").withLastName("last name")
                    .withAddress("address").withMobileNumber("123").withEmail("234@mail.ru").withGroup("groupName"));
        }
    }

    @Test
    public void testContactDeletion() {
        Contacts before = app.getDbHelper().contacts();
        ContactData contact = before.iterator().next();

        app.getNavigationHelper().goToHomepage();
        app.getContactHelper().deleteContact(contact);
        app.getNavigationHelper().goToHomepage();

        await().until(() -> app.getContactHelper().getContactsCount() == (before.size() - 1));
        Contacts after = app.getDbHelper().contacts();
        assertThat(after, equalTo(before.without(contact)));
    }
}