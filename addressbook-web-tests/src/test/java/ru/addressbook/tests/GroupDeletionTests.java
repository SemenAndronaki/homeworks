package ru.addressbook.tests;

import org.testng.annotations.Test;


public class GroupDeletionTests extends TestBase {

    @Test
    public void testGroupDeletion() {
        app.getNavigationHelper().goToGroupsPage();
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().clickDeleteGroup();
        app.getNavigationHelper().returnToGroups();
    }
}
