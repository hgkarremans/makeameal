package com.example.makeameal.Datastorage;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.makeameal.Domain.Meal;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MealDAO {
    @Insert
    void insertAll(ArrayList<Meal> meals);


    @Query("SELECT * FROM meal")
    List<Meal> getAll();

    @Query("DELETE FROM meal")
    public void nukeTable();

    @Delete
    void delete (Meal meal);

}
