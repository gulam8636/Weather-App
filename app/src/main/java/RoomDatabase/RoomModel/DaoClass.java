package RoomDatabase.RoomModel;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoClass {

    @Insert
    void insertAllData(LocalWeatherData localWeatherData);

    @Query("select * from localweatherinfo")
    List<LocalWeatherData> getAlldata();
}
