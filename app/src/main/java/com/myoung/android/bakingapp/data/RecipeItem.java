package com.myoung.android.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeItem implements Parcelable {
    // Constants
    private static final String JSON_ID = "id";
    private static final String JSON_NAME = "name";
    private static final String JSON_INGREDIENTS = "ingredients";
    private static final String JSON_STEPS = "steps";
    private static final String JSON_SERVINGS = "servings";
    private static final String JSON_IMAGE = "image";


    // Variables
    @SerializedName(JSON_ID)
    private String id;
    @SerializedName(JSON_NAME)
    private String name;
    @SerializedName(JSON_INGREDIENTS)
    private List<IngredientItem> ingredients;
    @SerializedName(JSON_STEPS)
    private List<StepItem> steps;
    @SerializedName(JSON_SERVINGS)
    private String servings;
    @SerializedName(JSON_IMAGE)
    private String imageURL;


    // Constructor
    protected RecipeItem(Parcel in) {
        id = in.readString();
        name = in.readString();
        ingredients = in.createTypedArrayList(IngredientItem.CREATOR);
        steps = in.createTypedArrayList(StepItem.CREATOR);
        servings = in.readString();
        imageURL = in.readString();
    }

    public static final Creator<RecipeItem> CREATOR = new Creator<RecipeItem>() {
        @Override
        public RecipeItem createFromParcel(Parcel in) {
            return new RecipeItem(in);
        }

        @Override
        public RecipeItem[] newArray(int size) {
            return new RecipeItem[size];
        }
    };

    // Getter and Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientItem> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientItem> ingredients) {
        this.ingredients = ingredients;
    }

    public List<StepItem> getSteps() {
        return steps;
    }

    public void setSteps(List<StepItem> steps) {
        this.steps = steps;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
        dest.writeString(servings);
        dest.writeString(imageURL);
    }
}