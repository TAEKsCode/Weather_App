package com.example.weatherapp.retrofit;


import com.example.weatherapp.data_models.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("onecall?")
    Call<WeatherResponse> getCurrentWeatherData(@Query("lat") String lat,
                                                @Query("lon") String lon,
                                                @Query("appid") String app_id,
                                                @Query("exclude") String exclude,
                                                @Query("units") String units,
                                                @Query("lang") String lang);
}
