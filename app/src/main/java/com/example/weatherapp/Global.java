package com.example.weatherapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.inputmethod.InputMethodManager;

import com.example.weatherapp.data_models.WeatherResponse;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Global {
    /* Two API keys, that must be changed */
    public static final String APP_ID = "Your OpenWeather API key";
    public static final String OPENCAGE_API = "Your OpenCageData API key";
    
    public static final String BASE_URL_WEATHER = "https://api.openweathermap.org/data/2.5/";
    public static final String BASE_URL_CITY = "https://api.opencagedata.com/geocode/v1/";
    
    public static final String PREFS_NAME = "weather_prefs";
    public static final int FRAGMENTS_COUNT = 2;
    public static final long MILLIS_IN_DAY = 86400000;

    public static int getCurrentHour() {
        SimpleDateFormat date = new SimpleDateFormat("HH");
        return Integer.parseInt(date.format(new Date()));
    }

    public static String convertDtToDay(long dt) {
        SimpleDateFormat date = new SimpleDateFormat("EEEE, dd MMM");
        return makeFirstUp(date.format(dt));
    }

    public static String convertDtRefr(int dt, int offset) {
        SimpleDateFormat date = new SimpleDateFormat(" dd MMM 'в' HH:mm");
        return date.format((long) dt * 1000 + offset);
    }

    public static String makeFirstUp(String str) {
        StringBuilder sb = new StringBuilder(str);
        String s = String.valueOf(sb.charAt(0));
        s = s.toUpperCase();
        sb.deleteCharAt(0);
        sb.insert(0, s);
        return sb.toString();
    }

    public static void saveData(SharedPreferences someData, WeatherResponse weather, String city) {
        SharedPreferences.Editor ed = someData.edit();
        Gson gson = new Gson();
        ed.putString("weather", gson.toJson(weather));
        ed.putString("city", city);
        ed.apply();
    }

    public static WeatherResponse getSavedWeather(SharedPreferences prefs) {
        if (prefs != null) {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            return gson.fromJson(parser.parse(prefs.getString("weather", "null")).
                    getAsJsonObject(), WeatherResponse.class);
        } else return null;
    }

    public static String getCity(SharedPreferences prefs) {
        if (prefs != null) {
            return prefs.getString("city", "No saved city");
        } else return null;
    }



}
