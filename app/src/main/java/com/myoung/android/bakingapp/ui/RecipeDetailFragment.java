package com.myoung.android.bakingapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myoung.android.bakingapp.R;
import com.myoung.android.bakingapp.adapter.StepListAdapter;
import com.myoung.android.bakingapp.data.IngredientItem;
import com.myoung.android.bakingapp.data.RecipeItem;
import com.myoung.android.bakingapp.data.StepItem;
import com.myoung.android.bakingapp.utils.BakingAppUtils;
import com.myoung.android.bakingapp.viewmodel.RecipeDetailViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment implements StepListAdapter.StepListAdapterOnItemClickHandler {
    // TAG for logging
    private static final String TAG = RecipeDetailFragment.class.getSimpleName();

    // View
    @BindView(R.id.tv_ingredients_contents) TextView textViewIngredientsContents;
    @BindView(R.id.rv_step_list) RecyclerView recyclerView;

    // Variables
    private RecipeDetailViewModel viewModel;
    private StepListAdapter stepListAdapter;
    private StepItemClickListener stepItemClickListener;

    public interface StepItemClickListener {
        void onStepItemClick(StepItem stepItem, int position);
    }

    // Constructor
    public RecipeDetailFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        initFragment(rootView);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupViewModel();
    }

    private void initFragment(View rootView) {
        ButterKnife.bind(this, rootView);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        stepListAdapter = new StepListAdapter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(stepListAdapter);
        recyclerView.setHasFixedSize(true);
    }

    public void setupViewModel() {
        viewModel = ViewModelProviders.of(getActivity()).get(RecipeDetailViewModel.class);

        viewModel.getRecipe().observe(this, recipeItem -> {
            stepListAdapter.setStepListData(recipeItem.getSteps());
            setIngredients(recipeItem.getIngredients());
        });

    }

    private void setIngredients(List<IngredientItem> ingredients) {
        String ingredientString = BakingAppUtils.getIngredientString(ingredients);

        textViewIngredientsContents.setText(ingredientString);
    }


    @Override
    public void onStepItemClick(StepItem stepItem, int position) {
        if(stepItemClickListener == null) {
            return ;
        }
        stepItemClickListener.onStepItemClick(stepItem, position);
    }

    public void setOnStepItemClickListener(StepItemClickListener listener) {
        stepItemClickListener = listener;
    }

}
