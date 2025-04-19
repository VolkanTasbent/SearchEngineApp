package AramaMotoru;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

public class WikipediaSearcher {

    private static final OkHttpClient client = new OkHttpClient();

    public static String search(String query) throws Exception {
        String formattedQuery = query.replace(" ", "%20");
        String url = "https://en.wikipedia.org/api/rest_v1/page/summary/" + formattedQuery;

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

            if (json.has("extract")) {
                return json.getString("extract");
            } else {
                return "Sonuç bulunamadı.";
            }
        }
    }
}
