package com.example.opus.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.opus.Models.ItemModel;
import com.example.opus.R;
import com.example.opus.RequisitionEntry.RequisitionEntryActivity2;

import java.util.List;

import es.dmoral.toasty.Toasty;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<ItemModel> itemList;
    private Context context;

    public ItemAdapter(List<ItemModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    public interface OnItemLongClickListener {
        public boolean onItemLongClicked(int position);
    }

    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, final int position) {
        holder.itemCode.setText(itemList.get(position).getItemCode());
        holder.itemName.setText(itemList.get(position).getItemName());
        holder.unit.setText(itemList.get(position).getUnit());
        holder.approxPrice.setText(itemList.get(position).getApproxPrice());
        holder.total.setText(itemList.get(position).getTotal());
        holder.quantity.setText(String.valueOf(itemList.get(position).getQuantity()));
        holder.specification.setText(itemList.get(position).getSpecification());

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toasty.info(context, "Item removed", Toast.LENGTH_SHORT, true).show();
                itemList.remove(position);
                notifyDataSetChanged();
                if (itemList.size() == 0)
                    RequisitionEntryActivity2.saveButton.setVisibility(View.GONE);
                return false;
            }
        });
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
        }
    }
}
