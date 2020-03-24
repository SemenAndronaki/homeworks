package ru.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupModificationTests extends TestBase {

    @Test
    public void testGroupModification() {
        app.getNavigationHelper().goToGroupsPage();
        GroupData newGroupData = new GroupData("new groupName", null, "groupFooter");
        if (!app.getGroupHelper().isThereGroup()) {
            app.getGroupHelper().createGroup(new GroupData("groupName", "groupHeader", "groupFooter"));
        }
        List<GroupData> before = app.getGroupHelper().getGroupList();
        app.getGroupHelper().selectGroup(before.size() - 1);

        app.getGroupHelper().initGroupModification();
        app.getGroupHelper().fillGroupForm(newGroupData);
        app.getGroupHelper().submitGroupUpdate();
        app.getNavigationHelper().goToGroupsPage();
        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size() - 1);
        newGroupData.setGroupId(after.get(before.size()).getGroupId());
        before.add(newGroupData);
        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getGroupId(), g2.getGroupId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(after, before);
    }
}
