package com.example.opus.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.opus.Models.ApprovedPraHistoryModel;
import com.example.opus.R;

import java.util.List;

public class ApprovedPraAdapter extends RecyclerView.Adapter<ApprovedPraAdapter.ItemViewHolder> {
    private List<ApprovedPraHistoryModel> itemList;
    private Context context;

    public ApprovedPraAdapter(List<ApprovedPraHistoryModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ApprovedPraAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.approved_pra_history_list_view, parent, false);
        return new ApprovedPraAdapter.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovedPraAdapter.ItemViewHolder holder, final int position) {
        holder.processByTextView.setText(itemList.get(position).getProcessBy());
        holder.dateTextView.setText(itemList.get(position).getDate());
        holder.remarkTextView.setText(itemList.get(position).getRemark());
        holder.nextProcessByTextView.setText(itemList.get(position).getNextProcessBy());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView processByTextView;
        public TextView dateTextView;
        public TextView remarkTextView;
        public TextView nextProcessByTextView;

        public ItemViewHolder(View view) {
            super(view);
            processByTextView = view.findViewById(R.id.process_by_text_view);
            dateTextView = view.findViewById(R.id.date_text_view);
            remarkTextView = view.findViewById(R.id.remark_text_view);
            nextProcessByTextView = view.findViewById(R.id.next_process_by_text_view);
        }
    }
}
