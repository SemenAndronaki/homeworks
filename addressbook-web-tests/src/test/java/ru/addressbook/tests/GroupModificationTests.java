package ru.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.addressbook.model.GroupData;
import ru.addressbook.model.Groups;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase {
    @BeforeMethod
    public void ensurePrecondition() throws IOException {
        if (app.getDbHelper().groups().size() == 0) {
            app.getNavigationHelper().goToGroupsPage();
            app.getGroupHelper().createGroup(app.getTestDataHelper().readGroupsFromXml().get(0));
        }
    }

    @Test
    public void testGroupModification() {
        Groups before = app.getDbHelper().groups();
        GroupData group = before.iterator().next();
        GroupData newGroupData = new GroupData().withGroupName("groupName").withGroupHeader("new header")
                .withGroupFooter("groupFooter").withGroupId(group.getGroupId());

        app.getNavigationHelper().goToGroupsPage();
        app.getGroupHelper().modifyGroup(newGroupData);

        assertThat(app.getGroupHelper().getGroupCount(), equalTo(before.size()));
        Groups after = app.getDbHelper().groups();
        assertThat(after, equalTo(before.without(group).withAdded(newGroupData)));
        verifyGroupListInUi();
    }
}
