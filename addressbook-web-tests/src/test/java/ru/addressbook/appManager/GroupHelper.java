package ru.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.addressbook.model.GroupData;
import ru.addressbook.model.Groups;

import java.util.List;

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
        groupCache = null;
        returnToGroupPage();
    }

    public void modifyGroup(GroupData newGroupData) {
        selectGroupById(newGroupData.getGroupId());
        initGroupModification();
        fillGroupForm(newGroupData);
        submitGroupModification();
        groupCache = null;
        returnToGroupPage();
    }

    public void deleteGroup(GroupData group) {
        selectGroupById(group.getGroupId());
        deleteSelectedGroup();
        groupCache = null;
        returnToGroupPage();
    }

    public void initGroupModification() {
        click(By.name("edit"));
    }

    public void submitGroupModification() {
        click(By.name("update"));
    }

    public void selectGroupById(int id) {
        wd.findElement(By.cssSelector(String.format("input[value='%s']", id))).click();
    }

    public void deleteSelectedGroup() {
        click(By.name("delete"));
    }

    private Groups groupCache = null;

    public Groups getGroups() {
        if (groupCache != null) {
            return new Groups(groupCache);
        }
        groupCache = new Groups();
        List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
        for (WebElement element : elements) {
            String name = element.getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            GroupData group = new GroupData().withGroupName(name).withGroupId(id).withGroupHeader(null).withGroupFooter(null);
            groupCache.add(group);
        }
        return new Groups(groupCache);
    }
}
