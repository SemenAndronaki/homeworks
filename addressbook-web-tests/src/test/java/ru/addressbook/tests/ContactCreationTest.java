package ru.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {
        ContactData contactData = new ContactData("first name", "last name",
                "address", "123", "234@mail.ru", "groupName");
        app.getNavigationHelper().goToHomepage();
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getNavigationHelper().goToPersonCreationPage();
        app.getContactHelper().contactCreation(contactData);
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() + 1);

        Comparator<? super ContactData> byId = (o1, o2) -> Integer.compare(o1.getId(), o2.getId());
        contactData.setId(after.stream().max(byId).get().getId());
        before.add(contactData);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(after, before);
    }
}
