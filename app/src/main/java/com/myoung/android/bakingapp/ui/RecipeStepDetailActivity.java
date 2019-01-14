package com.myoung.android.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.myoung.android.bakingapp.R;
import com.myoung.android.bakingapp.data.StepItem;

import java.util.List;

public class RecipeStepDetailActivity extends AppCompatActivity {
    // TAG for logging
    private static final String TAG = RecipeStepDetailActivity.class.getSimpleName();

    // Constants
    private static final String KEY_STEP_INDEX = "key_step_index";

    // Variables
    private List<StepItem> stepList;
    private int stepIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);
        initActivity(savedInstanceState);
    }

    private void initActivity(Bundle savedInstanceState) {
        // Get recipe step data from Intent
        stepList = getIntent().getParcelableArrayListExtra(getString(R.string.key_step_list));
        stepIndex = getIntent().getIntExtra(getString(R.string.key_step_index), 0);

        if(savedInstanceState != null) {
            stepIndex = savedInstanceState.getInt(KEY_STEP_INDEX);
        }

        StepItem stepItem = stepList.get(stepIndex);

        // Setup ActionBar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.recipe_step_detail_title);
        }

        replaceStepDetailFragment(stepItem);
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

    public void onButtonClick(View view) {
        int id = view.getId();
        switch(id) {
            case R.id.bt_prev:
                if(stepIndex > 0) {
                    replaceStepDetailFragment(stepList.get(--stepIndex));
                }
                return;

            case R.id.bt_next:
                if(stepIndex < stepList.size()-1) {
                    replaceStepDetailFragment(stepList.get(++stepIndex));
                }
                return;
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_STEP_INDEX, stepIndex);
    }
}
