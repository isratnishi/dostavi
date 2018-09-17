package com.opus_bd.dostavi.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opus_bd.dostavi.Constants;
import com.opus_bd.dostavi.R;
import com.opus_bd.dostavi.iou_approval.IOUApprovalActivity2;
import com.opus_bd.dostavi.models.IOUApprovalModel;

import java.util.List;

public class IOUApproveAdapter extends RecyclerView.Adapter<IOUApproveAdapter.ItemViewHolder> {

    private List<IOUApprovalModel> itemList;
    private Context context;

    public IOUApproveAdapter(List<IOUApprovalModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public IOUApproveAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.iou_approval_list_view, parent, false);
        return new IOUApproveAdapter.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IOUApproveAdapter.ItemViewHolder holder, final int position) {
        holder.iOUDateTextView.setText(itemList.get(position).getIOUDate());
        holder.iOUNoTextView.setText(itemList.get(position).getIOUNo());
        holder.deliveryDateTextView.setText(itemList.get(position).getTargetDate());
        holder.iOUValueTextView.setText(itemList.get(position).getIOUValue().toString());
        holder.prNoTextView.setText(itemList.get(position).getPrIOUNo());
        holder.statusTextView.setText(itemList.get(position).getStatusDesc());

        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, IOUApprovalActivity2.class);
                intent.putExtra(Constants.MASTER_ID, itemList.get(position).getID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public View rootLayout;
        public TextView iOUDateTextView;
        public TextView iOUNoTextView;
        public TextView deliveryDateTextView;
        public TextView iOUValueTextView;
        public TextView prNoTextView;
        public TextView statusTextView;

        public ItemViewHolder(View view) {
            super(view);
            iOUDateTextView = view.findViewById(R.id.iou_date_text_view);
            iOUNoTextView = view.findViewById(R.id.iou_no_text_view);
            deliveryDateTextView = view.findViewById(R.id.delivery_date_text_view);
            iOUValueTextView = view.findViewById(R.id.iou_value_text_view);
            prNoTextView = view.findViewById(R.id.pr_no_text_view);
            statusTextView = view.findViewById(R.id.status_text_view);
            rootLayout = view.findViewById(R.id.root_layout);
        }
    }
}
