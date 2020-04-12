package ru.mantiss.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.mantiss.appManager.HttpSession;
import ru.mantiss.model.MailMessage;
import ru.mantiss.model.User;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase {
    private SessionFactory sessionFactory;

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }

    @Test
    public void testChangePassword() throws IOException {
        User user = readUserDataFromDb().stream().filter((u) -> !u.getUsername().equals("administrator")).findFirst().get();
        app.loginHelper().login("administrator", "root");
        app.navigationHelper().goToManageOverviewPage();
        app.navigationHelper().goToManageUserPage();
        app.changePasswordHelper().clickEditUser(user);
        app.changePasswordHelper().clickResetPassword();

        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String confirmationLink = findConfirmationMessages(user.getEmail(), mailMessages);
        String newPassword = "new password" + System.currentTimeMillis();
        app.changePasswordHelper().changePassword(confirmationLink, newPassword);

        HttpSession session = app.newSession();
        assertTrue(session.login(user.getUsername(), newPassword));
    }

    public List<User> readUserDataFromDb() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<User> result = session.createQuery("from User").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    private String findConfirmationMessages(String email, List<MailMessage> mailMessages) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)
                && m.text.contains("Someone (presumably you) requested a password change through e-mail")).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }
}