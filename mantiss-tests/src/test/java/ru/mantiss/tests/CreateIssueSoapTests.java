package ru.mantiss.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.mantiss.model.Issue;
import ru.mantiss.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class CreateIssueSoapTests extends TestBase {
    @BeforeMethod
    public void skipTest() throws RemoteException, ServiceException, MalformedURLException {
        skipIfNotFixed(1);
    }

    @Test
    public void testCreateIssue() throws MalformedURLException, ServiceException, RemoteException {
        Set<Project> projects = app.soap().getProjects();
        Issue issue = new Issue().withSummary("Test issue")
                .withDescription("Test issue description").withProject(projects.iterator().next());
        Issue addedIssue = app.soap().addIssue(issue);
        assertEquals(issue.getSummary(), addedIssue.getSummary());
    }
}
