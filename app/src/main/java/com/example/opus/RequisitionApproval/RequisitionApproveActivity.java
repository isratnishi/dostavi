package com.example.opus.RequisitionApproval;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.opus.AppSingleton;
import com.example.opus.Constants;
import com.example.opus.Models.RequisitionApprovalListModel;
import com.example.opus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class RequisitionApproveActivity extends AppCompatActivity {
    ProgressBar loadStatusProgressbar;
    TextView requisitionNumberTextView;
    TextView requisitionDateTextView;
    TextView productTargetDateTextView;
    TextView requisitionByTextView;
    TextView justificationTextView;
    TextView subjectTextView;
    CheckBox compititionWaiverCheckBox;
    TextView supplierNameTextView;
    TextView approverNameTextView;
    TextView departmentTextView;
    TextView maxAmountTextView;
    TextView supplierNameLabelTextView;
    Button nextButton;
    RequisitionApprovalListModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_requisition_approve);
        initializeVariables();
        getJSON();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequisitionApproveActivity.this, RequistionApproveActivity2.class);
                intent.putExtra(Constants.REQUISITION_APPROVAL_LIST_MODEL, model);
                startActivity(intent);
            }
        });
    }

    private void initializeVariables() {
        loadStatusProgressbar = findViewById(R.id.load_data_progress_bar);
        requisitionNumberTextView = findViewById(R.id.requisition_number_edit_text);
        requisitionDateTextView = findViewById(R.id.requisition_date_edit_text);
        productTargetDateTextView = findViewById(R.id.product_delivery_date_edit_text);
        requisitionByTextView = findViewById(R.id.requisition_by_edit_text);
        justificationTextView = findViewById(R.id.justification_edit_text);
        subjectTextView = findViewById(R.id.subject_edit_text);
        compititionWaiverCheckBox = findViewById(R.id.compitition_waiver_checkbox);
        supplierNameTextView = findViewById(R.id.supplier_name_text_view);
        approverNameTextView = findViewById(R.id.approver_name_edit_text);
        departmentTextView = findViewById(R.id.department_edit_text);
        maxAmountTextView = findViewById(R.id.max_amount_edit_text);
        supplierNameLabelTextView = findViewById(R.id.supplier_name_label);
        nextButton = findViewById(R.id.next_button);

        model = (RequisitionApprovalListModel) getIntent().getSerializableExtra(Constants.REQUISITION_APPROVAL_LIST_MODEL);
    }

    private void getJSON() {
        loadStatusProgressbar.setVisibility(View.VISIBLE);
        String finalURL = Constants.GET_PRA_APPROVER + "?username=" + Constants.USER_EMAIL + "&projectID=" + model.getRequisitionID();
        //Log.d(Constants.LOGTAG, finalURL);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                finalURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d(Constants.LOGTAG, response);
                        loadStatusProgressbar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject requisitionMaster = jsonObject.getJSONObject("RequisitionMaster");

                            String requisitionBy = requisitionMaster.getString("EmpCode") + "- " +
                                    requisitionMaster.getString("EmpName");
                            String compititionWaiver = requisitionMaster.getString("CompWaiver");

                            requisitionNumberTextView.setText(requisitionMaster.getString("ReqNo"));
                            requisitionDateTextView.setText(requisitionMaster.getString("ReqDate"));
                            productTargetDateTextView.setText(requisitionMaster.getString("TargetDate"));
                            requisitionByTextView.setText(requisitionBy);
                            justificationTextView.setText(requisitionMaster.getString("Remarks"));
                            subjectTextView.setText(requisitionMaster.getString("Subject"));

                            if (!compititionWaiver.equalsIgnoreCase("0")) {
                                compititionWaiverCheckBox.setChecked(true);
                                supplierNameLabelTextView.setVisibility(View.VISIBLE);
                                supplierNameTextView.setVisibility(View.VISIBLE);
                            }

                            JSONObject nextApprover = jsonObject.getJSONObject("NextApprover");
                            String approverName = nextApprover.getString("EmpName");
                            String department = nextApprover.getString("Department");
                            String maxAmount = nextApprover.getString("MaxAmountPO");

                            approverNameTextView.setText(approverName);
                            departmentTextView.setText(department);
                            maxAmountTextView.setText(maxAmount);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
