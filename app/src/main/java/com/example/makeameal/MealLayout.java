package com.example.makeameal;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.makeameal.Application.Formatters;
import com.example.makeameal.Domain.Meal;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Objects;

public class MealLayout extends AppCompatActivity {
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        SpannableString spannableString = new SpannableString("Gerechten");
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(spannableString);
        Drawable drawable = getResources().getDrawable(R.drawable.baseline_arrow_back_24);

        getSupportActionBar().setHomeAsUpIndicator(drawable);
        if (getIntent().getExtras() != null) {
            Meal passedMeal = (Meal) getIntent().getSerializableExtra("meal");

            setContentView(R.layout.activity_meal_layout);
            TextView mealName = findViewById(R.id.mealname);
            TextView mealDescription = findViewById(R.id.mealdescription);
            TextView mealPrice = findViewById(R.id.mealprice);
            TextView isVega = findViewById(R.id.isvega);
            TextView isVegan = findViewById(R.id.isvegan);
            TextView isToTakeHome = findViewById(R.id.istotakehome);
            ImageView mealImage = findViewById(R.id.mealimage);
            TextView allergenes = findViewById(R.id.allergenes);
            TextView cookName = findViewById(R.id.name_cook);
            TextView participants = findViewById(R.id.participants);
        mealName.setText(passedMeal.getName());
        mealDescription.setText(passedMeal.getDescription());
        mealPrice.setText("Prijs €" + passedMeal.getPrice());
        isVega.setText("Vega " + Formatters.returnCheckmarkOrX(passedMeal.getVega()));
        isVegan.setText("Vegan " + Formatters.returnCheckmarkOrX(passedMeal.getVegan()));
        isToTakeHome.setText("Mee naar huis te nemen " + Formatters.returnCheckmarkOrX(passedMeal.getToTakeHome()));
        if (passedMeal.getAllergenes().size() == 0)
            allergenes.setText("Allergenen: Geen allergenen");
        else {

            StringBuilder text = new StringBuilder("Allergenen: ");
            for (String a : passedMeal.getAllergenes()) {
                text.append(" ").append(a );
            }
            allergenes.setText(text);
        }
        cookName.setText("Kok: " + passedMeal.getCook().getFirstName() + " " + passedMeal.getCook().getLastName());
        participants.setText("Ingeschreven " + passedMeal.getParticipants().stream().count() + "/" + passedMeal.getMaxAmountOfParticipants());
            Picasso.get().load(passedMeal.getImageUrl()).into(mealImage);

        }
    }
}