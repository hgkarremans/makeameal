package com.example.makeameal.Datastorage;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.makeameal.Domain.Meal;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface MealDAO {
    @Insert
    void insert(Meal meal);


    @Query("SELECT * FROM meal")
    LiveData<List<Meal>> getAll();

    @Delete
    void delete (Meal meal);

}
