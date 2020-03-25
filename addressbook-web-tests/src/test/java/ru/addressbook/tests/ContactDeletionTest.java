package ru.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.addressbook.model.ContactData;

import java.util.HashSet;
import java.util.List;

public class ContactDeletionTest extends TestBase {
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
    public void testContactDeletion() {
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size() - 1);
        app.getContactHelper().clickDeleteContacts();
        app.getContactHelper().closePopupWindow();
        app.getNavigationHelper().goToHomepage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(before.size() - 1);
        Assert.assertEquals(new HashSet<>(after), new HashSet<>(before));
    }
}
