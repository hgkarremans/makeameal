package com.example.makeameal.Datastorage;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import androidx.lifecycle.LiveData;

import com.example.makeameal.Domain.Meal;

import java.util.List;

public class MealViewModel extends AndroidViewModel {
    private MealRepository mealRepository;
    private LiveData<List<Meal>> mealList;


    public MealViewModel(Application application) {
        super(application);
        mealRepository = new MealRepository(application);
        mealList = mealRepository.getMealList();
    }
    LiveData<List<Meal>> getMealList() {
        return mealList;
    }
    public void insert(Meal meal) {
        mealRepository.insert(meal);

    }
}
