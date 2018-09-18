package com.opus_bd.dostavi.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opus_bd.dostavi.Constants;
import com.opus_bd.dostavi.R;
import com.opus_bd.dostavi.Utils;
import com.opus_bd.dostavi.custom_dialogs.IOUApproveDialog;
import com.opus_bd.dostavi.custom_dialogs.POApproveDialog;
import com.opus_bd.dostavi.iou_approval.IOUApprovalActivity2;
import com.opus_bd.dostavi.models.POApproveModel;

import java.util.List;

public class POApprovalAdapter extends RecyclerView.Adapter<POApprovalAdapter.ItemViewHolder> {
    private List<POApproveModel> itemList;
    private Context context;

    public POApprovalAdapter(List<POApproveModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public POApprovalAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.po_approve_list_view, parent, false);
        return new POApprovalAdapter.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull POApprovalAdapter.ItemViewHolder holder, final int position) {
        holder.requisitionNoTextView.setText(itemList.get(position).getReqNo());
        holder.requisitionDateTextView.setText(itemList.get(position).getReqDate());
        holder.procurementPersonTextView.setText(itemList.get(position).getBuyerName());
        holder.csNoTextView.setText(itemList.get(position).getCSNo());
        holder.csCreationDateTextView.setText(itemList.get(position).getCSDate());
        holder.finalApproverTextView.setText(itemList.get(position).getFinalApprover());

        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POApproveDialog dialog = new POApproveDialog(context);
                dialog.setPoApproveModel(itemList.get(position));
                //dialog.show();

                Utils.showLogcatMessage(itemList.get(position).getBuyerName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public View rootLayout;
        public TextView requisitionNoTextView;
        public TextView requisitionDateTextView;
        public TextView procurementPersonTextView;
        public TextView csNoTextView;
        public TextView csCreationDateTextView;
        public TextView finalApproverTextView;

        public ItemViewHolder(View view) {
            super(view);
            requisitionNoTextView = view.findViewById(R.id.requisition_no_text_view);
            requisitionDateTextView = view.findViewById(R.id.requisition_date_text_view);
            procurementPersonTextView = view.findViewById(R.id.procurement_person_text_view);
            csNoTextView = view.findViewById(R.id.cs_no_text_view);
            csCreationDateTextView = view.findViewById(R.id.cs_creation_date_text_view);
            finalApproverTextView = view.findViewById(R.id.final_approver_text_view);
            rootLayout = view.findViewById(R.id.root_layout);
        }
    }
}
