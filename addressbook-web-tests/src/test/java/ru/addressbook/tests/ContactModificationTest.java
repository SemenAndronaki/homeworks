package ru.addressbook.tests;

import org.testng.annotations.Test;
import ru.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase {

    @Test
    public void testContactModification() {
        app.getNavigationHelper().goToHomepage();
        app.getNavigationHelper().clickEditContactButton();
        app.getContactHelper().fillContactData(new ContactData("first name1", "last name1", "address1", "1234", "2345@mail.ru"));
        app.getContactHelper().submitContactUpdate();
        app.getNavigationHelper().returnToHomePage();
    }

}