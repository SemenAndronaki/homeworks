package ru.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupModificationTests extends TestBase {
    @BeforeMethod
    public void ensurePrecondition(){
        app.getNavigationHelper().goToGroupsPage();
        if (!app.getGroupHelper().isThereGroup()) {
            app.getGroupHelper().createGroup(new GroupData("groupName", "groupHeader", "groupFooter"));
        }
    }

    @Test
    public void testGroupModification() {
        List<GroupData> before = app.getGroupHelper().getGroupList();

        int index = before.size() - 1;
        GroupData newGroupData = new GroupData("new groupName", null, "groupFooter", before.get(index).getGroupId());

        app.getGroupHelper().modifyGroup(index, newGroupData);

        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size());
        before.remove(index);
        before.add(newGroupData);
        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getGroupId(), g2.getGroupId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(after, before);
    }
}
