package com.example.makeameal.Application;

import androidx.room.TypeConverter;

import com.example.makeameal.Domain.Cook;
import com.example.makeameal.Domain.Person;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converters {
    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
    @TypeConverter
    public static ArrayList<Person> fromString2(String value) {
        Type listType = new TypeToken<List<Person>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromArrayList2(List<Person> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
    @TypeConverter
    public static Cook fromJson(String value) {
        return new Gson().fromJson(value, Cook.class);
    }
    @TypeConverter
    public static String toJson(Cook cook) {
        Gson gson = new Gson();
        return gson.toJson(cook);
    }

}
