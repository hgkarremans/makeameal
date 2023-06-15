package com.example.makeameal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MealLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_layout);
        TextView mealName = findViewById(R.id.mealname);
        TextView mealDescription = findViewById(R.id.mealdescription);
        TextView mealPrice = findViewById(R.id.mealprice);
        TextView isVega = findViewById(R.id.isvega);
        TextView isVegan = findViewById(R.id.isvegan);
        TextView isToTakeHome = findViewById(R.id.istotakehome);
        TextView maxAmountOfParticipants = findViewById(R.id.maxamountofparticipants);
        ImageView mealImage = findViewById(R.id.mealimage);
        TextView allergenes = findViewById(R.id.allergenes);
        TextView cookName = findViewById(R.id.name_cook);
        TextView participants = findViewById(R.id.participants);

    }
}