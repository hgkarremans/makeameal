package com.example.makeameal.Datastorage;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.makeameal.Application.Converters;
import com.example.makeameal.Domain.Meal;

@Database(entities = {Meal.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class MealDatabase extends RoomDatabase {
    public abstract MealDAO mealDAO();
    private static MealDatabase INSTANCE;
    public static MealDatabase getDbInstance (Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MealDatabase.class, "meal_database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}
