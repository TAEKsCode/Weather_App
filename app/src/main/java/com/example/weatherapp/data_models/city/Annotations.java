package com.example.weatherapp.data_models.city;

import com.google.gson.annotations.SerializedName;

public class Annotations{
    @SerializedName("DMS")
    public DMS dMS;
    @SerializedName("MGRS")
    public String mGRS;
    @SerializedName("Maidenhead")
    public String maidenhead;
    @SerializedName("Mercator")
    public Mercator mercator;
    @SerializedName("OSM")
    public OSM oSM;
    @SerializedName("UN_M49")
    public UNM49 uN_M49;
    public int callingcode;
    public Currency currency;
    public String flag;
    public String geohash;
    public double qibla;
    public Roadinfo roadinfo;
    public Sun sun;
    public Timezone timezone;
    public What3words what3words;
    public String wikidata;
}