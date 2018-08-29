package com.opus_bd.dostavi.requisition_status;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.opus_bd.dostavi.LoginActivity;
import com.opus_bd.dostavi.adapters.PrStatusAdapter;
import com.opus_bd.dostavi.AppSingleton;
import com.opus_bd.dostavi.Constants;
import com.opus_bd.dostavi.models.PrStatusModel;
import com.opus_bd.dostavi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PrStatusActivity extends AppCompatActivity {
    private ArrayList<PrStatusModel> items = new ArrayList<>();
    RecyclerView recyclerView;
    PrStatusAdapter adapter;
    ProgressDialog progress;
    String requisitionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_pr_status);
        initializeVariables();
        getPrStatusJson();
    }

    private void initializeVariables() {
        recyclerView = findViewById(R.id.pr_status_recycler_view);
        adapter = new PrStatusAdapter(items, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        progress = new ProgressDialog(this);
        progress.setMessage("Please Wait");
        requisitionID = getIntent().getExtras().getString(Constants.REQUISITION_ID);
    }

    private void getPrStatusJson() {
        progress.show();
        String finalURL = Constants.GET_PRA_STATUS_INFO + "?reqID=" + requisitionID;
        Log.d(Constants.LOGTAG, finalURL);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                finalURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        // Log.d(Constants.LOGTAG, response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject tempObject = jsonArray.getJSONObject(i);

                                PrStatusModel model = new PrStatusModel();
                                model.setEmpName(tempObject.getString("EmpName"));
                                model.setAprovalStatus(tempObject.getString("ApvStatus"));
                                model.setEntryTime(tempObject.getString("EntryTime"));
                                model.setNextEmpName(tempObject.getString("NEmpName"));

                                items.add(model);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
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
            Intent intent = new Intent(PrStatusActivity.this, LoginActivity.class);
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
