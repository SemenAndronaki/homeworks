package ru.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.addressbook.model.GroupData;

import java.util.Set;


public class GroupDeletionTests extends TestBase {
    @BeforeMethod
    public void ensurePrecondition() {
        app.getNavigationHelper().goToGroupsPage();
        if (app.getGroupHelper().getGroupSet().size() == 0) {
            app.getGroupHelper().createGroup(new GroupData().withGroupName("groupName").withGroupHeader("groupHeader").withGroupFooter("groupFooter"));
        }
    }

    @Test
    public void testGroupDeletion() {
        Set<GroupData> before = app.getGroupHelper().getGroupSet();
        GroupData deletedGroup = before.iterator().next();
        app.getGroupHelper().deleteGroup(deletedGroup);

        Set<GroupData> after = app.getGroupHelper().getGroupSet();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(deletedGroup);
        Assert.assertEquals(after, before);
    }
}
