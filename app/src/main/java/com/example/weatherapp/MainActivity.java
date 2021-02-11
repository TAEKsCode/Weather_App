package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

import com.example.weatherapp.data_models.WeatherResponse;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity implements OnFragmentSendDataListener{

    TabLayout tabLayout;
    ViewPager2 viewPager;
    CustomPagerAdapter adapter;
    public SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        addTabs();
        prefs = getSharedPreferences(Global.PREFS_NAME, Context.MODE_PRIVATE);

    }


    @SuppressLint("WrongConstant")
    private void addTabs() {
        adapter = new CustomPagerAdapter(this);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(Global.FRAGMENTS_COUNT);
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText(R.string.choseCityTabHeader);
            } else tab.setText(R.string.forecastHeader);
        });
        mediator.attach();
    }

    @Override
    public void onSendData(WeatherResponse data, String cityName) {
        tabLayout.selectTab(tabLayout.getTabAt(1));
        ForecastWeatherFragment fragment = (ForecastWeatherFragment) getSupportFragmentManager().getFragments().get(1);
        if (fragment!=null){
            try {
                fragment.setData(data, cityName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Global.saveData(prefs, data, cityName);
        }
    }


    @Override
    public void onRefresh(String cityName) {
        ChooseCityFragment fragment = (ChooseCityFragment) getSupportFragmentManager().getFragments().get(0);
        if (fragment!=null){
            try {
                fragment.makeTwoAPICalls(cityName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}