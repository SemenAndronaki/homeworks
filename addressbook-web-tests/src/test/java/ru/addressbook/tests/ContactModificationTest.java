package ru.addressbook.tests;

import org.testng.annotations.Test;
import ru.addressbook.model.ContactData;
import ru.addressbook.model.GroupData;

public class ContactModificationTest extends TestBase {

    @Test
    public void testContactModification() {
        app.getNavigationHelper().goToHomepage();
        if (!app.getContactHelper().isContactHere()) {
            app.getNavigationHelper().goToPersonCreationPage();
            app.getContactHelper().contactCreation(new ContactData("first name", "last name",
                    "address", "123", "234@mail.ru", "groupName"));
        }
        app.getContactHelper().clickEditContactButton();
        app.getContactHelper().fillContactData(new ContactData("first name1", "last name1",
                "address1", "1234", "2345@mail.ru", null), false);
        app.getContactHelper().submitContactUpdate();
        app.getContactHelper().returnToHomePage();
    }

}