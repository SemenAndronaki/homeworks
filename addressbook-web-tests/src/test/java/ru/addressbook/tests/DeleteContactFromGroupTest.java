package ru.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.addressbook.model.ContactData;
import ru.addressbook.model.Contacts;
import ru.addressbook.model.Groups;
import ru.addressbook.model.GroupData;

import java.io.IOException;
import java.util.stream.Collectors;

public class DeleteContactFromGroupTest extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() throws IOException {
        if (app.getDbHelper().groups().size() == 0) {
            app.getNavigationHelper().goToGroupsPage();
            app.getGroupHelper().createGroup(app.getTestDataHelper().readGroupsFromXml().get(0));
        }
        if (app.getDbHelper().contacts().size() == 0) {
            app.getNavigationHelper().goToContactCreationPage();
            app.getContactHelper().contactCreation(app.getTestDataHelper().readContactsFromXml().get(0)
                    .inGroup(app.getDbHelper().groups().iterator().next()));
        }
        if (app.getDbHelper().contacts().stream().filter((c) -> !(c.getGroups().size() == 0))
                .collect(Collectors.toList()).size() == 0) {

            app.getNavigationHelper().goToHomepage();
            app.getContactHelper().addContactToGroup(app.getDbHelper().contacts().iterator().next().getId(),
                    app.getDbHelper().groups().iterator().next());
        }
    }

    @Test
    public void testDeleteContactFromGroup() {
        Groups groups = app.getDbHelper().groups();
        Contacts contacts = app.getDbHelper().contacts();

        ContactData contact = contacts.stream().filter((c) -> c.getGroups().size() > 0).findFirst().get();
        GroupData group = groups.stream().filter((g) -> contact.getGroups().contains(g)).findFirst().get();

        app.getContactHelper().selectAddressbookForGroup(group);
        app.getContactHelper().selectContact(contact.getId());
        app.getContactHelper().clickRemoveContact();
        app.getContactHelper().returnToHomepageWithSelectedGroup(group.getGroupName());

        Assert.assertEquals(app.getDbHelper().contacts().stream().
                filter((c) -> c.getId() == contact.getId() && c.getGroups().contains(group)).collect(Collectors.toList()).size(), 0);
        Assert.assertEquals(app.getDbHelper().groups().stream().
                filter((g) -> g.getGroupId() == group.getGroupId() && g.getContacts().contains(contact)).collect(Collectors.toList()).size(), 0);
    }
}
