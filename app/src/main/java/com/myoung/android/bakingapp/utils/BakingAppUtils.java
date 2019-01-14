package com.myoung.android.bakingapp.utils;

import com.myoung.android.bakingapp.data.IngredientItem;

import java.util.List;

public class BakingAppUtils {

    public static String getIngredientString(List<IngredientItem> ingredients) {
        StringBuilder sb = new StringBuilder();
        for(IngredientItem item : ingredients) {
            sb.append(item.getQuantity())
                    .append(" ")
                    .append(item.getMeasure())
                    .append("   ")
                    .append(item.getIngredient())
                    .append("\n");
        }

        return sb.toString();
    }

}
