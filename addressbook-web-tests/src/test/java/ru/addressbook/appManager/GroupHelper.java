package ru.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.addressbook.model.GroupData;
import ru.addressbook.model.Groups;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupHelper extends HelperBase {

    public GroupHelper(WebDriver wd) {
        super(wd);
    }

    public void submitGroupCreation() {
        click(By.name("submit"));
    }

    public void initGroupCreation() {
        click(By.name("new"));
    }

    public void fillGroupForm(GroupData groupData) {
        type(groupData.getGroupName(), By.name("group_name"));
        type(groupData.getGroupHeader(), By.name("group_header"));
        type(groupData.getGroupFooter(), By.name("group_footer"));
    }

    public void returnToGroupPage() {
        click(By.linkText("group page"));
    }

    public void createGroup(GroupData groupData) {
        initGroupCreation();
        fillGroupForm(groupData);
        submitGroupCreation();
        returnToGroupPage();
    }

    public void modifyGroup(GroupData newGroupData) {
        selectGroupById(newGroupData.getGroupId());
        initGroupModification();
        fillGroupForm(newGroupData);
        submitGroupUpdate();
        returnToGroupPage();
    }

    public void deleteGroup(GroupData group) {
        selectGroupById(group.getGroupId());
        deleteSelectedGroup();
        returnToGroupPage();
    }

    public void initGroupModification() {
        click(By.name("edit"));
    }

    public void submitGroupUpdate() {
        click(By.name("update"));
    }

    public void selectGroupById(int id) {
        wd.findElement(By.cssSelector(String.format("input[value='%s']", id))).click();
    }

    public void deleteSelectedGroup() {
        click(By.name("delete"));
    }

    public Groups getGroups() {
       Groups groups = new Groups();
        List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
        for (WebElement element : elements) {
            String name = element.getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            GroupData group = new GroupData().withGroupName(name).withGroupId(id).withGroupHeader(null).withGroupFooter(null);
            groups.add(group);
        }
        return groups;
    }
}
