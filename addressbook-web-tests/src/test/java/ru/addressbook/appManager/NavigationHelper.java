package ru.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(FirefoxDriver wd) {
        super(wd);
    }

    public void returnToHomePage() {
        click(By.linkText("home page"));
    }

    public void goToPersonCreationPage() {
        click(By.linkText("add new"));
    }

    public void returnToGroups() {
        click(By.linkText("group page"));
    }

    public void goToGroupsPage() {
        click(By.linkText("groups"));
    }

    public void clickEditContactButton() {
        click(By.xpath("//img[@alt='Edit']"));
    }

    public void goToHomepage() {
        click(By.linkText("home"));
    }
}
