package com.example.makeameal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.makeameal.Domain.Meal;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealListActivity extends AppCompatActivity{
    private ArrayList<Meal> meals;
    private RecyclerView mealsRecyclerView;
    private MyApi myApi;
    private MealListAdapter mealListAdapter;
    private String baseUrl = "https://shareameal-api.herokuapp.com/";
    private static final String TAG = MealListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);
        //initialize the recyclerview
        mealsRecyclerView = findViewById(R.id.meals_list);
        //create arraylist to store meals
        meals = new ArrayList<>();

        //create a view data method
        viewJsonData();
    }

    private void viewJsonData() {
        //create retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //retrofit api
        myApi = retrofit.create(MyApi.class);
        //display all data from api
        Call<MealList> arrayListCall = myApi.getMeals();
        System.out.println("call made");
        arrayListCall.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                System.out.println("got a response");
                meals = response.body().getResult();
                //for loop for data display
                for (int i = 0; i < meals.size(); i++) {
                    mealListAdapter = new MealListAdapter(meals, MealListActivity.this);
                    //implementation
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MealListActivity.this, LinearLayoutManager.VERTICAL, false);
                    //set layout manager
                    mealsRecyclerView.setLayoutManager(linearLayoutManager);
                    //set adapter
                    mealsRecyclerView.setAdapter(mealListAdapter);
            }
                }

            @Override
            public void onFailure(Call<MealList> call, Throwable t) {
                System.out.println("failed to get response");
                System.out.println(t.getMessage());
            }
        });

    }


}