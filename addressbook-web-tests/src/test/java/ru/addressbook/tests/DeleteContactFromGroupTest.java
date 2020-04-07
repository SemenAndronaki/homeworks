package ru.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.addressbook.model.ContactData;
import ru.addressbook.model.Groups;
import ru.addressbook.model.GroupData;

import java.io.IOException;
import java.util.Comparator;
import java.util.stream.Collectors;

public class DeleteContactFromGroupTest extends TestBase {
    @BeforeMethod
    public void ensurePrecondition() throws IOException {
        if (app.getDbHelper().groups().size() == 0) {
            app.getNavigationHelper().goToGroupsPage();
            app.getGroupHelper().createGroup(app.getTestDataHelper().readGroupsFromXml().get(0));
        }
    }

    @Test
    public void testDeleteContactFromGroup() throws IOException {
        app.getNavigationHelper().goToContactCreationPage();
        GroupData group = app.getDbHelper().groups().iterator().next();
        app.getContactHelper().contactCreation(app.getTestDataHelper().readContactsFromXml().get(0).inGroup(group));
        ContactData contact = app.getDbHelper().contacts().stream().max(Comparator.comparing(ContactData::getId)).get();

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
