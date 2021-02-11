package com.example.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.weatherapp.data_models.city.CityResponse;
import com.example.weatherapp.data_models.WeatherResponse;
import com.example.weatherapp.retrofit.CoordinatesService;
import com.example.weatherapp.retrofit.RetrofitClient;
import com.example.weatherapp.retrofit.WeatherService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseCityFragment} factory method to
 * create an instance of this fragment.
 */
public class ChooseCityFragment extends Fragment {

    private OnFragmentSendDataListener fragmentSendDataListener;

    private List<String> cities;
    private String lon = "", lat = "";


    public ChooseCityFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readFile();
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

    private void readFile() {
        cities = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.
                    requireNonNull(getActivity()).getAssets().open("cities.txt")));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replace(";", ", ");
                line = line.replace("?", "`");
                cities.add(line);
            }
        } catch (IOException e) {
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_choose_city, container, false);
        FloatingActionButton fab = root.findViewById(R.id.btnOpenMaps);
        fab.setOnClickListener(v -> {
            openGoogleMaps();
            Toast.makeText(getActivity(), "Гугль карты пока не завезли :)", Toast.LENGTH_SHORT).show();
        });

        AutoCompleteTextView editTextCity = root.findViewById(R.id.et_city);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(), R.layout.support_simple_spinner_dropdown_item, cities);
        editTextCity.setAdapter(adapter);

        editTextCity.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        Button btn_find = root.findViewById(R.id.btn_find);

        btn_find.setOnClickListener(v ->
                makeTwoAPICalls(editTextCity.getText().toString()));
        return root;
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void makeTwoAPICalls(String cityName) {
        Retrofit cityRetrofit = RetrofitClient.getInstance(Global.BASE_URL_CITY);

        CoordinatesService coordinatesService = cityRetrofit.create(CoordinatesService.class);
        Call<CityResponse> coordinatesCall = coordinatesService.getLatLonByCityName(
                cityName, Global.OPENCAGE_API);
        coordinatesCall.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(@NonNull Call<CityResponse> cityCall, @NonNull Response<CityResponse> cityResponse) {
                if (cityResponse.code() == 200) {
                    CityResponse callResponse = cityResponse.body();
                    assert callResponse != null;
                    try {
                        lon = String.valueOf(callResponse.results.get(0).geometry.lng);
                        lat = String.valueOf(callResponse.results.get(0).geometry.lat);
                        callForWeatherForecast(Global.makeFirstUp(cityName));

                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Невозможно определить населённый пункт", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CityResponse> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callForWeatherForecast(String cityName) {
        Retrofit retrofit = RetrofitClient.getInstance(Global.BASE_URL_WEATHER);

        WeatherService weatherService = retrofit.create(WeatherService.class);
        Call<WeatherResponse> call = weatherService.getCurrentWeatherData(
                lat, lon, Global.APP_ID, "minutely", "metric", "RU");
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;
                    fragmentSendDataListener.onSendData(weatherResponse, cityName);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void openGoogleMaps() {
        //Intent intent = new Intent();

        //startActivityForResult();
    }
}