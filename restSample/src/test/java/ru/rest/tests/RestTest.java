package ru.rest.tests;

import org.testng.annotations.Test;
import ru.rest.appManager.RestHelper;
import ru.rest.model.Issie;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestTest {
    @Test
    public void testCreateIssue() throws IOException {
        RestHelper restHelper = new RestHelper();
        Set<Issie> oldIssues = restHelper.getIssues();
        Issie newIssue = new Issie().withSubject("Test issue" + System.currentTimeMillis()).withDescription("Test issue description");
        int id = restHelper.createIssue(newIssue);
        Set<Issie> newIssues = restHelper.getIssues();
        assertEquals(oldIssues.add(newIssue.withId(id)), newIssues);
    }
}
