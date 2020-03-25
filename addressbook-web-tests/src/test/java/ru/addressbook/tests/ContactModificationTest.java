package ru.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTest extends TestBase {
    @BeforeMethod
    public void ensurePrecondition() {
        app.getNavigationHelper().goToHomepage();
        if (!app.getContactHelper().isContactHere()) {
            app.getNavigationHelper().goToPersonCreationPage();
            app.getContactHelper().contactCreation(new ContactData("first name", "last name",
                    "address", "123", "234@mail.ru", "groupName"));
        }
    }

    @Test
    public void testContactModification() {
        List<ContactData> before = app.getContactHelper().getContactList();
        int index = before.size() -1;
        ContactData newContactData = new ContactData("first name1", "last name1",
                "address1", "1234", "2345@mail.ru", before.get(index).getId());

        app.getContactHelper().modifyContact(index, newContactData);

        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(index);
        before.add(newContactData);
        Comparator<? super ContactData> byId = (o1, o2) -> Integer.compare(o1.getId(), o2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(after, before);
    }
}