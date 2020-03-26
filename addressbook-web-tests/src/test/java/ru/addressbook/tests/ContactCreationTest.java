package ru.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {
        ContactData contactData = new ContactData().withFirstName("first name").withLastName("last name")
                .withAddress("address").withMobileNumber("123").withEmail("234@mail.ru").withGroup("groupName");

        app.getNavigationHelper().goToHomepage();
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getNavigationHelper().goToPersonCreationPage();
        app.getContactHelper().contactCreation(contactData);
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() + 1);

        Comparator<? super ContactData> byId = (o1, o2) -> Integer.compare(o1.getId(), o2.getId());
        before.add(contactData.withId(after.stream().max(byId).get().getId()));
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(after, before);
    }
}
