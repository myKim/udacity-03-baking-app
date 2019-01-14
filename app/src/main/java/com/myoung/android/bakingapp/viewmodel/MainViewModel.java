package com.myoung.android.bakingapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.myoung.android.bakingapp.data.RecipeItem;
import com.myoung.android.bakingapp.utils.RetrofitService;
import com.myoung.android.bakingapp.utils.RetrofitUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<RecipeItem>> recipes;

    // Constructor
    public MainViewModel() {

    }

    // Load data
    public void loadRecipes() {
        RetrofitService service = RetrofitUtils.getInstance().getService();

        Call<List<RecipeItem>> call = service.getRecipeList();
        call.enqueue(new Callback<List<RecipeItem>>() {
            @Override
            public void onResponse(Call<List<RecipeItem>> call, Response<List<RecipeItem>> response) {
                if(!response.isSuccessful()) {
                    return;
                }

                List<RecipeItem> recipeList = response.body();
                recipes.setValue(recipeList);
            }

            @Override
            public void onFailure(Call<List<RecipeItem>> call, Throwable t) {

            }
        });
    }

    // Getter
    public LiveData<List<RecipeItem>> getRecipes() {
        return (recipes == null) ? recipes = new MutableLiveData<>() : recipes;
    }

}
