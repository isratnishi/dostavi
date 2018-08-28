package com.example.opus.RequisitionApproval;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.example.opus.AppSingleton;
import com.example.opus.Constants;
import com.example.opus.HomeActivity;
import com.example.opus.Models.RequisitionApprovalListModel;
import com.example.opus.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class RequisitionApproveActivity4 extends AppCompatActivity {

    CheckBox returnRejectCheckBox;
    RadioGroup returnRejectRadioGroup;
    Button approveButton;
    RequisitionApprovalListModel model;
    int appType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisition_approve4);

        returnRejectCheckBox = findViewById(R.id.return_reject_checkbox);
        returnRejectRadioGroup = findViewById(R.id.return_reject_radio_group);
        approveButton = findViewById(R.id.approve_button);

        model = (RequisitionApprovalListModel) getIntent().getSerializableExtra(Constants.REQUISITION_APPROVAL_LIST_MODEL);

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
        //progressBar.setVisibility(View.VISIBLE);

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

                Intent intent = new Intent(RequisitionApproveActivity4.this, RequisitionApproveActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                //Log.d(Constants.LOGTAG, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              /*  progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Something went wrong!  Please try again later",
                        Toast.LENGTH_SHORT).show();*/
            }
        }) /*{
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String reqMasterID = model.getRequisitionID();
                String remark = "";
                String tempAppType = String.valueOf(appType);
                String projectID = model.getProjectID();
                String reqNo = model.getRequisitionNo();

                Map<String, String> params = new HashMap<>();
                params.put("ReqMasterId", reqMasterID);
                params.put("Remark", remark);
                params.put("AppType", tempAppType);
                params.put("ProjectId", projectID);
                params.put("reqNo", reqNo);
                params.put("sender", Constants.USER_EMAIL);
                params.put("EmailID", Constants.USER_EMAIL);
                return params;
            }
        }*/ {

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
}
