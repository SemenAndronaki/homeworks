package ru.rest.appManager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.message.BasicNameValuePair;
import ru.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

public class RestHelper {
    private final ApplicationManager app;

    public RestHelper(ApplicationManager app) {
        this.app = app;
    }

    public int createIssue(Issue newIssie) throws IOException {
        String json = getExecutor().execute(Request.Post(app.getProperty("url.issues") + "?limit=500")
                .bodyForm(new BasicNameValuePair("subject", newIssie.getSubject()),
                        new BasicNameValuePair("description", newIssie.getDescription())))
                .returnContent().asString();
        JsonElement parsed = JsonParser.parseString(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }

    public Set<Issue> getIssues() throws IOException {
        Response response = getExecutor().execute(Request.Get(app.getProperty("url.issues") + ".json" + "?limit=500"));
        String json = response.returnContent().asString();
        JsonElement parsed = JsonParser.parseString(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {}.getType());
    }

    public Issue getIssueById(int id) throws IOException {
        Response response = getExecutor().execute(Request.Get(String.format(app.getProperty("url.issues")+"/%s.json", id)));
        String json = response.returnContent().asString();
        JsonElement parsed = JsonParser.parseString(json);
        JsonElement issuesJson = parsed.getAsJsonObject().get("issues");
        return ((Set<Issue>) (new Gson().fromJson(issuesJson, new TypeToken<Set<Issue>>() {}.getType()))).iterator().next();
    }

    private Executor getExecutor() {
        return Executor.newInstance().auth(app.getProperty("username"), app.getProperty("password"));
    }

    public String getIssueResolutionById(int issueId) throws IOException {
        return getIssueById(issueId).getState();
    }
}