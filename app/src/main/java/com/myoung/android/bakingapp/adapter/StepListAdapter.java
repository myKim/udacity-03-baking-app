package com.myoung.android.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myoung.android.bakingapp.R;
import com.myoung.android.bakingapp.data.StepItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<StepItem> stepList;
    private StepListAdapterOnItemClickHandler itemClickHandler;

    public interface StepListAdapterOnItemClickHandler {
        void onStepItemClick(StepItem stepItem, int position);
    }


    public StepListAdapter(StepListAdapterOnItemClickHandler itemClickHandler) {
        this.itemClickHandler = itemClickHandler;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_step, viewGroup, false);

        return new StepListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        StepItem stepItem = stepList.get(position);

        StepListViewHolder stepListViewHolder = (StepListViewHolder) viewHolder;
        stepListViewHolder.textViewId.setText(stepItem.getId());
        stepListViewHolder.textViewShortDescription.setText(stepItem.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return (stepList == null) ? 0 : stepList.size();
    }


    public void setStepListData(List<StepItem> stepList) {
        this.stepList = stepList;
        notifyDataSetChanged();
    }



    public class StepListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_step_id) TextView textViewId;
        @BindView(R.id.tv_step_short_description) TextView textViewShortDescription;

        public StepListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener((view)->{
                int position = getAdapterPosition();
                StepItem stepItem = stepList.get(position);
                itemClickHandler.onStepItemClick(stepItem, position);
            });
        }
    }

}
