package ru.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.addressbook.model.ContactData;
import ru.addressbook.model.Contacts;
import ru.addressbook.model.GroupData;
import ru.addressbook.model.Groups;

import java.io.IOException;
import java.util.stream.Collectors;

public class AddContactToGroupTest extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() throws IOException {
        Groups groups = app.getDbHelper().groups();
        Contacts contacts = app.getDbHelper().contacts();
        if (groups.size() == 0 || contacts.stream().filter((c) -> c.getGroups().equals(groups))
                .collect(Collectors.toList()).size() == contacts.size() && contacts.size() != 0) {
            app.getNavigationHelper().goToGroupsPage();
            app.getGroupHelper().createGroup(app.getTestDataHelper().readGroupsFromXml().get(0));
        }
        if (contacts.size() == 0) {
            app.getNavigationHelper().goToContactCreationPage();
            app.getContactHelper().contactCreation(app.getTestDataHelper().readContactsFromXml().get(0));
        }
    }

    @Test
    public void testAddContactToGroup() {
        Groups groups = app.getDbHelper().groups();
        Contacts contacts = app.getDbHelper().contacts();
        ContactData contact = contacts.stream().filter((c) -> !c.getGroups().equals(groups))
                .collect(Collectors.toList()).get(0);
        GroupData group = groups.stream().filter((g) -> !contact.getGroups().contains(g))
                .collect(Collectors.toList()).get(0);

        app.getNavigationHelper().goToHomepage();
        app.getContactHelper().addContactToGroup(contact.getId(), group);

        Assert.assertEquals(app.getDbHelper().contacts().stream().
                filter((c) -> c.getId() == contact.getId() && c.getGroups().contains(group)).collect(Collectors.toList()).size(), 1);
        Assert.assertEquals(app.getDbHelper().groups().stream().
                filter((g) -> g.getGroupId() == group.getGroupId() && g.getContacts().contains(contact)).collect(Collectors.toList()).size(), 1);
    }
}
