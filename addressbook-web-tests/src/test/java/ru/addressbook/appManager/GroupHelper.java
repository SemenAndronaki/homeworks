package ru.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.addressbook.model.GroupData;

public class GroupHelper extends HelperBase {

    public GroupHelper(FirefoxDriver wd) {
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

    public void initGroupModification() {
        click(By.name("edit"));
    }

    public void submitGroupUpdate() {
        click(By.name("update"));
    }

    public void selectGroup() {
        click(By.name("selected[]"));
    }

    public void clickDeleteGroup() {
        click(By.name("delete"));
    }
}
