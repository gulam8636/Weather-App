package RoomDatabase.RoomModel;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName ="LocalWeatherInfo")
public class LocalWeatherData {
    @PrimaryKey(autoGenerate = true)
    private int key;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @ColumnInfo(name="city")
    private String city;

    @ColumnInfo(name="LocalWeather")
    private String localWeather;

    @ColumnInfo(name="LocalTemp")
    private String localTemp;

    @ColumnInfo(name = "LocalHumidity")
    private String localHumidity;

    @ColumnInfo(name = "LocalWind")
    private String localWind;

//constructor


    public LocalWeatherData(String city, String localWeather, String localTemp, String localHumidity, String localWind) {
        this.city = city;
        this.localWeather = localWeather;
        this.localTemp = localTemp;
        this.localHumidity = localHumidity;
        this.localWind = localWind;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        city = city;
    }

    public String getLocalWeather() {
        return localWeather;
    }

    public void setLocalWeather(String localWeather) {
        this.localWeather = localWeather;
    }

    public String getLocalTemp() {
        return localTemp;
    }

    public void setLocalTemp(String localTemp) {
        this.localTemp = localTemp;
    }

    public String getLocalHumidity() {
        return localHumidity;
    }

    public void setLocalHumidity(String localHumidity) {
        this.localHumidity = localHumidity;
    }

    public String getLocalWind() {
        return localWind;
    }

    public void setLocalWind(String localWind) {
        this.localWind = localWind;
    }
}
