package com.example.makeameal.Domain;

public class Ingredient {

    private final String name, imageURL;
    private final boolean isAllergenic;

    public Ingredient(String name, String imageURL, boolean isAllergenic) {
        this.name = name;
        this.imageURL = imageURL;
        this.isAllergenic = isAllergenic;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public boolean isAllergenic() {
        return isAllergenic;
    }
}
