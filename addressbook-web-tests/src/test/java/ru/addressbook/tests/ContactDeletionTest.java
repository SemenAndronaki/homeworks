package ru.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTest extends TestBase {

    @Test
    public void testContactDeletion() {
        app.getNavigationHelper().goToHomepage();
        app.getContactHelper().selectContact();
        app.getContactHelper().clickDeleteContacts();
        app.getContactHelper().closePopupWindow();
        app.getNavigationHelper().goToHomepage();
    }
}
