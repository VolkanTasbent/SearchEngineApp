package AramaMotoru;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class GoogleSearch {

    private static final OkHttpClient client = new OkHttpClient();

    public static String search(String query) throws Exception {
        String apiKey = "AIzaSyC7xA0rnDNh_eBX-VBg7kstfPESk1vI1t8";  
        String cseId = "YOUR_CSE_ID";  // Buraya Google Custom Search Engine ID'nizi girin ben anahtar almadım alınca çalışır
        String url = "https://www.googleapis.com/customsearch/v1?q=" + query.replace(" ", "+") + "&key=" + apiKey + "&cx=" + cseId;

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

            if (json.has("items")) {
                JSONArray items = json.getJSONArray("items");
                for (int i = 0; i < items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);
                    if (item.has("title") && item.has("link")) {
                        String title = item.getString("title");
                        String link = item.getString("link");
                        html.append("<a href='").append(link).append("'>").append(title).append("</a><br><br>");
                    }
                }
            }

            return html.toString().isEmpty() ? "Sonuç bulunamadı." : html.toString();
        }
    }
}
