package ru.addressbook.tests;

import org.testng.annotations.Test;
import ru.addressbook.model.GroupData;

public class GroupModificationTests extends TestBase {

    @Test
    public void testGroupModification() {
        app.getNavigationHelper().goToGroupsPage();
        if (!app.getGroupHelper().isThereGroup()){
            app.getGroupHelper().createGroup(new GroupData("groupName", "groupHeader", "groupFooter"));
        }
        app.getGroupHelper().selectGroupToEdit();
        app.getGroupHelper().initGroupModification();
        app.getGroupHelper().fillGroupForm(new GroupData("new groupName", null, "groupFooter"));
        app.getGroupHelper().submitGroupUpdate();
        app.getNavigationHelper().goToGroupsPage();
    }
}
