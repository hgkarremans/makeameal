package com.example.makeameal.Application;

import com.example.makeameal.Domain.Meal;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MealList {

    @SerializedName("result")
    @Expose
    private ArrayList<Meal> result;

    public ArrayList<Meal> getResult() {
        return result;
    }
}
