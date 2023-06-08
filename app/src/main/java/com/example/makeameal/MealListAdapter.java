package com.example.makeameal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.makeameal.Domain.Meal;

import java.util.ArrayList;

public class MealListAdapter extends RecyclerView.Adapter<MealListAdapter.MealViewHolder>{
    private ArrayList<Meal> meals = new ArrayList<>();
    private LayoutInflater mInflater;
    private final String TAG = Meal.class.getSimpleName();

    public MealListAdapter(ArrayList<Meal> meals, Context context) {
        this.meals = meals;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View mItemView = mInflater.inflate(R.layout.meal_list_item, parent, false);
        return new MealViewHolder(mItemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder mealViewHolder, int position) {
        Log.d(TAG, "onBindViewHolder nr " + position);
        Meal meal = meals.get(position);

        mealViewHolder.name.setText(meal.getName());
        mealViewHolder.description.setText(meal.getDescription());
//        mealViewHolder.price.setText("" + meal.getPrice());
    }
    public ArrayList<Meal> setMeals() {
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


        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.meal_list_item_name);
            this.description = itemView.findViewById(R.id.meal_list_item_description);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "ViewHolder" + getAdapterPosition()+ "clicked");
        }
    }
}
