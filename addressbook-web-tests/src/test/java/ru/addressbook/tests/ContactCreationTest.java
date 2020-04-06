package ru.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.addressbook.model.ContactData;
import ru.addressbook.model.Contacts;
import ru.addressbook.model.Groups;

import java.io.IOException;
import java.util.Iterator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTest extends TestBase {
    @BeforeMethod
    public void ensurePrecondition() throws IOException {
        if (app.getDbHelper().groups().size() == 0) {
            app.getNavigationHelper().goToGroupsPage();
            app.getGroupHelper().createGroup(app.getTestDataHelper().readGroupsFromXml().get(0));
        }
    }

    @DataProvider
    public Iterator<Object[]> validContactsFromXML() throws IOException {
        return app.getTestDataHelper().validContactsFromXML();
    }

    @DataProvider
    public Iterator<Object[]> validContactsFromJson() throws IOException {
        return app.getTestDataHelper().validContactsFromJson();
    }

    @Test(dataProvider = "validContactsFromJson")
    public void testContactCreation(ContactData contactData) {
        Groups groups = app.getDbHelper().groups();
        Contacts before = app.getDbHelper().contacts();

        app.getNavigationHelper().goToContactCreationPage();
        app.getContactHelper().contactCreation(contactData.withPhoto(contactData.getPhoto()));

        assertThat(app.getContactHelper().getContactsCount(), equalTo(before.size() + 1));
        Contacts after = app.getDbHelper().contacts();
        assertThat(after, equalTo(before.withAdded(contactData.withId((after.stream().mapToInt((c) -> c.getId()).max().getAsInt())))));
        verifyContactsListInUi();
    }
}
