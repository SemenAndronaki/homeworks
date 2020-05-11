package ru.addressbook.bdd;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.remote.BrowserType;
import ru.addressbook.appManager.ApplicationManager;
import ru.addressbook.model.GroupData;
import ru.addressbook.model.Groups;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class StepDefinitions {
    private ApplicationManager app;
    private Groups groups;
    private GroupData newGroup;

    @Before
    public void init() throws IOException {
        app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));
        app.init();
    }

    @After
    public void stop() {
        app.stop();
        app = null;
    }

    @Given("^a set of groups$")
    public void loadGroups() {
        groups = app.getDbHelper().groups();
    }

    @When("^I create new group with name (.+), header (.+) and footer (.+)$")
    public void createGroup(String name, String header, String footer) {
        newGroup = new GroupData()
                .withGroupName(name).withGroupHeader(header).withGroupFooter(footer);
        app.getNavigationHelper().goToGroupsPage();
        app.getGroupHelper().createGroup(newGroup);
    }

    @Then("^the new set of groups is equal to the old set with added group$")
    public void verifyGroupCreated() {
        Groups after = app.getDbHelper().groups();
        assertThat(after, equalTo(groups.
                withAdded(newGroup.withGroupId(after.stream().mapToInt((g) -> g.getGroupId()).max().getAsInt()))));
    }

    @Then("^the new set of groups is equal to the old set$")
    public void verifyGroupNotCreated() {
        Groups after = app.getDbHelper().groups();
        assertThat(after, equalTo(groups));
    }
}
