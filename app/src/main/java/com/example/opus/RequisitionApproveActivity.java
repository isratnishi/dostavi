package com.example.opus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RequisitionApproveActivity extends AppCompatActivity {

    ProgressBar loadStatusProgressbar;
    EditText requisitionNumberEditText;
    EditText requisitionDateEditText;
    EditText productTargetDateEditText;
    EditText requisitionByEditText;
    EditText justificationEditText;
    EditText subjectEditText;
    CheckBox compititionWaiverCheckBox;
    EditText supplierNameEditText;
    EditText approverNameEditText;
    EditText departmentEditRText;
    EditText maxAmountEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisition_approve);
        initializeVariables();
        getJSON();

    }

    private void initializeVariables() {
        loadStatusProgressbar = findViewById(R.id.load_data_progress_bar);
        requisitionNumberEditText = findViewById(R.id.requisition_number_edit_text);
        requisitionDateEditText = findViewById(R.id.requisition_date_edit_text);
        productTargetDateEditText = findViewById(R.id.product_delivery_date_edit_text);
        requisitionByEditText = findViewById(R.id.requisition_by_edit_text);
        justificationEditText = findViewById(R.id.justification_edit_text);
        subjectEditText = findViewById(R.id.subject_edit_text);
        compititionWaiverCheckBox = findViewById(R.id.compitition_waiver_checkbox);
        supplierNameEditText = findViewById(R.id.supplier_name_edit_text);
        approverNameEditText = findViewById(R.id.approver_name_edit_text);
        departmentEditRText = findViewById(R.id.department_edit_text);
        maxAmountEditText = findViewById(R.id.max_amount_edit_text);
    }


    private void getJSON() {
        loadStatusProgressbar.setVisibility(View.VISIBLE);
        String id = "146257";
        String username = "mamun@bnb.com";
        String finalURL = Constants.GET_PRA_APPROVER + "?username=" + username + "&projectID=" + id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                finalURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loadStatusProgressbar.setVisibility(View.GONE);
                        //getPraApprover();
                        //Log.d(Constants.LOGTAG, response);
                        try {
                            //String object1 = new JSONObject("NextApprover");
                            // JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject requisitionMaster = jsonObject.getJSONObject("RequisitionMaster");

                            String requisitionBy = requisitionMaster.getString("EmpCode") + "- " +
                                    requisitionMaster.getString("EmpName");
                            String compititionWaiver = requisitionMaster.getString("CompWaiver");

                            requisitionNumberEditText.setText(requisitionMaster.getString("ReqNo"));
                            requisitionDateEditText.setText(requisitionMaster.getString("ReqDate"));
                            productTargetDateEditText.setText(requisitionMaster.getString("TargetDate"));
                            requisitionByEditText.setText(requisitionBy);
                            justificationEditText.setText(requisitionMaster.getString("Remarks"));
                            subjectEditText.setText(requisitionMaster.getString("Subject"));

                            if (!compititionWaiver.equals("null")) {
                                compititionWaiverCheckBox.setChecked(true);
                            }

                            JSONObject nextApprover = jsonObject.getJSONObject("NextApprover");
                            String approverName = nextApprover.getString("EmpName");
                            String department = nextApprover.getString("Department");
                            String maxAmount = nextApprover.getString("MaxAmountPO");

                            approverNameEditText.setText(approverName);
                            departmentEditRText.setText(department);
                            maxAmountEditText.setText(maxAmount);

                            getRequisitionItem();

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

    private void getRequisitionItem() {
        loadStatusProgressbar.setVisibility(View.VISIBLE);
        String maxMasterID = "146257";
        String finalURL = Constants.GET_REQUISITION_LIST + "?MasterId=" + maxMasterID;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                finalURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loadStatusProgressbar.setVisibility(View.GONE);
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            Log.d(Constants.LOGTAG, response);

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
