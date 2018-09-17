package com.opus_bd.dostavi.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opus_bd.dostavi.R;
import com.opus_bd.dostavi.models.IOUItemModel;

import java.util.List;

public class IOUItemAdapter extends RecyclerView.Adapter<IOUItemAdapter.ItemViewHolder> {

    private List<IOUItemModel> itemList;
    private Context context;

    public IOUItemAdapter(List<IOUItemModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public IOUItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.iou_item_list_view, parent, false);
        return new IOUItemAdapter.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IOUItemAdapter.ItemViewHolder holder, final int position) {
        holder.itemNameTextView.setText(itemList.get(position).getItemName());
        holder.qtyTextView.setText(itemList.get(position).getQty().toString());
        holder.cumQtyTextView.setText(itemList.get(position).getTotalProcureQty().toString());
        holder.unitNameTextView.setText(itemList.get(position).getUnitName());
        holder.rateTextView.setText(itemList.get(position).getRate().toString());
        holder.lastPriceTextView.setText(itemList.get(position).getLastRate().toString());
        holder.subTotalTextView.setText(itemList.get(position).getTotalAmn().toString());
        holder.itemSpecificationTextView.setText(itemList.get(position).getItemSpac());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView itemNameTextView;
        public TextView qtyTextView;
        public TextView cumQtyTextView;
        public TextView unitNameTextView;
        public TextView rateTextView;
        public TextView lastPriceTextView;
        public TextView subTotalTextView;
        public TextView itemSpecificationTextView;

        public ItemViewHolder(View view) {
            super(view);
            itemNameTextView = view.findViewById(R.id.item_name_text_view);
            qtyTextView = view.findViewById(R.id.qty_text_view);
            cumQtyTextView = view.findViewById(R.id.cum_qty_text_view);
            unitNameTextView = view.findViewById(R.id.unit_name_text_view);
            rateTextView = view.findViewById(R.id.rate_text_view);
            lastPriceTextView = view.findViewById(R.id.last_price_view);
            subTotalTextView = view.findViewById(R.id.sub_total_text_view);
            itemSpecificationTextView = view.findViewById(R.id.item_specification_text_view);
        }
    }
}
