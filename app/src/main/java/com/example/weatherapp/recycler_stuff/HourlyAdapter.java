package com.example.weatherapp.recycler_stuff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.Global;
import com.example.weatherapp.R;
import com.example.weatherapp.data_models.Hourly;
import com.example.weatherapp.data_models.WeatherResponse;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final WeatherResponse response;
    private final Context context;

    public HourlyAdapter(Context context, WeatherResponse response) {
        this.response = response;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public HourlyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.hourly_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyAdapter.ViewHolder holder, int position) {
        Hourly hourlyItem = response.getHourly().get(position);
        holder.tv_temp.setText(String.valueOf(Math.round(hourlyItem.getTemp())).concat("°C"));
        int currentTime = Global.getCurrentHour() + position;
        if (currentTime >= 24) {
            currentTime -= 24;
        }
        holder.tv_time.setText(String.valueOf(currentTime).concat(":00"));
        int imageResource = context.getResources().getIdentifier(
                "@drawable/".concat(hourlyItem.getWeather().get(0).getIcon()), null, context.getPackageName());
        holder.imageWeather.setImageResource(imageResource);
    }

    @Override
    public int getItemCount() {
        return response.getHourly().size() / 2;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageWeather;
        final TextView tv_temp, tv_time;

        ViewHolder(View view) {
            super(view);
            imageWeather = view.findViewById(R.id.imageHourly);
            tv_temp = view.findViewById(R.id.tvTempHourly);
            tv_time = view.findViewById(R.id.tvTimeHourly);
        }
    }
}
