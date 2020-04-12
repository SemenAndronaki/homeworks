package ru.mantiss.appManager;

import org.openqa.selenium.By;
import ru.mantiss.model.User;

public class ChangePasswordHelper extends HelperBase {

    public ChangePasswordHelper(ApplicationManager app) {
        super(app);
    }

    public void clickEditUser(User user) {
        click(By.xpath(String.format("//a[@href='manage_user_edit_page.php?user_id=%s']", user.getId())));
    }

    public void clickResetPassword() {
        click(By.xpath("//*[@id=\"manage-user-reset-form\"]"));
    }


    public void changePassword(String confirmationLink, String newPassword) {
        wd.get(confirmationLink);
        type(newPassword, By.id("password"));
        type(newPassword, By.id("password-confirm"));
        click(By.xpath("//button[@type = 'submit']"));
    }
}
