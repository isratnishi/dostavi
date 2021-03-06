package com.opus_bd.dostavi.requisition_status;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.opus_bd.dostavi.LoginActivity;
import com.opus_bd.dostavi.Utils;
import com.opus_bd.dostavi.adapters.RequisitionStatusHomeAdapter;
import com.opus_bd.dostavi.AppSingleton;
import com.opus_bd.dostavi.Constants;
import com.opus_bd.dostavi.models.RequisitionStatusHomeModel;
import com.opus_bd.dostavi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class RequisitionStatusHome extends AppCompatActivity {
    private ArrayList<RequisitionStatusHomeModel> items = new ArrayList<>();
    RecyclerView recyclerView;
    RequisitionStatusHomeAdapter adapter;
    EditText searchFromEditText;
    EditText searchToEditText;
    Button search_button;
    ProgressDialog progress;

    Calendar mCurrentDate = Calendar.getInstance();
    int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_requisition_status_home);
        initializeVariables();

        searchFromEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(RequisitionStatusHome.this,
                        new DatePickerDialog.OnDateSetListener() {

                            public void onDateSet(DatePicker datepicker, int
                                    selectedYear, int selectedMonth, int selectedDay) {
                                mCurrentDate.set(Calendar.YEAR, selectedYear);
                                mCurrentDate.set(Calendar.MONTH, selectedMonth);
                                mCurrentDate.set(Calendar.DAY_OF_MONTH, selectedDay);

                                searchFromEditText.setText((selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear);
                            }
                        }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        searchToEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(RequisitionStatusHome.this,
                        new DatePickerDialog.OnDateSetListener() {

                            public void onDateSet(DatePicker datepicker, int
                                    selectedYear, int selectedMonth, int selectedDay) {
                                mCurrentDate.set(Calendar.YEAR, selectedYear);
                                mCurrentDate.set(Calendar.MONTH, selectedMonth);
                                mCurrentDate.set(Calendar.DAY_OF_MONTH, selectedDay);
                                searchToEditText.setText((selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear);
                            }
                        }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRequisitionStatusJson();
            }
        });
    }

    private void initializeVariables() {
        searchFromEditText = findViewById(R.id.requisition_date_edit_text);
        searchToEditText = findViewById(R.id.product_delivery_date_edit_text);
        recyclerView = findViewById(R.id.requisition_status_home_recycler_view);
        search_button = findViewById(R.id.search_button);

        mYear = mCurrentDate.get(Calendar.YEAR);
        mMonth = mCurrentDate.get(Calendar.MONTH);
        mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);

        adapter = new RequisitionStatusHomeAdapter(items, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        progress = new ProgressDialog(this);
        progress.setMessage("Please Wait");

        String tempDate = (mMonth + 1) + "/" + mDay + "/" + mYear;
        searchFromEditText.setText(tempDate);
        searchToEditText.setText(tempDate);
    }

    private void getRequisitionStatusJson() {
        progress.show();
        String fromDate = searchFromEditText.getText().toString();
        String toDate = searchToEditText.getText().toString();
        String finalURL = Constants.GET_REQUISITION_HOME_DATA + "?fromDate=" +
                fromDate + "&toDate=" + toDate +
                "&Search=" +
                "&searchType=&EmailID=" + Constants.USER_EMAIL;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                finalURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject tempObject = jsonArray.getJSONObject(i);

                                RequisitionStatusHomeModel model = new RequisitionStatusHomeModel();
                                model.setRequisitionNo(tempObject.getString("ReqNo"));
                                model.setRequisitionDate(tempObject.getString("ReqDate"));
                                model.setProjectName(tempObject.getString("ProjectName"));
                                model.setPrValue(tempObject.getString("ApproxCost"));
                                model.setCsNO(tempObject.getString("CSNo"));
                                model.setCsValue(tempObject.getString("CSVALUE"));
                                model.setSubject(tempObject.getString("Subject"));
                                model.setRequisitionID(tempObject.getString("RequisitionId"));
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
            Intent intent = new Intent(RequisitionStatusHome.this, LoginActivity.class);
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
