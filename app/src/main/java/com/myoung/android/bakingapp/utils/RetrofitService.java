package com.myoung.android.bakingapp.utils;

import com.myoung.android.bakingapp.data.RecipeItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitService {

    @GET("59121517_baking/baking.json")
    Call<List<RecipeItem>> getRecipeList();

}
