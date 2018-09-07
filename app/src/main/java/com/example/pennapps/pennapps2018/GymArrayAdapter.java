package com.example.pennapps.pennapps2018;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class GymArrayAdapter extends ArrayAdapter<Gym> {

    private Context context;
    private List<Gym> gyms;
    private SimpleDateFormat inputDateFormat, outputDateFormat;

    public GymArrayAdapter(@NonNull Context context, @NonNull List<Gym> objects) {
        super(context, 0, objects);
        this.context = context;
        this.gyms = objects;
        inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US);
        outputDateFormat = new SimpleDateFormat("h:mm a", Locale.US);
    }

    @Override
    public @NonNull View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.gym_listview_item, parent, false);
        }
        if (position < gyms.size()) {
            Gym gym = gyms.get(position);
            TextView gymName = convertView.findViewById(R.id.gymName);
            TextView gymStartTime = convertView.findViewById(R.id.startTime);
            TextView gymEndTime = convertView.findViewById(R.id.endTime);
            gymName.setText(gym.getName());
            try {
                gymStartTime.setText(outputDateFormat.format(inputDateFormat.parse(gym.getStartTime())));
                gymEndTime.setText(outputDateFormat.format(inputDateFormat.parse(gym.getEndTime())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }
}
