package com.opus_bd.dostavi.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.opus_bd.dostavi.models.ItemModel;
import com.opus_bd.dostavi.requisition_entry.RequisitionEntryActivity2;
import com.opus_bd.dostavi.R;

import java.util.List;

import es.dmoral.toasty.Toasty;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<ItemModel> itemList;
    private Context context;

    public ItemAdapter(List<ItemModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
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
                showAlertDialog(position);
                return true;
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

    private void showAlertDialog(final int position) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context,
                    android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle("Remove Entry")
                .setMessage("Do you want to remove the entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        itemList.remove(position);
                        notifyDataSetChanged();
                        if (itemList.size() == 0)
                            RequisitionEntryActivity2.saveButton.setVisibility(View.GONE);
                        Toasty.info(context, "Item removed", Toast.LENGTH_SHORT, true).show();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
