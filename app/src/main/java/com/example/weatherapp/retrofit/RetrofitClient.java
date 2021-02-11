package com.example.weatherapp.retrofit;

import com.example.weatherapp.Global;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit instance;

    public static Retrofit getInstance(String baseUrl){
        if (instance==null) return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        else return instance;
    }



//    Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl(Global.BASE_URL_WEATHER)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build();
//    WeatherService service = retrofit.create(WeatherService.class);
//    Call<WeatherResponse> call = service.getCurrentWeatherData("city",Global.APP_ID,"RU","metric");
}
