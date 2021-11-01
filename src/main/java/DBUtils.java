import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {

    public static final String JDBC_PATH = "jdbc:sqlite:C:\\Users\\alexe\\IdeaProjects\\weatherAPI\\src\\main\\resources\\WeatheAppDB.db";

    private Connection connection;

    public void saveForecast(OneDayForecast forecast) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO WeatherForecast('CityName', 'CityCode', 'Date', 'TemperatureLo', 'TemperatureHi')" +
                        " VALUES (?, ?, ?, ?, ?)"
        )) {
            statement.setObject(1, forecast.getCityName());
            statement.setObject(2, forecast.getCityCode());
            statement.setObject(3, forecast.getDate());
            statement.setObject(4, forecast.getTemperatureLo());
            statement.setObject(5, forecast.getTemperatureHigh());

            statement.execute();
        }
    }

    public void deleteForecast(int cityCode) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE WeatherForecast WHERE CityCode = ?"
        )) {
            statement.setObject(1, cityCode);

            statement.execute();
        }
    }


    public List<OneDayForecast> loadForecast() throws SQLException {
        ArrayList<OneDayForecast> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(
                    "SELECT CityName, CityCode, Date, TemperatureLo, TemperatureHi " +
                    " FROM WeatherForecast"
            );

            while (resultSet.next()) {
                list.add(new OneDayForecast(
                        resultSet.getString("CityName"),
                        resultSet.getInt("CityCode"),
                        resultSet.getString("Date"),
                        resultSet.getDouble("TemperatureLo"),
                        resultSet.getDouble("TemperatureHi")
                ));
            }
        }

        return list;
    }

    public DBUtils() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(JDBC_PATH);
    }
}
