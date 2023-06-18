package com.example.makeameal.Datastorage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.makeameal.Domain.Meal;

import java.util.List;

public class MealRepository {
    private MealDAO mealDAO;
    private LiveData<List<Meal>> mealList;


    public MealRepository(Application application) {
        MealDatabase mealDatabase = MealDatabase.getDbInstance(application);
        mealDAO = mealDatabase.mealDAO();
        mealList = mealDAO.getAll();

    }
    LiveData<List<Meal>> getMealList() {
        return mealList;
    }
    public void insert(Meal meal) {
        new InsertMealAsyncTask(mealDAO).execute(meal);

    }
    private static class InsertMealAsyncTask extends android.os.AsyncTask<Meal, Void, Void> {
        private MealDAO mealDAO;
        private InsertMealAsyncTask(MealDAO mealDAO) {
            this.mealDAO = mealDAO;
        }

        @Override
        protected Void doInBackground(Meal... meals) {
            mealDAO.insert(meals[0]);
            return null;
        }
    }
}
