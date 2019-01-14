package com.myoung.android.bakingapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.myoung.android.bakingapp.data.RecipeItem;
import com.myoung.android.bakingapp.data.StepItem;

import java.util.List;

public class RecipeDetailViewModel extends ViewModel {

    // Variables
    private MutableLiveData<RecipeItem> recipe;
    private MutableLiveData<Integer> stepIndex;


    // Constructor
    public RecipeDetailViewModel() {

    }


    public LiveData<RecipeItem> getRecipe() {
        return (recipe == null) ? (recipe = new MutableLiveData<>()) : recipe;
    }

    public void setRecipe(RecipeItem recipeItem) {
        if(recipe == null) {
            recipe = new MutableLiveData<>();
        }
        recipe.setValue(recipeItem);
    }

    public LiveData<Integer> getStepIndex() {
        return (stepIndex == null) ? (stepIndex = new MutableLiveData<>()) : stepIndex;
    }

    public void setStepIndex(int index) {
        if(stepIndex == null) {
            stepIndex = new MutableLiveData<>();
        }
        stepIndex.setValue(index);
    }

    public StepItem getStepItem(int position) {
        if(recipe.getValue() == null) {
            return null;
        }
        List<StepItem> steps = recipe.getValue().getSteps();
        return steps == null ? null : steps.get(position);
    }

    public boolean addStepIndex(int n) {
        if(recipe == null) {
            return false;
        }

        List<StepItem> steps = recipe.getValue().getSteps();
        if(steps == null) {
            return false;
        }

        int index = stepIndex.getValue() + n;
        if(0<=index && index<steps.size()) {
            stepIndex.setValue(index);
            return true;
        }
        else {
            return false;
        }
    }

}
