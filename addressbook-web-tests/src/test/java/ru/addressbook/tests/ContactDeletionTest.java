package ru.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.addressbook.model.ContactData;
import ru.addressbook.model.Contacts;

import java.io.IOException;

import static org.awaitility.Awaitility.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTest extends TestBase {
    @BeforeMethod
    public void ensurePrecondition() throws IOException {
        if (app.getDbHelper().groups().size() == 0) {
            app.getNavigationHelper().goToGroupsPage();
            app.getGroupHelper().createGroup(app.getTestDataHelper().readGroupsFromXml().get(0));
        }
        if (app.getDbHelper().contacts().size() == 0) {
            app.getNavigationHelper().goToContactCreationPage();
            app.getContactHelper().contactCreation(app.getTestDataHelper().readContactsFromXml().get(0));
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
        verifyContactsListInUi();
    }
}