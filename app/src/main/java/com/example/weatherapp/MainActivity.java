package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Model.MousamData;
import Model.Main;
import Model.Weather;
import Model.Wind;
import Model.myapi;
import RoomDatabase.RoomModel.DatabaseHelper;
import RoomDatabase.RoomModel.LocalWeatherData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    //Main API
String Url="https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";

//API Key
String apikey="5daec89513becb74802ffb6734b7dcb6";

private TextView tvTemp, tvWeather, tvWind, tvHumidity, tvCity, tvMainTemp;
private  String localCity,localTemp,localWeather,localHumidity,localWind;
String city;
private EditText inputCity;
private ProgressDialog progressDialog;
private   DatabaseHelper databaseHelper;

// Defing ImageView for search city icon
private ImageView searchCity;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTemp = findViewById(R.id.tvTemp);
        tvWeather = findViewById(R.id.tvWeather);
        tvWind = findViewById(R.id.tvWind);
        tvHumidity = findViewById(R.id.tvHumidity);
        inputCity=findViewById(R.id.inputCity);
        tvCity =findViewById(R.id.displayCity);
        tvMainTemp =findViewById(R.id.displayTemp);
        searchCity=findViewById(R.id.searchCity);

        progressDialog=new ProgressDialog(this);

          progressDialog.show();
        //Show the progressBar Dialouge  when the application will be open

         if(localCity==null || localCity=="")
         {
             WeatherInfo("");
         }
        databaseHelper  = DatabaseHelper.getDB(MainActivity.this);

        //Get all LocalWeatherData
        ArrayList<LocalWeatherData> arrLocalData = (ArrayList<LocalWeatherData>) databaseHelper.expenseDao().getAlldata();
        for(int i = 0; i< arrLocalData.size(); i++){
         localCity   =arrLocalData.get(i).getCity();
         localTemp =arrLocalData.get(i).getLocalTemp();
         localWeather =arrLocalData.get(i).getLocalWeather();
         localHumidity=arrLocalData.get(i).getLocalHumidity();
         localWind=arrLocalData.get(i).getLocalWind();
        }

    // Set all localdata to textView with validation

        if (localCity!=null){
            WeatherInfo(localCity);
            tvCity.setText(localCity);
        }
        if (localTemp!=null){
            tvTemp.setText(localTemp+"C");
            tvMainTemp.setText(localTemp+"C");
        }
        if (localWeather!=null){
           tvWeather.setText(localWeather);
        }
        if (localHumidity!=null){
           tvHumidity.setText(localHumidity+"%");
        }
        if (localWind!=null){
            tvWind.setText(localWind+" km/hr");
        }
        else {
            tvCity.setText("");
            tvMainTemp.setText("");
        }

        //Search City Btn ClickListner

       searchCity.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //progressBar show when button will be click
               progressDialog.show();
               city=inputCity.getText().toString().trim();

               //Validation for empty data
               if(city.isEmpty()){
                   inputCity.setError("Please Enter City");
                   inputCity.requestFocus();

                   //progressBar dissmiss
                   progressDialog.dismiss();
               }
               //if input is valid then call WeatherInfo method
               else {
                   WeatherInfo(city);

               }
           }
       });
    }
    //WeatherInfo Method
    public void WeatherInfo(String city){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myapi myapi=retrofit.create(Model.myapi.class);
        Call<MousamData> examplecall=myapi.getweather(city,apikey);

        examplecall.enqueue(new Callback<MousamData>() {
            @Override
            public void onResponse(Call<MousamData> call, Response<MousamData> response) {

                //if response not found
                if((response.code()==404)  ){
                    //check validation for invalid city
                    Toast.makeText(MainActivity.this, "Invalid City", Toast.LENGTH_SHORT).show();
                    inputCity.requestFocus();
                    inputCity.setText("");
                    progressDialog.dismiss();
                }
                else if (city=="" ||city==null){
                    progressDialog.dismiss();
                }

                // if response is sucessfull
                else if(response.isSuccessful()) {

                    //get data from MousamData model class
                    MousamData mydata = response.body();
                    Main maindata = mydata.getMain();
                    Wind windData = mydata.getWind();
                    Double temp = maindata.getTemp();
                    Integer temperature = (int) (temp - 273.5);

                    //Set all data to textViews
                    tvTemp.setText(temperature.toString() + "C");
                    inputCity.setText("");
                    tvCity.setText(city);
                    tvWind.setText(windData.getSpeed().toString()+" km/hr");
                    tvMainTemp.setText(temperature.toString() + "C");
                    tvHumidity.setText(maindata.getHumidity().toString()+"%");
                     List<Weather> weatherList =mydata.getWeatherList();
                     String desc=weatherList.get(0).getDescription();
                     String icon =weatherList.get(0).getIcon();
                     tvWeather.setText(desc);

                     //progressBar dismiss
                    progressDialog.dismiss();

                    databaseHelper.expenseDao().insertAllData(
                            new LocalWeatherData(city,desc,temperature.toString(),maindata.getHumidity().toString(),
                                    windData.getSpeed().toString()));
                    }
                }


            @Override
            public void onFailure(Call<MousamData> call, Throwable t) {
                Toast.makeText(MainActivity.this,"No Internet Connection", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }
}