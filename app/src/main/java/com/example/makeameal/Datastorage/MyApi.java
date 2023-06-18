package com.example.makeameal.Datastorage;

import com.example.makeameal.Domain.Meal;
import com.example.makeameal.MealList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyApi {
    @GET("api/meal")
    Call<MealList> getMeals();
}
