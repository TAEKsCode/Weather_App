package com.example.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.weatherapp.data_models.WeatherResponse;
import com.example.weatherapp.recycler_stuff.DailyAdapter;
import com.example.weatherapp.recycler_stuff.HourlyAdapter;


import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForecastWeatherFragment#} factory method to
 * create an instance of this fragment.
 */
public class ForecastWeatherFragment extends Fragment {

    private RecyclerView hourlyList;
    private RecyclerView dailyList;
    private SwipeRefreshLayout refreshLayout;
    static ForecastWeatherFragment instance;
    private OnFragmentSendDataListener fragmentSendDataListener;

    public static ForecastWeatherFragment getInstance() {
        if (instance == null) {
            return new ForecastWeatherFragment();
        } else return instance;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_forecast_weather, container, false);
        hourlyList = root.findViewById(R.id.recyclerHourly);
        dailyList = root.findViewById(R.id.recyclerDaily);
        refreshLayout = root.findViewById(R.id.refresh_layout);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(Global.PREFS_NAME, Context.MODE_PRIVATE);
        loadSavedData(preferences);
        refreshLayout.setOnRefreshListener(() -> {
            fragmentSendDataListener.onRefresh(Global.getCity(preferences));
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentSendDataListener = (OnFragmentSendDataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    private void loadSavedData(SharedPreferences prefs) {
        try {
            setData(Global.getSavedWeather(prefs), Global.getCity(prefs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setData(WeatherResponse response, String cityName) throws Exception {
        HourlyAdapter hourlyAdapter = new HourlyAdapter(getActivity(), response);
        hourlyList.setAdapter(hourlyAdapter);
        DailyAdapter dailyAdapter = new DailyAdapter(getActivity(), response);
        dailyList.setAdapter(dailyAdapter);

        TextView currentDescrip = Objects.requireNonNull(getView()).findViewById(R.id.tvDescrip);
        currentDescrip.setText(Global.makeFirstUp(response.getCurrent().getWeather().get(0).getDescription()));

        TextView currentTemp = Objects.requireNonNull(getView()).findViewById(R.id.textViewTemp);
        currentTemp.setText(String.valueOf(Math.round(response.getCurrent().getTemp())));

        TextView textCityName = Objects.requireNonNull(getView()).findViewById(R.id.textViewCityName);
        textCityName.setText(cityName);

        TextView refreshDate = Objects.requireNonNull(getView().findViewById(R.id.tv_last_update));
        refreshDate.setText(Global.convertDtRefr(response.current.dt,response.timezone_offset));


        ImageView image = getView().findViewById(R.id.imageCurrentWeather);
        int imageResource = Objects.requireNonNull(getActivity()).getResources().getIdentifier(
                "@drawable/".concat(response.getCurrent().getWeather().get(0).getIcon()), null, getActivity().getPackageName());
        image.setImageResource(imageResource);

        refreshLayout.setRefreshing(false);
    }

}