package com.example.weatherapp.data_models;

public class Weather {
    public int id;
    public String main;
    public String description;
    public String icon;

    public int getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return "a" + icon;
    }
}