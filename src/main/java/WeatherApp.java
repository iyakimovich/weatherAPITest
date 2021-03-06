import java.io.IOException;
import java.util.Scanner;

public class WeatherApp {
    public static void main(String[] args) {
        System.out.println("Enter city name: ");
        Scanner scanner = new Scanner(System.in);

        //read city name from user
        String cityName = scanner.nextLine();
        String cityCode = "";

        try {
            cityCode = AccuWeatherAPIUtils.getCityCode(cityName);
            System.out.println(cityName + " " + cityCode);
        } catch (IOException e) {
            System.out.println("Cannot find city " + cityName + ". Error: " + e.getMessage());
        }

        String forecastJSon;
        if (cityCode.equals("")) {
            System.out.println("Cannot find city " + cityName);
        } else {
            try {
                forecastJSon = AccuWeatherAPIUtils.get5DaysForecastJSon(cityCode);
                System.out.println("Forecast JSon: " + forecastJSon);
            } catch (IOException e) {
                System.out.println("Cannot find forecast for city code " + cityCode + ". Error: " + e.getMessage());
            }
        }

    }
}
