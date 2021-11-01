public class OneDayForecast {
    private String cityName;
    private int cityCode;
    private String date;
    private double temperatureLo;
    private double temperatureHigh;

    public OneDayForecast(String cityName, int cityCode, String date, double temperatureLo, double temperatureHigh) {
        this.cityName = cityName;
        this.cityCode = cityCode;
        this.date = date;
        this.temperatureLo = temperatureLo;
        this.temperatureHigh = temperatureHigh;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setTemperatureHigh(double temperatureHigh) {
        this.temperatureHigh = temperatureHigh;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTemperatureLo() {
        return temperatureLo;
    }

    public void setTemperatureLo(double temperatureLo) {
        this.temperatureLo = temperatureLo;
    }

    public double getTemperatureHigh() {
        return temperatureHigh;
    }

    @Override
    public String toString() {
        return "Forecast for city: " + this.cityName + " for date: " + this.date + "; Lo: " + this.temperatureLo + "; High: " + this.temperatureHigh;
    }
}
