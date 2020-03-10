package ru.addressbook.tests;

import org.testng.annotations.*;
import ru.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.getNavigationHelper().goToGroupsPage();
        app.getGroupHelper().initGroupCreation();
        app.getGroupHelper().fillGroupForm(new GroupData("groupName", "groupHeader", "groupFooter"));
        app.getGroupHelper().submitGroupCreation();
        app.getNavigationHelper().returnToGroups();
    }
}
