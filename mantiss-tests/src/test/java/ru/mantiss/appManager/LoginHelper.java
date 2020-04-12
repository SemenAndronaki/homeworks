package ru.mantiss.appManager;

import org.openqa.selenium.By;

public class LoginHelper extends HelperBase {

    public LoginHelper(ApplicationManager app) {
        super(app);
    }

    public void login(String userName, String password) {
        wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
        type(userName, By.id("username"));
        click(By.xpath("//input[@type = 'submit']"));
        type(password, By.id("password"));
        click(By.xpath("//input[@type = 'submit']"));
    }
}
