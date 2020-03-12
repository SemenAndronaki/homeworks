package ru.addressbook.tests;

import org.testng.annotations.*;
import ru.addressbook.model.ContactData;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {
        app.getNavigationHelper().goToPersonCreationPage();
        app.getContactHelper().fillContactData(new ContactData("first name", "last name", "address", "123", "234@mail.ru"));
        app.getContactHelper().submitContactCreation();
        app.getNavigationHelper().returnToHomePage();
    }
}
