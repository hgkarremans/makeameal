package com.example.makeameal.Presentation;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makeameal.Application.MealList;
import com.example.makeameal.Application.NetworkUtils;
import com.example.makeameal.Datastorage.MealDAO;
import com.example.makeameal.Datastorage.MealDatabase;
import com.example.makeameal.Datastorage.MyApi;
import com.example.makeameal.Domain.Meal;
import com.example.makeameal.Logic.MealViewModel;
import com.example.makeameal.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealListActivity extends AppCompatActivity {
    private ArrayList<Meal> meals;
    private RecyclerView mealsRecyclerView;
    private MyApi myApi;
    private MealListAdapter mealListAdapter;
    private String baseUrl = "https://shareameal-api.herokuapp.com/";
    private static final String TAG = MealListActivity.class.getSimpleName();
    private MealViewModel mealViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        SpannableString spannableString = new SpannableString("Gerechten");
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(spannableString);
        Drawable drawable = getResources().getDrawable(R.drawable.baseline_arrow_back_24);

        getSupportActionBar().setHomeAsUpIndicator(drawable);
        //initialize the recyclerview
        mealsRecyclerView = findViewById(R.id.meals_list);
        //create arraylist to store meals
        meals = new ArrayList<>();

        //check for wifi connection:
        boolean isWifiConnected = NetworkUtils.isWifiConnected(this);
        if (isWifiConnected) {
            // Wi-Fi is connected
            Log.d(TAG, "onCreate: wifi connected");
            //create a view data method
            viewJsonData();

        } else {
            // Wi-Fi is not connected
            Log.d(TAG, "onCreate: wifi not connected");
            mealViewModel = new ViewModelProvider(this).get(MealViewModel.class);
            mealViewModel.getAllMeals().observe(this, meals -> {
                this.meals = (ArrayList<Meal>) meals;
                mealListAdapter = new MealListAdapter((ArrayList<Meal>) meals, MealListActivity.this);
                mealsRecyclerView.setAdapter(mealListAdapter);
                mealsRecyclerView.setLayoutManager(new LinearLayoutManager(MealListActivity.this));
            });
            Toast toast = Toast.makeText(MealListActivity.this, "Geopend uit opslag (offline)", Toast.LENGTH_SHORT);
            toast.show();

        }
        Button vega = findViewById(R.id.vega_button);
        ArrayList<Meal> veganMeals = new ArrayList<>();
        Button vegan = findViewById(R.id.vegan_button);
        ArrayList<Meal> vegetarianMeals = new ArrayList<>();
        Button clear = findViewById(R.id.clear_filter_button);
        vegan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veganMeals.clear();
                for(int i = 0; i < meals.size(); i++){
                    if(meals.get(i).getVegan() == true){
                        veganMeals.add(meals.get(i));
                    }
                }
                if (veganMeals.size() == 0){
                    Toast toast = Toast.makeText(MealListActivity.this, "Geen vegan gerechten gevonden", Toast.LENGTH_SHORT);
                    toast.show();
                }
                mealListAdapter = new MealListAdapter(veganMeals, MealListActivity.this);
                mealsRecyclerView.setAdapter(mealListAdapter);
            }
        });
        vega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vegetarianMeals.clear();
                for(int i = 0; i < meals.size(); i++){
                    if(meals.get(i).getVega() == true){
                        vegetarianMeals.add(meals.get(i));
                    }
                }
                if (vegetarianMeals.size() == 0){
                    Toast toast = Toast.makeText(MealListActivity.this, "Geen vegetarische gerechten gevonden", Toast.LENGTH_SHORT);
                    toast.show();
                }
                mealListAdapter = new MealListAdapter(vegetarianMeals, MealListActivity.this);
                mealsRecyclerView.setAdapter(mealListAdapter);
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealListAdapter = new MealListAdapter(meals, MealListActivity.this);
                mealsRecyclerView.setAdapter(mealListAdapter);
                Toast toast = Toast.makeText(MealListActivity.this, "Filter verwijderd", Toast.LENGTH_SHORT);
                toast.show();
            }

        });

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
                //add to database
                MealDatabase database = MealDatabase.getDbInstance(MealListActivity.this);
                MealDAO mealDAO = database.mealDAO();
                mealDAO.nukeTable();
                mealDAO.insertAll(meals);
                Log.d(TAG, "posted to room database");

                    //create adapter
                    Log.d(TAG, "onResponse: " + meals.get(0).getName());
                    mealListAdapter = new MealListAdapter(meals, MealListActivity.this);
                    //implementation
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MealListActivity.this, LinearLayoutManager.VERTICAL, false);
                    //set layout manager
                    mealsRecyclerView.setLayoutManager(linearLayoutManager);
                     System.out.println(mealListAdapter.getItemCount());
                    //set adapter
                    mealsRecyclerView.setAdapter(mealListAdapter);

                Toast toast = Toast.makeText(MealListActivity.this, meals.size() + " gerechten opgeslagen", Toast.LENGTH_SHORT);
                toast.show();
                }

            @Override
            public void onFailure(Call<MealList> call, Throwable t) {
                System.out.println("failed to get response");
                System.out.println(t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
