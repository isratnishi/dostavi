package com.opus_bd.dostavi.requisition_approval;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.opus_bd.dostavi.AppSingleton;
import com.opus_bd.dostavi.Constants;
import com.opus_bd.dostavi.LoginActivity;
import com.opus_bd.dostavi.models.RequisitionApprovalListModel;
import com.opus_bd.dostavi.R;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class RequisitionApproveActivity5 extends AppCompatActivity {

    CheckBox returnRejectCheckBox;
    RadioGroup returnRejectRadioGroup;
    Button approveButton;
    RequisitionApprovalListModel model;
    int appType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisition_approve4);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeVariables();

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (returnRejectCheckBox.isChecked()) {
                    if (returnRejectRadioGroup.getCheckedRadioButtonId() == R.id.return_radio_button)
                        appType = 4;
                    else if (returnRejectRadioGroup.getCheckedRadioButtonId() == R.id.reject_radio_button)
                        appType = -1;
                    else
                        appType = 1;
                } else
                    appType = 1;

                postApproveData();
            }
        });

        changeRadioButtonStatus(false);

        returnRejectCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                changeRadioButtonStatus(isChecked);
            }
        });
    }

    private void initializeVariables() {
        returnRejectCheckBox = findViewById(R.id.return_reject_checkbox);
        returnRejectRadioGroup = findViewById(R.id.return_reject_radio_group);
        approveButton = findViewById(R.id.approve_button);
        model = (RequisitionApprovalListModel) getIntent().getSerializableExtra(Constants.REQUISITION_APPROVAL_LIST_MODEL);
    }

    private void changeRadioButtonStatus(boolean isEnabled) {
        if (!isEnabled) {
            returnRejectRadioGroup.clearCheck();
        }
        for (int i = 0; i < returnRejectRadioGroup.getChildCount(); i++) {
            returnRejectRadioGroup.getChildAt(i).setEnabled(isEnabled);
        }
    }

    private JSONObject getRequisitionStatusJsonObject() {
        String reqMasterID = model.getRequisitionID();
        String remark = "";
        String tempAppType = String.valueOf(appType);
        String projectID = model.getProjectID();
        String reqNo = model.getRequisitionNo();

        JSONObject requisitionApprovalJsonObject = new JSONObject();
        try {
            requisitionApprovalJsonObject.put("ReqMasterId", reqMasterID);
            requisitionApprovalJsonObject.put("Remark", remark);
            requisitionApprovalJsonObject.put("AppType", tempAppType);
            requisitionApprovalJsonObject.put("ProjectId", projectID);
            requisitionApprovalJsonObject.put("reqNo", reqNo);
            requisitionApprovalJsonObject.put("sender", Constants.USER_EMAIL);
            requisitionApprovalJsonObject.put("EmailID", Constants.USER_EMAIL);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requisitionApprovalJsonObject;
    }

    private void postApproveData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.POST_APPROVED_DATA, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (response.equals("-1"))
                    Toasty.success(getApplicationContext(), "Your PR has been rejected", Toast.LENGTH_SHORT).show();
                else if (response.equals("4"))
                    Toasty.success(getApplicationContext(), "Your PR has been returned", Toast.LENGTH_SHORT).show();
                else
                    Toasty.success(getApplicationContext(), "Your PR has been approved", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(RequisitionApproveActivity5.this, RequisitionApproveActivity2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                return getRequisitionStatusJsonObject().toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
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
            Intent intent = new Intent(RequisitionApproveActivity5.this, LoginActivity.class);
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
