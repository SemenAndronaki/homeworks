package ru.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.addressbook.model.GroupData;
import ru.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;


public class GroupDeletionTests extends TestBase {
    @BeforeMethod
    public void ensurePrecondition() {
        app.getNavigationHelper().goToGroupsPage();
        if (app.getGroupHelper().getGroups().size() == 0) {
            app.getGroupHelper().createGroup(new GroupData().withGroupName("groupName").withGroupHeader("groupHeader").withGroupFooter("groupFooter"));
        }
    }

    @Test
    public void testGroupDeletion() {
        Groups before = app.getGroupHelper().getGroups();
        GroupData deletedGroup = before.iterator().next();
        app.getGroupHelper().deleteGroup(deletedGroup);

        Groups after = app.getGroupHelper().getGroups();
        assertEquals(after.size(), before.size() - 1);
        assertThat(after, equalTo(before.without(deletedGroup)));
    }
}
