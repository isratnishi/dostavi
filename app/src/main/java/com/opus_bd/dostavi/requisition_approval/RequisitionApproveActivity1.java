package com.opus_bd.dostavi.requisition_approval;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.opus_bd.dostavi.LoginActivity;
import com.opus_bd.dostavi.adapters.RequisitionApprovalListAdapter;
import com.opus_bd.dostavi.AppSingleton;
import com.opus_bd.dostavi.Constants;
import com.opus_bd.dostavi.models.RequisitionApprovalListModel;
import com.opus_bd.dostavi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RequisitionApproveActivity1 extends AppCompatActivity {
    private ArrayList<RequisitionApprovalListModel> items = new ArrayList<>();
    RecyclerView recyclerView;
    RequisitionApprovalListAdapter requisitionApprovalListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_requisition_list);
        initializeVariables();
        getRequisitionApprovedList();
    }

    private void initializeVariables() {
        recyclerView = findViewById(R.id.requisition_list_recycler_view);
        requisitionApprovalListAdapter = new RequisitionApprovalListAdapter(items, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(requisitionApprovalListAdapter);
        requisitionApprovalListAdapter.notifyDataSetChanged();
    }

    private void getRequisitionApprovedList() {
        String finalURL = Constants.GET_REQUISITION_LIST_FOR_APPROVE + "?Email=" + Constants.USER_EMAIL;
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
                            requisitionApprovalListAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            Intent intent = new Intent(RequisitionApproveActivity1.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        /*if (id == android.R.id.next) {
            finish();
            return true;
        }*/
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
