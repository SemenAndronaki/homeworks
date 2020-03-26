package ru.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.getNavigationHelper().goToGroupsPage();
        List<GroupData> before = app.getGroupHelper().getGroupList();
        GroupData groupData = new GroupData().withGroupName("groupName").withGroupHeader("groupHeader").withGroupFooter("groupFooter");
        app.getGroupHelper().createGroup(groupData);

        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size() + 1);

        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getGroupId(), g2.getGroupId());
        before.add(groupData.withGroupId((after.stream().max(byId).get().getGroupId())));
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(after, before);
    }
}
