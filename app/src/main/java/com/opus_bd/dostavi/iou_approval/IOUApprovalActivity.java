package com.opus_bd.dostavi.iou_approval;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opus_bd.dostavi.AppSingleton;
import com.opus_bd.dostavi.Constants;
import com.opus_bd.dostavi.R;
import com.opus_bd.dostavi.Utils;
import com.opus_bd.dostavi.adapters.IOUApproveAdapter;
import com.opus_bd.dostavi.models.IOUApprovalModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IOUApprovalActivity extends AppCompatActivity {

    public ArrayList<IOUApprovalModel> items = new ArrayList<>();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    public IOUApproveAdapter iouApproveAdapter;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iouapproval);
        ButterKnife.bind(this);
        initializeVariables();
        getIouApprovalData();
    }

    private void initializeVariables() {
        iouApproveAdapter = new IOUApproveAdapter(items, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(iouApproveAdapter);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
    }

    private void getIouApprovalData() {
        // TODO : Must Change
        String finalURL = Constants.GET_IOU_APPROVAL + "?userName=mamun@bnb.com";
        //String finalURL = Constants.GET_IOU_APPROVAL + "?userName=" + Constants.USER_EMAIL;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                finalURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Utils.showLogcatMessage(response);
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);
                            if (jsonArray.length() > 0) {
                                List<IOUApprovalModel> leaveApprovalModels = Arrays.asList(gson.fromJson(jsonArray.toString(),
                                        IOUApprovalModel[].class));

                                items.addAll(leaveApprovalModels);
                                iouApproveAdapter.notifyDataSetChanged();
                                iouApproveAdapter.notifyDataSetChanged();
                            }
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
