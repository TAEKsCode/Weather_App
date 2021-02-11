package com.example.weatherapp.retrofit;

import com.example.weatherapp.data_models.city.CityResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CoordinatesService {
    @GET("json?")
    Call<CityResponse> getLatLonByCityName(@Query("q")String city_name,
                                           @Query("key") String key);
}
