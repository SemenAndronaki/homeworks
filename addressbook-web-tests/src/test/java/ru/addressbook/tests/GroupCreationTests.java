package ru.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.addressbook.model.GroupData;
import ru.addressbook.model.Groups;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validGroupsFromXML() throws IOException {
        List<GroupData> groups = app.getTestDataHelper().readGroupsFromXml();
        return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }

    @DataProvider
    public Iterator<Object[]> validGroupsFromJson() throws IOException {
        return app.getTestDataHelper().validGroupsFromJson();
    }

    @Test(dataProvider = "validGroupsFromXML")
    public void testGroupCreation(GroupData group) {
        Groups before = app.getDbHelper().groups();

        app.getNavigationHelper().goToGroupsPage();
        app.getGroupHelper().createGroup(group);

        assertThat(app.getGroupHelper().getGroupCount(), equalTo(before.size() + 1));
        Groups after = app.getDbHelper().groups();
        assertThat(after, equalTo(before.
                withAdded(group.withGroupId(after.stream().mapToInt((g) -> g.getGroupId()).max().getAsInt()))));
        verifyGroupListInUi();
    }
}
