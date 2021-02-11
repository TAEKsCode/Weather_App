package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class CustomPagerAdapter extends FragmentStateAdapter {

    public CustomPagerAdapter(FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==0){
            return new ChooseCityFragment();
        }
        else return ForecastWeatherFragment.getInstance();
    }

    @Override
    public int getItemCount() {
        return Global.FRAGMENTS_COUNT;
    }
}
