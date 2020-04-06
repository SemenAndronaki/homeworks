package ru.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.addressbook.model.ContactData;
import ru.addressbook.model.Groups;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDataCheckTest extends TestBase {
    @BeforeMethod
    public void ensurePrecondition() throws IOException {
        if (app.getDbHelper().groups().size() == 0) {
            app.getNavigationHelper().goToGroupsPage();
            app.getGroupHelper().createGroup(app.getTestDataHelper().readGroupsFromXml().get(0));
        }
        Groups groups = app.getDbHelper().groups();
        app.getNavigationHelper().goToHomepage();
        if (!app.getContactHelper().isContactHere()) {
            app.getNavigationHelper().goToContactCreationPage();
            app.getContactHelper().contactCreation(new ContactData().withFirstName("first name").withLastName("last name")
                    .withAddress("address").withSecondaryAddress("address2").withMobileNumber("+7(111)").withHomeNumber("111-111")
                    .withWorkNumber("111 111").withEmail("email1@mail.ru").withEmail2("email2@mail.ru")
                    .withEmail3("email3@mail.ru").inGroup(groups.iterator().next()));
        }
    }

    @Test
    public void testCheckContactData() {
        app.getNavigationHelper().goToHomepage();
        ContactData contact = app.getContactHelper().getContacts().iterator().next();
        ContactData infoFromEditForm = app.getContactHelper().getInfoFromEditForm(contact);

        assertThat(contact.getAllPhones(), equalTo(mergePhones(infoFromEditForm)));
        assertThat(contact.getAllEmails(), equalTo(mergeEmails(infoFromEditForm)));
        assertThat(contact.getAllAddresses(), equalTo(mergeAddresses(infoFromEditForm)));
    }

    public String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomeNumber(), contact.getMobileNumber(), contact.getWorkNumber())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactDataCheckTest::cleaned)
                .collect(Collectors.joining("\n"));
    }

    public String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s) -> !s.equals(""))
                .collect(Collectors.joining("\n"));
    }

    public String mergeAddresses(ContactData contact) {
        return Arrays.asList(contact.getAddress())
                .stream().filter((s) -> !s.equals(""))
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }
}


