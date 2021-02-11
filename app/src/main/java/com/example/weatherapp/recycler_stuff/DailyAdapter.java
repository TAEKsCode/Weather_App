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
import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;
import com.example.weatherapp.data_models.Daily;
import com.example.weatherapp.data_models.DailyItem;
import com.example.weatherapp.data_models.WeatherResponse;

import java.util.Date;
import java.util.List;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final WeatherResponse response;
    private final Context context;

    public DailyAdapter(Context context, WeatherResponse response) {
        this.response = response;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public DailyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.daily_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyAdapter.ViewHolder holder, int position) {
        Daily item = response.getDaily().get(position);
        holder.tv_temp.setText(String.valueOf(Math.round(item.getTemp().getMax())).concat("°C / ").
                concat(String.valueOf(Math.round(item.getTemp().getMin())).concat("°C")));
        holder.tv_date.setText(Global.convertDtToDay(new Date().getTime() + (Global.MILLIS_IN_DAY * position)));
        holder.tv_descrip.setText(Global.makeFirstUp(item.getWeather().get(0).description));
        int imageResource = context.getResources().getIdentifier(
                "@drawable/".concat(item.getWeather().get(0).getIcon()), null, context.getPackageName());
        holder.imageDaily.setImageResource(imageResource);
    }

    @Override
    public int getItemCount() {
        return response.getDaily().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageDaily;
        final TextView tv_temp, tv_date, tv_descrip;

        ViewHolder(View view) {
            super(view);
            imageDaily = view.findViewById(R.id.imageDaily);
            tv_temp = view.findViewById(R.id.tvDailyTemp);
            tv_date = view.findViewById(R.id.tvDailyDate);
            tv_descrip = view.findViewById(R.id.tvDailyDescrip);
        }
    }
}
