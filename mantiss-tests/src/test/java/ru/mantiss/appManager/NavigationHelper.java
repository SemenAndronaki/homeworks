package ru.mantiss.appManager;

import org.openqa.selenium.By;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(ApplicationManager app) {
        super(app);
    }

    public void goToManageOverviewPage() {
        click(By.xpath("//a[@href= '/mantisbt-2.24.0/mantisbt-2.24.0/manage_overview_page.php']"));
    }

    public void goToManageUserPage() {
        click(By.xpath("//a[@href= '/mantisbt-2.24.0/mantisbt-2.24.0/manage_user_page.php']"));
    }
}
