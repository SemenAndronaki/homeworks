package ru.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.addressbook.model.ContactData;
import ru.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTest extends TestBase {

    @Test
    public void testContactModification() {
        ContactData newContactData = new ContactData("first name1", "last name1",
                "address1", "1234", "2345@mail.ru", null);
        app.getNavigationHelper().goToHomepage();
        if (!app.getContactHelper().isContactHere()) {
            app.getNavigationHelper().goToPersonCreationPage();
            app.getContactHelper().contactCreation(new ContactData("first name", "last name",
                    "address", "123", "234@mail.ru", "groupName"));
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().clickEditContactButton(before.size() - 1);
        app.getContactHelper().fillContactData(newContactData, false);
        app.getContactHelper().submitContactUpdate();
        app.getContactHelper().returnToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size() - 1);
        newContactData.setId(after.get(before.size()).getId());
        before.add(newContactData);

        Comparator<? super ContactData> byId = (o1, o2) -> Integer.compare(o1.getId(), o2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(after, before);
    }

}