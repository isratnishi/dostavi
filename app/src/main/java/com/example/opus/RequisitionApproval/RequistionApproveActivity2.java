package com.example.opus.RequisitionApproval;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.opus.Adapters.ItemAdapter;
import com.example.opus.AppSingleton;
import com.example.opus.Constants;
import com.example.opus.Models.ItemModel;
import com.example.opus.Models.RequisitionApprovalListModel;
import com.example.opus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RequistionApproveActivity2 extends AppCompatActivity {

    private ArrayList<ItemModel> items = new ArrayList<>();
    RecyclerView recyclerView;
    ItemAdapter itemAdapter;

    Button nextButton;

    RequisitionApprovalListModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requistion_approve2);
        initializeVariables();
        getRequisitionItem();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequistionApproveActivity2.this, RequisitionApproveActivity3.class);
                intent.putExtra(Constants.REQUISITION_APPROVAL_LIST_MODEL, model);
                startActivity(intent);
            }
        });
    }

    private void initializeVariables() {
        recyclerView = findViewById(R.id.item_recycler_view);
        nextButton = findViewById(R.id.next_button);

        itemAdapter = new ItemAdapter(items, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(itemAdapter);
        model = (RequisitionApprovalListModel) getIntent().getSerializableExtra(Constants.REQUISITION_APPROVAL_LIST_MODEL);

    }

    private void getRequisitionItem() {
        // loadStatusProgressbar.setVisibility(View.VISIBLE);
        String maxMasterID = "146257";
        String finalURL = Constants.GET_REQUISITION_LIST + "?MasterId=" + model.getRequisitionID();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                finalURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //loadStatusProgressbar.setVisibility(View.GONE);
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject tempObject = jsonArray.getJSONObject(i);

                                String tempItemName = tempObject.getString("ItemName");
                                String tempItemCode = tempObject.getString("ItemCode");
                                String tempItemUnit = tempObject.getString("UnitName");
                                String tempItemSpac = tempObject.getString("ItemSpac");
                                String tempCumQty = tempObject.getString("TotalProcureQty");
                                String tempReqQty = tempObject.getString("ReqQTY");
                                String tempApproxCost = tempObject.getString("ApproxCost");
                                String tempLastRate = tempObject.getString("LastRate");

                                double tempTotal = 0;
                                try {
                                    tempTotal = Double.parseDouble(tempCumQty) * Double.parseDouble(tempApproxCost);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                ItemModel itemModel = new ItemModel();
                                itemModel.setItemName(tempItemName);
                                itemModel.setItemCode(tempItemCode);
                                itemModel.setUnit(tempItemUnit);
                                itemModel.setApproxPrice(tempApproxCost);
                                itemModel.setSpecification(tempItemSpac);
                                itemModel.setQuantity(Double.parseDouble(tempCumQty));
                                itemModel.setLastRate(tempLastRate);
                                itemModel.setTotal(String.valueOf(String.valueOf(tempTotal)));

                                items.add(itemModel);

                            }
                            itemAdapter.notifyDataSetChanged();

                            //Log.d(Constants.LOGTAG, response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        AppSingleton.getInstance(getApplicationContext())
                .addToRequestQueue(stringRequest, Constants.REQUEST_TAG);

    }
}
