package com.example.makeameal.Application;

public class Formatters {
    public static String returnCheckmarkOrX(boolean b){
        if(b){
            return "✔";
        }
        return "❌";
        // returns checkmarks and X
    }
}
