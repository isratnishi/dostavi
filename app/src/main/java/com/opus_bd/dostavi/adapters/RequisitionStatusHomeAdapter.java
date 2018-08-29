package com.opus_bd.dostavi.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opus_bd.dostavi.Constants;
import com.opus_bd.dostavi.models.RequisitionStatusHomeModel;
import com.opus_bd.dostavi.R;
import com.opus_bd.dostavi.requisition_status.PrStatusActivity;

import java.util.List;

public class RequisitionStatusHomeAdapter extends RecyclerView.Adapter<RequisitionStatusHomeAdapter.ItemViewHolder> {

    private List<RequisitionStatusHomeModel> itemList;
    private Context context;

    public RequisitionStatusHomeAdapter(List<RequisitionStatusHomeModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }


    @NonNull
    @Override
    public RequisitionStatusHomeAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.requisition_status_home_list_view, parent, false);
        return new RequisitionStatusHomeAdapter.ItemViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull RequisitionStatusHomeAdapter.ItemViewHolder holder, final int position) {
        holder.requisitionNoTextView.setText(itemList.get(position).getRequisitionNo());
        holder.reqDateTextView.setText(itemList.get(position).getRequisitionDate());
        holder.projectNameTextView.setText(itemList.get(position).getProjectName());
        holder.prValue.setText(itemList.get(position).getPrValue());
        holder.csNoTextView.setText(itemList.get(position).getPrValue());
        holder.csValueTextView.setText(itemList.get(position).getCsNO());
        holder.subjectTextView.setText(itemList.get(position).getSubject());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PrStatusActivity.class);
                intent.putExtra(Constants.REQUISITION_ID, itemList.get(position).getRequisitionID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView requisitionNoTextView;
        public TextView reqDateTextView;
        public TextView projectNameTextView;
        public TextView prValue;
        public TextView csNoTextView;
        public TextView csValueTextView;
        public TextView subjectTextView;
        public View view;

        public ItemViewHolder(View view) {
            super(view);
            this.view = view;
            requisitionNoTextView = view.findViewById(R.id.requisition_no_text_view);
            reqDateTextView = view.findViewById(R.id.requisition_date_text_view);
            projectNameTextView = view.findViewById(R.id.project_name_text_view);
            prValue = view.findViewById(R.id.pr_value_text_view);
            csNoTextView = view.findViewById(R.id.cs_no_text_view);
            csValueTextView = view.findViewById(R.id.cs_value_text_view);
            subjectTextView = view.findViewById(R.id.subject_text_view);
        }
    }
}
