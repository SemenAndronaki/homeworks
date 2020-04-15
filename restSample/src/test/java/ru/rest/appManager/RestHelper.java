package ru.rest.appManager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.message.BasicNameValuePair;
import ru.rest.model.Issie;

import java.io.IOException;
import java.util.Set;

public class RestHelper {
    private final ApplicationManager app;

    public RestHelper(ApplicationManager app) {
        this.app = app;
    }

    public int createIssue(Issie newIssie) throws IOException {
        String json = getExecutor().execute(Request.Post(app.getProperty("url.issues")+"?limit=500")
                .bodyForm(new BasicNameValuePair("subject", newIssie.getSubject()),
                        new BasicNameValuePair("description", newIssie.getDescription())))
                .returnContent().asString();
        JsonElement parsed = JsonParser.parseString(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }

    public Set<Issie> getIssues() throws IOException {
        Response response = getExecutor().execute(Request.Get(app.getProperty("url.issues")+"?limit=500"));
        String json = response.returnContent().asString();
        JsonElement parsed = JsonParser.parseString(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issie>>() {}.getType());
    }

    private Executor getExecutor() {
        return Executor.newInstance().auth(app.getProperty("username"), app.getProperty("password"));
    }

    public String getIssueResolutionById(int issueId) throws IOException {
        return getIssues().stream().filter((issue) -> issue.getId() == issueId).findFirst().get().getState();
    }
}