package com.myoung.android.bakingapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.myoung.android.bakingapp.R;
import com.myoung.android.bakingapp.data.RecipeItem;
import com.myoung.android.bakingapp.data.StepItem;
import com.myoung.android.bakingapp.idlingResource.SimpleIdlingResource;
import com.myoung.android.bakingapp.viewmodel.RecipeDetailViewModel;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.StepItemClickListener {
    // TAG for logging
    private static final String TAG = RecipeDetailActivity.class.getSimpleName();

    // Variables
    private RecipeDetailViewModel viewModel;
    private boolean isTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        initActivity();
    }

    private void initActivity() {
        RecipeItem recipeItem = null;
        if(viewModel != null && viewModel.getRecipe().getValue() != null) {
            // Get recipe data from ViewModel
            recipeItem = viewModel.getRecipe().getValue();
        }
        else {
            // Get recipe data from Intent
            recipeItem = getIntent().getParcelableExtra(Intent.EXTRA_TEXT);
        }

        // Setup ActionBar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.recipe_detail_title);  // "Recipe Detail"
        }

        // Check if the screen has two pane
        isTwoPane = (findViewById(R.id.container_recipe_step_detail) != null);

        // Setup ViewModel
        setupViewModel(recipeItem);
    }

    private void setupViewModel(RecipeItem recipeItem) {
        viewModel = ViewModelProviders.of(this).get(RecipeDetailViewModel.class);

        if(isTwoPane) {
            viewModel.getStepIndex().observe(this, position -> {
                StepItem stepItem = viewModel.getStepItem(position);
                replaceStepDetailFragment(stepItem);
            });
        }

        // Set initial data to ViewModel
        if(viewModel.getRecipe().getValue() == null) {
            viewModel.setRecipe(recipeItem);
            viewModel.setStepIndex(0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStepItemClick(StepItem stepItem, int position) {
        if(isTwoPane) {
            int currentPosition = viewModel.getStepIndex().getValue();
            if(position != currentPosition) {
                viewModel.setStepIndex(position);
            }
        }
        else {
            ArrayList<StepItem> stepList = (ArrayList<StepItem>) viewModel.getRecipe().getValue().getSteps();

            Intent intent = new Intent(this, RecipeStepDetailActivity.class);
            intent.putParcelableArrayListExtra(getString(R.string.key_step_list), stepList);
            intent.putExtra(getString(R.string.key_step_index), position);
            startActivity(intent);
        }
    }

    private void replaceStepDetailFragment(StepItem stepItem) {
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        fragment.setStepItem(stepItem);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container_recipe_step_detail, fragment)
                .commit();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if(fragment instanceof RecipeDetailFragment) {
            RecipeDetailFragment recipeDetailFragment = (RecipeDetailFragment) fragment;
            recipeDetailFragment.setOnStepItemClickListener(this);
        }
    }

    public void onButtonClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.bt_prev:
                viewModel.addStepIndex(-1);
                break;

            case R.id.bt_next:
                viewModel.addStepIndex(1);
                break;
        }
    }

}
