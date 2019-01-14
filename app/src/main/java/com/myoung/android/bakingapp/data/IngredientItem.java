package com.myoung.android.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class IngredientItem implements Parcelable {
    // Constants
    private static final String JSON_QUANTITY = "quantity";
    private static final String JSON_MEASURE = "measure";
    private static final String JSON_INGREDIENT = "ingredient";


    // Variables
    @SerializedName(JSON_QUANTITY)
    private float quantity;
    @SerializedName(JSON_MEASURE)
    private String measure;
    @SerializedName(JSON_INGREDIENT)
    private String ingredient;


    protected IngredientItem(Parcel in) {
        quantity = in.readFloat();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<IngredientItem> CREATOR = new Creator<IngredientItem>() {
        @Override
        public IngredientItem createFromParcel(Parcel in) {
            return new IngredientItem(in);
        }

        @Override
        public IngredientItem[] newArray(int size) {
            return new IngredientItem[size];
        }
    };

    // Getter and Setter
    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }
}
