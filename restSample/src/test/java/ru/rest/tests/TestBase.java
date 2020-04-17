package ru.rest.tests;

import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;
import ru.rest.appManager.ApplicationManager;

import java.io.IOException;

public class TestBase {
    protected static final ApplicationManager app = new ApplicationManager();

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws IOException {
        app.init();
    }

    public void skipIfNotFixed(int issueId) throws IOException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    private boolean isIssueOpen(int issueId) throws IOException {
        return app.rest().getIssueResolutionById(issueId).equals("Open");
    }
}