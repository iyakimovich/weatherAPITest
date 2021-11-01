import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;

public class AccuWeatherAPIUtils {
    public static String CITY_SEARCH_URL = "http://dataservice.accuweather.com/locations/v1/cities/search";
    public static String ONE_DAY_FORECAST_URL = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/";
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
                .addQueryParameter("apikey", API_KEY)
                .addQueryParameter("metric", "true");

        String forecastURL = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(forecastURL)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        //returning full JSon as string
        return response.body().string();
    }

    public static OneDayForecast get1DayForecastJSon(String cityName, String cityCode) throws IOException {
        OneDayForecast result;

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(ONE_DAY_FORECAST_URL + cityCode).newBuilder();
        urlBuilder
                .addQueryParameter("apikey", API_KEY)
                .addQueryParameter("metric", "true");

        String forecastURL = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(forecastURL)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        int cityCodeInt = Integer.parseInt(cityCode);
        String date = "";
        double tempLo = 0;
        double tempHi = 0;

        if (response.isSuccessful()) {
            String jsonResponse = response.body().string();
            if (jsonResponse.length() > 0) {
                ObjectMapper mapper = new ObjectMapper();

                JsonNode nodeMain = mapper.readTree(jsonResponse);

                JsonNode nodeForecast = nodeMain.get("DailyForecasts").get(0);

                if (nodeForecast != null) {
                    date =  nodeForecast.at("/Date").asText();

                    JsonNode nodeTemperature = nodeForecast.at("/Temperature");
                    tempLo =  nodeTemperature.at("/Minimum").at("/Value").asDouble();
                    tempHi =  nodeTemperature.at("/Maximum").at("/Value").asDouble();
                }
            }
        }

        result = new OneDayForecast(cityName, cityCodeInt, date, tempLo, tempHi);
        return result;
    }

}
