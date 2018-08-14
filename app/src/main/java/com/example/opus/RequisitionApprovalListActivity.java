package com.example.opus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.opus.Adapters.ApprovedPraAdapter;
import com.example.opus.Adapters.RequistionApprovalListAdapter;
import com.example.opus.Models.ApprovedPraHistoryModel;
import com.example.opus.Models.RequisitionApprovalListModel;

import java.util.ArrayList;

public class RequisitionApprovalListActivity extends AppCompatActivity {

    private ArrayList<RequisitionApprovalListModel> items = new ArrayList<>();
    RecyclerView recyclerView;
    RequistionApprovalListAdapter requistionApprovalListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisition_list);
        initializeVariables();
    }

    private void initializeVariables() {
        recyclerView = findViewById(R.id.requisition_list_recycler_view);
        requistionApprovalListAdapter = new RequistionApprovalListAdapter(items, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(requistionApprovalListAdapter);

        RequisitionApprovalListModel model = new RequisitionApprovalListModel();
        model.setRequisitionNo("123");
        model.setRequisitionDate("b");
        model.setDepartment("c");
        model.setSubject("asd");

        items.add(model);

        requistionApprovalListAdapter.notifyDataSetChanged();


    }
}
