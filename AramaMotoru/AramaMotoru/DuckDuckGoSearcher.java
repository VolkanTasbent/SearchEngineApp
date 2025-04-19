package AramaMotoru;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class DuckDuckGoSearcher {

    private static final OkHttpClient client = new OkHttpClient();

    public static String search(String query) throws Exception {
        String url = "https://api.duckduckgo.com/?q=" + query.replace(" ", "+") + "&format=json&pretty=1";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return "Hata: " + response.code();
            }

            String responseBody = response.body().string();
            JSONObject json = new JSONObject(responseBody);

            StringBuilder html = new StringBuilder();

            if (json.has("RelatedTopics")) {
                JSONArray topics = json.getJSONArray("RelatedTopics");
                for (int i = 0; i < topics.length(); i++) {
                    JSONObject item = topics.getJSONObject(i);
                    if (item.has("Text") && item.has("FirstURL")) {
                        String title = item.getString("Text");
                        String link = item.getString("FirstURL");
                        html.append("<a href='").append(link).append("'>").append(title).append("</a><br><br>");
                    }
                }
            }

            return html.toString().isEmpty() ? "Sonuç bulunamadı." : html.toString();
        }
    }
}
