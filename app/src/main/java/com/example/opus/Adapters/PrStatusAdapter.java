package com.example.opus.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.opus.Models.PrStatusModel;
import com.example.opus.R;
import com.example.opus.RequisitionEntry.RequisitionEntryActivity2;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class PrStatusAdapter extends RecyclerView.Adapter<PrStatusAdapter.ItemViewHolder> {

    private List<PrStatusModel> itemList;
    private Context context;

    public PrStatusAdapter(List<PrStatusModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public PrStatusAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pr_status_list_view, parent, false);
        return new PrStatusAdapter.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PrStatusAdapter.ItemViewHolder holder, final int position) {
        holder.userNameTextView.setText(itemList.get(position).getEmpName());
        holder.approvalStepsTextView.setText(itemList.get(position).getAprovalStatus());
        holder.approvalTimeTextView.setText(itemList.get(position).getEntryTime());
        holder.nextProcessByTextView.setText(itemList.get(position).getNextEmpName());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView userNameTextView;
        public TextView approvalStepsTextView;
        public TextView approvalTimeTextView;
        public TextView nextProcessByTextView;
        public View view;

        public ItemViewHolder(View view) {
            super(view);
            this.view = view;
            userNameTextView = view.findViewById(R.id.user_name_text_view);
            approvalStepsTextView = view.findViewById(R.id.approval_steps_text_view);
            approvalTimeTextView = view.findViewById(R.id.approval_time_text_view);
            nextProcessByTextView = view.findViewById(R.id.next_process_by_text_view);
        }
    }
}
