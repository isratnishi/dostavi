package com.example.opus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.opus.Adapters.ApprovedPraAdapter;
import com.example.opus.Models.ApprovedPraHistoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RequisitionApproveActivity3 extends AppCompatActivity {

    private ArrayList<ApprovedPraHistoryModel> items = new ArrayList<>();
    RecyclerView recyclerView;
    ApprovedPraAdapter praAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisition_approve3);
        initializeVariables();
        getPraApprovedHistory();
    }

    private void initializeVariables() {
        recyclerView = findViewById(R.id.approved_pra_history_recycler_view);
        praAdapter = new ApprovedPraAdapter(items, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(praAdapter);

       /* ApprovedPraHistoryModel model = new ApprovedPraHistoryModel();
        model.setDate("a");
        model.setNextProcessBy("b");
        model.setProcessBy("c");
        model.setRemark("asd");

        items.add(model);

        praAdapter.notifyDataSetChanged();*/


    }

    private void getPraApprovedHistory() {
        // loadStatusProgressbar.setVisibility(View.VISIBLE);
        String maxMasterID = "146257";
        String finalURL = Constants.GET_APPROVED_HISTORY + "?ReqID=" + maxMasterID;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                finalURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(Constants.LOGTAG, response);
                        //loadStatusProgressbar.setVisibility(View.GONE);
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject tempObject = jsonArray.getJSONObject(i);

                                String tempProcessBy = tempObject.getString("ProcesName");
                                String tempDate = tempObject.getString("AprvDate");
                                String tempRemark = tempObject.getString("Remarks");
                                String tempNextProcessBy = tempObject.getString("ApproveName");

                                ApprovedPraHistoryModel historyModel = new ApprovedPraHistoryModel();
                                historyModel.setProcessBy(tempProcessBy);
                                historyModel.setDate(tempDate);
                                historyModel.setRemark(tempRemark);
                                historyModel.setProcessBy(tempNextProcessBy);
                                items.add(historyModel);

                            }
                            praAdapter.notifyDataSetChanged();

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
