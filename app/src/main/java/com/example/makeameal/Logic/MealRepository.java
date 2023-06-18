package com.example.makeameal.Logic;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.makeameal.Datastorage.MealDAO;
import com.example.makeameal.Datastorage.MealDatabase;
import com.example.makeameal.Domain.Meal;
import java.util.List;

public class MealRepository {
    private MealDAO mealDAO;
    private LiveData<List<Meal>> allMeals;

    MealRepository(Application application) {
        MealDatabase db = MealDatabase.getDbInstance(application);
        mealDAO = db.mealDAO();
        allMeals = mealDAO.getAll();
    }
    public LiveData<List<Meal>> getAllMeals() {
        return allMeals;
    }
    public void insert(Meal meal) {
        new insertAsyncTask(mealDAO).execute(meal);
    }

    private class insertAsyncTask extends AsyncTask<Meal, Void, Void>{
        private MealDAO mAsyncTaskDao;
        public insertAsyncTask(MealDAO mealDAO) {
            mAsyncTaskDao = mealDAO;
        }

        @Override
        protected Void doInBackground(Meal... meals) {
            mAsyncTaskDao.insert(meals[0]);
            return null;
        }
    }
}
