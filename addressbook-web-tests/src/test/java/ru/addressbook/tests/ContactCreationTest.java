package ru.addressbook.tests;

import org.testng.annotations.*;
import ru.addressbook.model.ContactData;
import ru.addressbook.model.GroupData;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {
        app.getNavigationHelper().goToPersonCreationPage();
        app.getContactHelper().contactCreation(new ContactData("first name", "last name",
                "address", "123", "234@mail.ru", "groupName"));
    }
}
