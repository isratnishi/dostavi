package com.example.opus.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.opus.Models.ApprovedPraHistoryModel;
import com.example.opus.Models.RequisitionApprovalListModel;
import com.example.opus.R;

import java.util.List;

public class RequistionApprovalListAdapter extends RecyclerView.Adapter<RequistionApprovalListAdapter.ItemViewHolder> {

    private List<RequisitionApprovalListModel> itemList;
    private Context context;

    public RequistionApprovalListAdapter(List<RequisitionApprovalListModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }


    @NonNull
    @Override
    public RequistionApprovalListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.requisition_approve_list_view, parent, false);
        return new RequistionApprovalListAdapter.ItemViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull RequistionApprovalListAdapter.ItemViewHolder holder, final int position) {
        holder.requisitionNoTextView.setText(itemList.get(position).getRequisitionNo());
        holder.requisitionDateTextView.setText(itemList.get(position).getRequisitionDate());
        holder.subjectTextView.setText(itemList.get(position).getSubject());
        holder.departmentByTextView.setText(itemList.get(position).getDepartment());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView requisitionNoTextView;
        public TextView requisitionDateTextView;
        public TextView subjectTextView;
        public TextView departmentByTextView;

        public ItemViewHolder(View view) {
            super(view);

            requisitionNoTextView = view.findViewById(R.id.requisition_no_text_view);
            requisitionDateTextView = view.findViewById(R.id.requisition_date_text_view);
            subjectTextView = view.findViewById(R.id.subject_text_view);
            departmentByTextView = view.findViewById(R.id.department_by_text_view);
        }
    }
}
