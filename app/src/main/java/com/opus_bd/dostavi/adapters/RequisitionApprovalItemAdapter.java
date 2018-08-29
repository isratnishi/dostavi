package com.opus_bd.dostavi.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opus_bd.dostavi.models.ItemModel;
import com.opus_bd.dostavi.R;

import java.util.List;

public class RequisitionApprovalItemAdapter extends RecyclerView.Adapter<RequisitionApprovalItemAdapter.ItemViewHolder> {
    private List<ItemModel> itemList;
    private Context context;

    public RequisitionApprovalItemAdapter(List<ItemModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public RequisitionApprovalItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.requisition_approve1_listview, parent, false);
        return new RequisitionApprovalItemAdapter.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RequisitionApprovalItemAdapter.ItemViewHolder holder, final int position) {
        holder.itemCode.setText(itemList.get(position).getItemID());
        holder.itemName.setText(itemList.get(position).getItemName());
        holder.unit.setText(itemList.get(position).getUnit());
        holder.approxPrice.setText(itemList.get(position).getApproxPrice());
        holder.total.setText(itemList.get(position).getTotal());
        holder.quantity.setText(String.valueOf(itemList.get(position).getQuantity()));
        holder.specification.setText(itemList.get(position).getSpecification());
        holder.requiredQuantity.setText(String.valueOf(itemList.get(position).getRequiredQuantity()));
        holder.lastPrice.setText(itemList.get(position).getLastRate());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView itemCode;
        public TextView itemName;
        public TextView unit;
        public TextView approxPrice;
        public TextView total;
        public TextView requiredQuantity;
        public TextView lastPrice;
        public TextView quantity;
        public TextView specification;
        public View view;

        public ItemViewHolder(View view) {
            super(view);
            this.view = view;
            itemCode = view.findViewById(R.id.item_code_text_view);
            itemName = view.findViewById(R.id.item_name_text_view);
            unit = view.findViewById(R.id.unit_text_view);
            approxPrice = view.findViewById(R.id.approx_price_text_view);
            total = view.findViewById(R.id.total_text_view);
            quantity = view.findViewById(R.id.quantity_text_view);
            specification = view.findViewById(R.id.specification_text_view);
            requiredQuantity = view.findViewById(R.id.req_qty_text_view);
            lastPrice = view.findViewById(R.id.last_price_text_view);
        }
    }
}
