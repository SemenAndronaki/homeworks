package ru.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.addressbook.model.GroupData;

import java.util.Set;

public class GroupModificationTests extends TestBase {
    @BeforeMethod
    public void ensurePrecondition(){
        app.getNavigationHelper().goToGroupsPage();
        if (app.getGroupHelper().getGroupSet().size() == 0) {
            app.getGroupHelper().createGroup(new GroupData().withGroupName("groupName").withGroupHeader("groupHeader").withGroupFooter("groupFooter"));
        }
    }

    @Test
    public void testGroupModification() {
        Set<GroupData> before = app.getGroupHelper().getGroupSet();

        GroupData newGroupData = new GroupData().withGroupName("groupName").withGroupFooter("groupFooter").withGroupId(before.iterator().next().getGroupId());

        app.getGroupHelper().modifyGroup(newGroupData);

        Set<GroupData> after = app.getGroupHelper().getGroupSet();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size() - 1);
        before.add(newGroupData);
        Assert.assertEquals(after, before);
    }
}
