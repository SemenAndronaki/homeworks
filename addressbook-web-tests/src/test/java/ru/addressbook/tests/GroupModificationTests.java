package ru.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.addressbook.model.GroupData;
import ru.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase {
    @BeforeMethod
    public void ensurePrecondition() {
        app.getNavigationHelper().goToGroupsPage();
        if (app.getGroupHelper().getGroups().size() == 0) {
            app.getGroupHelper().createGroup(new GroupData().withGroupName("groupName").withGroupHeader("groupHeader").withGroupFooter("groupFooter"));
        }
    }

    @Test
    public void testGroupModification() {
        Groups before = app.getGroupHelper().getGroups();

        GroupData group = before.iterator().next();
        GroupData newGroupData = new GroupData().withGroupName("groupName").withGroupFooter("groupFooter").withGroupId(group.getGroupId());

        app.getGroupHelper().modifyGroup(newGroupData);

        Groups after = app.getGroupHelper().getGroups();
        assertThat(after.size(), equalTo(before.size()));
        assertThat(after, equalTo(before.without(group).withAdded(newGroupData)));
    }
}
