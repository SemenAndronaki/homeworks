package ru.rest.tests;

import org.testng.annotations.Test;
import ru.rest.appManager.RestHelper;
import ru.rest.model.Issie;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestTest extends TestBase{
    @Test
    public void testCreateIssue() throws IOException {
        skipIfNotFixed(2560);
        Set<Issie> oldIssues = app.rest().getIssues();
        Issie newIssue = new Issie().withSubject("Test issue" + System.currentTimeMillis()).withDescription("Test issue description");
        int id =  app.rest().createIssue(newIssue);
        Set<Issie> newIssues =  app.rest().getIssues();

        oldIssues.add(newIssue.withId(id));
        assertEquals(oldIssues, newIssues);
    }
}
