import java.io.IOException;
import java.util.Scanner;

public class WeatherApp {
    public static void main(String[] args){
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
                System.out.println("Cannot find forecast for city code " + cityCode + ". Error: " + e.getMessage());
            }
        }

    }
}
