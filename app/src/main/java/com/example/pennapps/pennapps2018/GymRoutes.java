package com.example.pennapps.pennapps2018;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GymRoutes {

    @GET("/fitness/schedule")
    Call<List<Gym>> getGymData();
}
