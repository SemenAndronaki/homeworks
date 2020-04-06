package ru.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.addressbook.model.GroupData;
import ru.addressbook.model.Groups;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;


public class GroupDeletionTests extends TestBase {
    @BeforeMethod
    public void ensurePrecondition() throws IOException {
        if (app.getDbHelper().groups().size() == 0) {
            app.getNavigationHelper().goToGroupsPage();
            app.getGroupHelper().createGroup(app.getTestDataHelper().readGroupsFromXml().get(0));
        }
    }

    @Test
    public void testGroupDeletion() {
        Groups before = app.getDbHelper().groups();
        GroupData deletedGroup = before.iterator().next();

        app.getGroupHelper().deleteGroup(deletedGroup);

        assertEquals(app.getGroupHelper().getGroupCount(), before.size() - 1);
        Groups after = app.getDbHelper().groups();
        assertThat(after, equalTo(before.without(deletedGroup)));
        verifyGroupListInUi();
    }
}
