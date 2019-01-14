package com.myoung.android.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.myoung.android.bakingapp.data.RecipeItem;

public class RecipeIngredientWidgetService extends IntentService {

    public static final String ACTION_UPDATE_INGREDIENT_WIDGETS = "com.myoung.android.bakingapp.action.update_ingredient_widgets";
    public static final String EXTRA_RECIPE_ITEM = "com.myoung.android.bakingapp.extra.RECIPE";

    // Constructor
    public RecipeIngredientWidgetService() {
        super("RecipeIngredientService");
    }


    public static void startActionUpdateIngredientWidgets(Context context, RecipeItem recipeItem) {
        Intent intent = new Intent(context, RecipeIngredientWidgetService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENT_WIDGETS);
        intent.putExtra(EXTRA_RECIPE_ITEM, recipeItem);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null) {
            final String action = intent.getAction();

            if(ACTION_UPDATE_INGREDIENT_WIDGETS.equals(action)) {
                RecipeItem recipeItem = intent.getParcelableExtra(EXTRA_RECIPE_ITEM);
                handleActionUpdateIngredientWidgets(recipeItem);
            }
        }
    }

    private void handleActionUpdateIngredientWidgets(RecipeItem recipeItem) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeIngredientWidgetProvider.class));

        RecipeIngredientWidgetProvider.updateIngredientWidgets(this, appWidgetManager, appWidgetIds, recipeItem);
    }

}
