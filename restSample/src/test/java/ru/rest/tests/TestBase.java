package ru.rest.tests;

import org.testng.SkipException;
import ru.rest.appManager.RestHelper;

import java.io.IOException;

public class TestBase {

    public void skipIfNotFixed(int issueId) throws IOException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    private boolean isIssueOpen(int issueId) throws IOException {
        RestHelper restHelper = new RestHelper();
        return restHelper.getIssueResolutionById(issueId).equals("open");
    }

}
