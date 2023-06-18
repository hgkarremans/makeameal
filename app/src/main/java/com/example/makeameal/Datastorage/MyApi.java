package com.example.makeameal.Datastorage;

import com.example.makeameal.Application.MealList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyApi {
    @GET("api/meal")
    Call<MealList> getMeals();
}
