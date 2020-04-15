package ru.rest.appManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.message.BasicNameValuePair;
import ru.rest.model.Issie;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

public class RestHelper {

    public int createIssue(Issie newIssie) throws IOException {
        String json = getExecutor().execute(Request.Post("https://bugify.stqa.ru/api/issues.json")
                .bodyForm(new BasicNameValuePair("subject", newIssie.getSubject()),
                        new BasicNameValuePair("description", newIssie.getDescription())))
                .returnContent().asString();
        JsonElement parsed = JsonParser.parseString(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }

    public Set<Issie> getIssues() throws IOException {
        Response response = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues.json"));
        String json = response.returnContent().asString();
        JsonElement parsed = JsonParser.parseString(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issie>>() {}.getType());
    }

    private Executor getExecutor() {
        return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
    }

    public String getIssueResolutionById(int issueId) throws IOException {
        Response response = getExecutor().execute(Request.Get(String.format("https://bugify.stqa.ru/api/issues/%s.json", issueId)));
        String json = response.returnContent().asString();
        JsonArray issueInfo = Arrays.asList(JsonParser.parseString(json).getAsJsonObject().get("issues").getAsJsonArray()).stream()
                .filter((issue) -> issue.getAsJsonObject().get("id").getAsInt() == issueId).findFirst().get();
        return issueInfo.getAsJsonObject().get("state_name").getAsString();
    }
}