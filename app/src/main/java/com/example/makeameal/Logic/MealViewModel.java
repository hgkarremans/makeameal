package com.example.makeameal.Logic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.makeameal.Domain.Meal;

import java.util.List;

public class MealViewModel extends AndroidViewModel {
    private LiveData<List<Meal>> mealList;
    private MealRepository mealRepository;


    public MealViewModel(Application application) {
        super(application);
        mealRepository = new MealRepository(application);
        mealList = mealRepository.getAllMeals();
    }
    public LiveData<List<Meal>> getAllMeals() {
        return mealList;
    }
    public void insert(Meal meal) {
        mealRepository.insert(meal);
    }
}
