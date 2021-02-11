package com.example.weatherapp.data_models;

public class HourlyItem {
    private int temperature;
    private int time;
    private String imageCode;

    public HourlyItem(int temperature, int time, String imageCode){
        this.temperature = temperature;
        this.time = time;
        this.imageCode = imageCode;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getTime() {
        return time;
    }

    public String getImageCode() {
        return imageCode;
    }
}
