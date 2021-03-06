package ru.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SessionHelper extends HelperBase {

    public SessionHelper(WebDriver wd) {
        super(wd);
    }

    public void login(String login, String password) {
        type(login, By.name("user"));
        type(password, By.name("pass"));
        click(By.xpath("//input[@value='Login']"));
    }

    public void logout() {
        click(By.linkText("Logout"));
    }
}
