package Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MousamData {
    @SerializedName("main")
    Main main;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public MousamData(Main main) {
        this.main = main;
    }

    @SerializedName("wind")
    Wind wind;

    public MousamData(Wind wind) {
        this.wind = wind;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    @SerializedName("weather")
    List<Weather> weatherList=null;

    public MousamData(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }
}
