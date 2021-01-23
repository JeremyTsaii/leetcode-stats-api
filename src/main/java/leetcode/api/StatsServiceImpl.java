package leetcode.api;

import org.springframework.stereotype.Service;
import java.io.IOException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.configurationprocessor.json.JSONException;
import okhttp3.Request;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Response;

@Service
public class StatsServiceImpl implements StatsService {
    @Override
    public StatsResponse getStats(String username) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        String query = String.format("{\"query\":\"query getUserProfile($username: String!) { allQuestionsCount { difficulty count } matchedUser(username: $username) { contributions { points } profile { reputation ranking } submitStats { acSubmissionNum { difficulty count submissions } totalSubmissionNum { difficulty count submissions } } } } \",\"variables\":{\"username\":\"%s\"}}", username);
        RequestBody body = RequestBody.create(mediaType, query);
        Request request = new Request.Builder()
                .url("https://leetcode.com/graphql/")
                .method("POST", body)
                .addHeader("referer", String.format("https://leetcode.com/%s/", username))
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();

            // Inspect response
            String responseString = response.body().string();
            JSONObject jsonObject = new JSONObject(responseString);

            if (response.isSuccessful()) {
                // Parse GraphQL response
                return new StatsResponse("success", "retrieved", 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
            } else {
                return new StatsResponse("error", jsonObject.getString("error"), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
            }
        } catch (IOException ex) {
            return new StatsResponse("error", ex.getMessage(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        } catch (JSONException ex) { // Query serialization error
            return new StatsResponse("error", ex.getMessage(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        }
    }
}
