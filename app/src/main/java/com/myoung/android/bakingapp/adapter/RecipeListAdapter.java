package com.myoung.android.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myoung.android.bakingapp.R;
import com.myoung.android.bakingapp.data.RecipeItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // TAG for logging
    private static final String TAG = RecipeListAdapter.class.getSimpleName();


    // Variables
    private Context context;
    private List<RecipeItem> recipeList;
    private RecipeListAdapterOnItemClickHandler itemClickHandler;

    // Constructor
    public RecipeListAdapter(Context context, RecipeListAdapterOnItemClickHandler itemClickHandler) {
        this.context = context;
        this.itemClickHandler = itemClickHandler;
    }


    public void setRecipeListData(List<RecipeItem> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutIdForItem = R.layout.item_recipe;
        boolean shouldAttatchToParentImmediately = false;

        View view = null;
        view = inflater.inflate(layoutIdForItem, viewGroup, shouldAttatchToParentImmediately);

        return new RecipeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        RecipeItem recipeItem = recipeList.get(position);
        String recipeId = recipeItem.getId();
        String recipeName = recipeItem.getName();
        String recipeImageUrl = recipeItem.getImageURL();

        RecipeListViewHolder recipeViewHoler = (RecipeListViewHolder) viewHolder;

        recipeViewHoler.textViewId.setText(recipeId);
        recipeViewHoler.textViewName.setText(recipeName);
        recipeViewHoler.textViewImageUrl.setText(recipeImageUrl);
    }

    @Override
    public int getItemCount() {
        return (recipeList == null) ? 0 : recipeList.size();
    }



    // Interface
    public interface RecipeListAdapterOnItemClickHandler {
        void onRecipeItemClick(RecipeItem recipeItem);
    }

    // ViewHolder
    public class RecipeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_recipe_id) TextView textViewId;
        @BindView(R.id.tv_recipe_name) TextView textViewName;
        @BindView(R.id.tv_recipe_image_url) TextView textViewImageUrl;

        public RecipeListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            RecipeItem recipeItem = recipeList.get(position);
            itemClickHandler.onRecipeItemClick(recipeItem);
        }
    }

}
