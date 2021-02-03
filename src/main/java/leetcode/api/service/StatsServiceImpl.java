package leetcode.api.service;

import leetcode.api.model.StatsResponse;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

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
        String query = String.format("{\"query\":\"query getUserProfile($username: String!) { allQuestionsCount { difficulty count } matchedUser(username: $username) { contributions { points } profile { reputation ranking } submissionCalendar submitStats { acSubmissionNum { difficulty count submissions } totalSubmissionNum { difficulty count submissions } } } } \",\"variables\":{\"username\":\"%s\"}}", username);
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

                // User not found
                if (jsonObject.has("errors")) {
                    return StatsResponse.error("error", "user does not exist");
                } else { // Parse user info
                    return decodeGraphqlJson(jsonObject);
                }
            } else {
                return StatsResponse.error("error", jsonObject.getString("error"));
            }
        } catch (IOException | JSONException ex) {
            return StatsResponse.error("error", ex.getMessage());
        }

    }

    private StatsResponse decodeGraphqlJson(JSONObject json) {
        int totalSolved = 0;
        int totalQuestions = 0;
        int easySolved = 0;
        int totalEasy = 0;
        int mediumSolved = 0;
        int totalMedium = 0;
        int hardSolved = 0;
        int totalHard = 0;
        float acceptanceRate = 0;
        int ranking = 0;
        int contributionPoints = 0;
        int reputation = 0;

        final Map<String, Integer> submissionCalendar = new TreeMap<>();

        try {
            JSONObject data = json.getJSONObject("data");
            JSONArray allQuestions = data.getJSONArray("allQuestionsCount");
            JSONObject matchedUser = data.getJSONObject("matchedUser");
            JSONObject submitStats = matchedUser.getJSONObject("submitStats");
            JSONArray actualSubmissions = submitStats.getJSONArray("acSubmissionNum");
            JSONArray totalSubmissions = submitStats.getJSONArray("totalSubmissionNum");

            // Fill in total counts
            totalQuestions = allQuestions.getJSONObject(0).getInt("count");
            totalEasy = allQuestions.getJSONObject(1).getInt("count");
            totalMedium = allQuestions.getJSONObject(2).getInt("count");
            totalHard = allQuestions.getJSONObject(3).getInt("count");

            // Fill in solved counts
            totalSolved = actualSubmissions.getJSONObject(0).getInt("count");
            easySolved = actualSubmissions.getJSONObject(1).getInt("count");
            mediumSolved = actualSubmissions.getJSONObject(2).getInt("count");
            hardSolved = actualSubmissions.getJSONObject(3).getInt("count");

            // Fill in etc
            float totalAcceptCount = actualSubmissions.getJSONObject(0).getInt("submissions");
            float totalSubCount = totalSubmissions.getJSONObject(0).getInt("submissions");
            if (totalSubCount != 0) {
                acceptanceRate = round((totalAcceptCount / totalSubCount) * 100, 2);
            }

            contributionPoints = matchedUser.getJSONObject("contributions").getInt("points");
            reputation = matchedUser.getJSONObject("profile").getInt("reputation");
            ranking = matchedUser.getJSONObject("profile").getInt("ranking");

            final JSONObject submissionCalendarJson = new JSONObject(matchedUser.getString("submissionCalendar"));

            for (String timeKey: submissionCalendarJson.keySet()) {
                submissionCalendar.put(timeKey, submissionCalendarJson.getInt(timeKey));
            }

        } catch (JSONException ex) {
            return StatsResponse.error("error", ex.getMessage());
        }

        return new StatsResponse("success", "retrieved", totalSolved, totalQuestions, easySolved, totalEasy, mediumSolved, totalMedium, hardSolved, totalHard, acceptanceRate, ranking, contributionPoints, reputation, submissionCalendar);
    }

    private float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
}
