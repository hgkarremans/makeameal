package com.example.makeameal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.makeameal.Domain.Meal;

import java.util.ArrayList;

public class MealListActivity extends AppCompatActivity implements MealTask.RandomMealListenener{
    private ArrayList<Meal> meals;
    private RecyclerView mealsRecyclerView;
    private MealListAdapter mealListAdapter;
    private String mealUrl = "https://shareameal-api.herokuapp.com/api/meal/";
    private static final String TAG = MealListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);
        Log.d(TAG, "Task aanmaken en starten");
        MealTask task = new MealTask(this);

        task.execute(mealUrl);
        Log.d(TAG, "Task gestart");
        this.meals = mealListAdapter.setMeals();
        //de code kapt er mee bij het aanmaken van de lijst met meals
        Log.d(TAG, "werkt die nog?");
        Log.d(TAG, "Meals = " + meals);
        Log.d(TAG, "ConsumeList created, amount = " + meals.size());

        this.mealsRecyclerView = findViewById(R.id.meals_list);
        this.mealListAdapter = new MealListAdapter(meals, this);

        this.mealsRecyclerView.setAdapter(mealListAdapter);
        this.mealsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void onMealAvailable(ArrayList<Meal> meals) {
        Log.d(TAG, meals.size() + " meals ontvangen");
        Log.d(TAG, meals.get(0).toString());
    }
}