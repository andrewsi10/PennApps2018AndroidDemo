package com.example.pennapps.pennapps2018;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView listOfGyms;
    private Button loadGymsButton;
    private TextView title;
    private GymRoutes gymRoutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listOfGyms = findViewById(R.id.listOfGyms);
        loadGymsButton = findViewById(R.id.loadGymsButton);
        title = findViewById(R.id.title);
        setUpNetworking();
        loadGymsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadGyms().execute();
                loadGymsButton.setVisibility(View.INVISIBLE);
                title.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setUpNetworking() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(new TypeToken<List<Gym>>() {}.getType(), new GymDeserializer());
        Gson gson = gsonBuilder.create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pennlabs.org")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        gymRoutes = retrofit.create(GymRoutes.class);
    }

    private class GymDeserializer implements JsonDeserializer<List<Gym>> {

        @Override
        public List<Gym> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonElement content = json.getAsJsonObject().get("schedule");
            return new Gson().fromJson(content, new TypeToken<List<Gym>>() {
            }.getType());
        }
    }

    private class LoadGyms extends AsyncTask<String, Void, List<Gym>> {

        @Override
        protected List<Gym> doInBackground(String... strings) {
            try {
                Response<List<Gym>> response = gymRoutes.getGymData().execute();
                if (response.isSuccessful()) {
                    return response.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }

        @Override
        public void onPostExecute(List<Gym> gyms) {
            listOfGyms.setAdapter(new GymArrayAdapter(MainActivity.this, gyms));
        }
    }
}
