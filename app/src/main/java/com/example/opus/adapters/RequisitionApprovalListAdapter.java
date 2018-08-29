package com.example.opus.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.opus.Constants;
import com.example.opus.models.RequisitionApprovalListModel;
import com.example.opus.R;
import com.example.opus.requisition_approval.RequisitionApproveActivity2;

import java.util.List;

public class RequisitionApprovalListAdapter extends RecyclerView.Adapter<RequisitionApprovalListAdapter.ItemViewHolder> {

    private List<RequisitionApprovalListModel> itemList;
    private Context context;

    public RequisitionApprovalListAdapter(List<RequisitionApprovalListModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }


    @NonNull
    @Override
    public RequisitionApprovalListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.requisition_approve_list_view, parent, false);
        return new RequisitionApprovalListAdapter.ItemViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull RequisitionApprovalListAdapter.ItemViewHolder holder, final int position) {
        holder.requisitionNoTextView.setText(itemList.get(position).getRequisitionNo());
        holder.requisitionDateTextView.setText(itemList.get(position).getRequisitionDate());
        holder.subjectTextView.setText(itemList.get(position).getSubject());
        holder.departmentByTextView.setText(itemList.get(position).getDepartment());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RequisitionApproveActivity2.class);
                intent.putExtra(Constants.REQUISITION_APPROVAL_LIST_MODEL, itemList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout linearLayout;
        public TextView requisitionNoTextView;
        public TextView requisitionDateTextView;
        public TextView subjectTextView;
        public TextView departmentByTextView;

        public ItemViewHolder(View view) {
            super(view);

            linearLayout = view.findViewById(R.id.requisition_approve_list_layout);
            requisitionNoTextView = view.findViewById(R.id.requisition_no_text_view);
            requisitionDateTextView = view.findViewById(R.id.requisition_date_text_view);
            subjectTextView = view.findViewById(R.id.subject_text_view);
            departmentByTextView = view.findViewById(R.id.department_by_text_view);
        }
    }
}
