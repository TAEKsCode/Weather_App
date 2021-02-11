package com.example.weatherapp.data_models;

public class DailyItem {
    private int temperature;
    private String date;
    private String description;
    private String imageCode;

    public DailyItem(String date, int temperature, String imageCode, String description){
        this.temperature = temperature;
        this.date = date;
        this.imageCode = imageCode;
        this.description = description;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getImageCode() {
        return imageCode;
    }


}
