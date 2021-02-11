package com.example.weatherapp;

import com.example.weatherapp.data_models.WeatherResponse;

public interface OnFragmentSendDataListener {
    void onSendData(WeatherResponse data, String cityName);
    void onRefresh(String cityName);
}
