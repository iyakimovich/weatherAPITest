import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;

public class AccuWeatherAPIUtils {
    public static String CITY_SEARCH_URL = "http://dataservice.accuweather.com/locations/v1/cities/search";
    public static String FIVE_DAY_FORECAST_URL = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/";
    public static String API_KEY = "et3zxzIgssIISgoaCy5DA4gWFl3KKiHe";

    public static String getCityCode(String cityName) throws IOException {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(CITY_SEARCH_URL).newBuilder();
        urlBuilder
                .addQueryParameter("q", cityName)
                .addQueryParameter("apikey", API_KEY);

        String citySearchURL = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(citySearchURL)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        String result = "";
        if (response.isSuccessful()) {
            String jsonResponse = response.body().string();
            if (jsonResponse.length() > 0) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(jsonResponse).get(0);
                //Check if node is empty (bad response)
                if (node != null) {
                    result = node.at("/Key").asText();
                }
            }
        }
        return result;
    }

    public static String get5DaysForecastJSon(String cityCode) throws IOException {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(FIVE_DAY_FORECAST_URL + cityCode).newBuilder();
        urlBuilder
                .addQueryParameter("apikey", API_KEY);

        String forecastURL = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(forecastURL)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        //returning full JSon
        return response.body().string();
    }

}
