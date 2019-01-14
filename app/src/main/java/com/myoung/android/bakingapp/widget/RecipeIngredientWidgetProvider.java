package com.myoung.android.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.myoung.android.bakingapp.R;
import com.myoung.android.bakingapp.data.RecipeItem;
import com.myoung.android.bakingapp.utils.BakingAppUtils;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, RecipeItem recipeItem) {

        // Construct the RemoteViews object
        RemoteViews views = getIngredientRemoteView(context, recipeItem);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static RemoteViews getIngredientRemoteView(Context context, RecipeItem recipeItem) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredient_widget);
        views.setTextViewText(R.id.tv_appwidget_recipe_name, recipeItem.getName());
        views.setTextViewText(R.id.tv_appwidget_recipe_ingredient, BakingAppUtils.getIngredientString(recipeItem.getIngredients()));

        return views;
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void updateIngredientWidgets(Context context, AppWidgetManager appWidgetManager,
                                               int[] appWidgetIds, RecipeItem recipeItem) {
        for(int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId,recipeItem);
        }
    }

}

