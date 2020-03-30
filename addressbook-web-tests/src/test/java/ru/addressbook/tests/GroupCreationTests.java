package ru.addressbook.tests;

import org.testng.annotations.Test;
import ru.addressbook.model.GroupData;
import ru.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.getNavigationHelper().goToGroupsPage();
        Groups before = app.getGroupHelper().getGroups();
        GroupData group = new GroupData().withGroupName("groupName").withGroupHeader("groupHeader").withGroupFooter("groupFooter");
        app.getGroupHelper().createGroup(group);

        assertThat(app.getGroupHelper().getGroupCount(), equalTo(before.size() + 1));
        Groups after = app.getGroupHelper().getGroups();
        assertThat(after, equalTo(before.
                withAdded(group.withGroupId(after.stream().mapToInt((g) -> g.getGroupId()).max().getAsInt()))));
    }
}
