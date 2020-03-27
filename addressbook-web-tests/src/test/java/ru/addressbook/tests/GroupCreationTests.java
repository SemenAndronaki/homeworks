package ru.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.addressbook.model.GroupData;

import java.util.Set;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.getNavigationHelper().goToGroupsPage();
        Set<GroupData> before = app.getGroupHelper().getGroupSet();
        GroupData groupData = new GroupData().withGroupName("groupName").withGroupHeader("groupHeader").withGroupFooter("groupFooter");
        app.getGroupHelper().createGroup(groupData);

        Set<GroupData> after = app.getGroupHelper().getGroupSet();
        Assert.assertEquals(after.size(), before.size() + 1);

        groupData.withGroupId(after.stream().mapToInt((g) -> g.getGroupId()).max().getAsInt());
        before.add(groupData);
        Assert.assertEquals(after, before);
    }
}
