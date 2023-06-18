package com.example.makeameal.Presentation;

import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makeameal.Domain.Meal;
import com.example.makeameal.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MealListAdapter extends RecyclerView.Adapter<MealListAdapter.MealViewHolder>{
    private ArrayList<Meal> meals = new ArrayList<>();
    private Context context;

    private final String TAG = Meal.class.getSimpleName();

    public MealListAdapter(ArrayList<Meal> meals, Context context) {
        this.meals = meals;

    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_list_item, parent, false);
        return new MealViewHolder(mItemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder mealViewHolder, int position) {
        Log.d(TAG, "onBindViewHolder nr " + position);
        Meal meal = meals.get(position);

        //set data in textview
        mealViewHolder.name.setText(meal.getName());
        mealViewHolder.description.setText(meal.getDescription());
        mealViewHolder.price.setText("Price: €" + meal.getPrice());

        //set image
        Picasso.get()
                .load(meal.getImageUrl())
                .into(mealViewHolder.image);



    }
    public ArrayList<Meal> setMeals() {
        Log.d(TAG, "setMeals");
        Log.d(TAG, "meals: " + meals);
        return meals;
    }



    @Override
    public int getItemCount() {
        return meals.size();
    }
    class MealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView description;
        TextView price;
        ImageView image;



        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.meal_list_item_name);
            this.description = itemView.findViewById(R.id.meal_list_item_description);
            this.price = itemView.findViewById(R.id.meal_list_item_price);

            this.image = itemView.findViewById(R.id.meal_list_item_imageView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "ViewHolder " + getAdapterPosition()+ " clicked");
            Intent intent = new Intent(view.getContext(), MealLayout.class);
            intent.putExtra("meal",  meals.get(getAdapterPosition()));
            view.getContext().startActivity(intent);

        }

    }
}
