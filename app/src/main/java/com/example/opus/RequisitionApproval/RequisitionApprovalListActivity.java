package com.example.opus.RequisitionApproval;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.opus.Adapters.RequistionApprovalListAdapter;
import com.example.opus.AppSingleton;
import com.example.opus.Constants;
import com.example.opus.Models.RequisitionApprovalListModel;
import com.example.opus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        getRequisitionApprovedList();

    }

    private void initializeVariables() {
        recyclerView = findViewById(R.id.requisition_list_recycler_view);
        requistionApprovalListAdapter = new RequistionApprovalListAdapter(items, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(requistionApprovalListAdapter);
        requistionApprovalListAdapter.notifyDataSetChanged();
    }

    private void getRequisitionApprovedList() {
        String email = "mamun@bnb.com";
        String finalURL = Constants.GET_REQUISITION_LIST_FOR_APPROVE + "?Email=" + email;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                finalURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject tempObject = jsonArray.getJSONObject(i);

                                String tempReqNO = tempObject.getString("ReqNo");
                                String tempReqDate = tempObject.getString("ReqDate");
                                String tempReqSubject = tempObject.getString("ReqDept");
                                String tempReqDepartment = tempObject.getString("Subject");
                                String tempReqID = tempObject.getString("RequisitionId");
                                String tempProjectID = tempObject.getString("ProjectId");

                                RequisitionApprovalListModel listModel = new RequisitionApprovalListModel();
                                listModel.setRequisitionNo(tempReqNO);
                                listModel.setRequisitionDate(tempReqDate);
                                listModel.setSubject(tempReqSubject);
                                listModel.setDepartment(tempReqDepartment);
                                listModel.setRequisitionID(tempReqID);
                                listModel.setProjectID(tempProjectID);
                                items.add(listModel);

                            }
                            requistionApprovalListAdapter.notifyDataSetChanged();

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
