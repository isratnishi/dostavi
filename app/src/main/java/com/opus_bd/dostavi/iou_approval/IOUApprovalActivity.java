package com.opus_bd.dostavi.iou_approval;

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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opus_bd.dostavi.AppSingleton;
import com.opus_bd.dostavi.Constants;
import com.opus_bd.dostavi.HomeActivity;
import com.opus_bd.dostavi.LoginActivity;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        String finalURL = Constants.GET_IOU_APPROVAL + "?userName=" + Constants.USER_EMAIL;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                finalURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            Intent intent = new Intent(IOUApprovalActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
