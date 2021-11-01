import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class WeatherApp {
    public static void main(String[] args) throws SQLException {
        System.out.println("Enter city name: ");
        Scanner scanner = new Scanner(System.in);

        String cityName = scanner.nextLine();
        String cityCode = "";

        try {
            cityCode = AccuWeatherAPIUtils.getCityCode(cityName);
            System.out.println(cityName + " " + cityCode);
        } catch (IOException e) {
            System.out.println("Cannot find city " + cityName + ". Error: " + e.getMessage());
        }

        String forecast;
        if (cityCode.equals("")) {
            System.out.println("Cannot find city " + cityName);
        } else {
            try {
                forecast= AccuWeatherAPIUtils.get5DaysForecastJSon(cityCode);
                System.out.println("Forecast " + forecast);
            } catch (IOException e) {
                System.out.println("Cannot find 5 days forecast for city code " + cityCode + ". Error: " + e.getMessage());
            }
        }

        //Get one day forecast
        OneDayForecast oneDayForecast;
        if (cityCode.equals("")) {
            System.out.println("Cannot find city " + cityName);
        } else {
            try {
                oneDayForecast = AccuWeatherAPIUtils.get1DayForecastJSon(cityName, cityCode);
                System.out.println(oneDayForecast);

                //Store forecast in DB Table
                DBUtils dbUtils = new DBUtils();
                dbUtils.saveForecast(oneDayForecast);

            } catch (IOException e) {
                System.out.println("Cannot find 1 day forecast for city code " + cityCode + ". Error: " + e.getMessage());
            }
        }

    }
}
