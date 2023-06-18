package com.example.makeameal;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makeameal.Application.NetworkUtils;
import com.example.makeameal.Datastorage.MealDAO;
import com.example.makeameal.Datastorage.MealDatabase;
import com.example.makeameal.Datastorage.MyApi;
import com.example.makeameal.Domain.Meal;

import java.lang.reflect.Array;
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
            MealDatabase database = MealDatabase.getDbInstance(this);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Meal> meals = database.mealDAO().getAll();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MealListActivity.this, LinearLayoutManager.VERTICAL, false);
                            //set layout manager
                            mealsRecyclerView.setLayoutManager(linearLayoutManager);
                            mealsRecyclerView.setAdapter(new MealListAdapter((ArrayList<Meal>) meals, MealListActivity.this));



                        }
                    });
                }
            }).start();

            mealsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            MealListAdapter adapter = new MealListAdapter(meals, MealListActivity.this);
            mealsRecyclerView.setAdapter(adapter);
            Toast toast = Toast.makeText(MealListActivity.this, "Geopend uit opslag (offline)", Toast.LENGTH_SHORT);
            toast.show();
        }
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
//                MealDatabase database1 = MealDatabase.getDbInstance(MealListActivity.this);
//                MealDAO mealDAO = database1.mealDAO();
//                mealDAO.insertAll(meals);
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
