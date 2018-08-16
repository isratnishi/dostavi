package com.example.opus;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.opus.Adapters.ApprovedPraAdapter;
import com.example.opus.Adapters.RequisitionStatusHomeAdapter;
import com.example.opus.Models.ApprovedPraHistoryModel;
import com.example.opus.Models.RequisitionStatusHomeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class RequisitionStatusHome extends AppCompatActivity {

    private ArrayList<RequisitionStatusHomeModel> items = new ArrayList<>();
    RecyclerView recyclerView;
    RequisitionStatusHomeAdapter adapter;
    String selectedType;

    Spinner selectedTypeSpinner;
    EditText searchFromEditText;
    EditText searchToEditText;
    Button search_button;

    ProgressDialog progress;

    Calendar mCurrentDate = Calendar.getInstance();
    int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        selectedTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = (String) selectedTypeSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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
        selectedTypeSpinner = findViewById(R.id.selected_type_spinner);
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

        /*RequisitionStatusHomeModel model = new RequisitionStatusHomeModel();
        model.setRequisitionNo("asdf");
        model.setRequisitionDate("asdf");
        model.setProjectName("asdf");
        model.setPrValue("asdf");
        model.setCsNO("asdf");
        model.setCsValue("asdf");
        model.setSubject("asdf");

        items.add(model);*//*
        adapter.notifyDataSetChanged();*/
    }

    private void getRequisitionStatusJson() {
        progress.show();
        // loadStatusProgressbar.setVisibility(View.VISIBLE);
        String fromDate = searchFromEditText.getText().toString();
        String toDate = searchToEditText.getText().toString();
        //String email = "mamun@bnb.com";

        String finalURL = Constants.GET_REQUISITION_HOME_DATA + "?fromDate=" +
                fromDate + "&toDate=" + toDate +
                "&Search=" +
                "&searchType=" + selectedType + "&EmailID=mamun@bnb.com";

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
}
