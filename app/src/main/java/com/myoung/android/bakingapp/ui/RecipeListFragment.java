package com.myoung.android.bakingapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myoung.android.bakingapp.R;
import com.myoung.android.bakingapp.adapter.RecipeListAdapter;
import com.myoung.android.bakingapp.data.RecipeItem;
import com.myoung.android.bakingapp.viewmodel.MainViewModel;
import com.myoung.android.bakingapp.widget.RecipeIngredientWidgetService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment extends Fragment implements RecipeListAdapter.RecipeListAdapterOnItemClickHandler {
    // TAG for logging
    private static final String TAG = RecipeListFragment.class.getSimpleName();

    // View
    @BindView(R.id.rv_recipe_list) RecyclerView recyclerView;

    // Variables
    private MainViewModel viewModel;
    private RecipeListAdapter recipeListAdapter;

    // Constructor
    public RecipeListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        initFragment(rootView);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();

        // load recipes
        if(viewModel.getRecipes().getValue() == null) {
            viewModel.loadRecipes();
        }
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);

        viewModel.getRecipes().observe(this, recipeList -> {
            recipeListAdapter.setRecipeListData(recipeList);
        });
    }

    private void initFragment(View rootView) {
        ButterKnife.bind(this, rootView);

        // setup recyclerView
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recipeListAdapter = new RecipeListAdapter(getContext(), this);

        boolean isTablet = getActivity().getResources().getBoolean(R.bool.is_tablet);

        int spanCount = 1;
        if(isTablet) {
            spanCount = 3;
        }

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recipeListAdapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onRecipeItemClick(RecipeItem recipeItem) {
        RecipeIngredientWidgetService.startActionUpdateIngredientWidgets(getContext(), recipeItem);

        Intent intent = new Intent(getContext(), RecipeDetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, recipeItem);
        startActivity(intent);
    }

}
