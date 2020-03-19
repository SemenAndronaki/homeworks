package ru.addressbook.tests;

import org.testng.annotations.Test;
import ru.addressbook.model.GroupData;


public class GroupDeletionTests extends TestBase {

    @Test
    public void testGroupDeletion() {
        app.getNavigationHelper().goToGroupsPage();
        if (!app.getGroupHelper().isThereGroup()){
            app.getGroupHelper().createGroup(new GroupData("groupName", "groupHeader", "groupFooter"));
        }
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().clickDeleteGroup();
        app.getGroupHelper().returnToGroupPage();
    }
}
