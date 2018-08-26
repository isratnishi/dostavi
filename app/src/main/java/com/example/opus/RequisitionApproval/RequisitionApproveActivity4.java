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
import com.example.opus.Models.RequisitionApprovalListModel;
import com.example.opus.R;

import java.util.HashMap;
import java.util.Map;

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
        for (int i = 0; i < returnRejectRadioGroup.getChildCount(); i++) {
            returnRejectRadioGroup.getChildAt(i).setEnabled(isEnabled);
        }
    }

    private void postApproveData() {
        //progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.POST_APPROVED_DATA, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(Constants.LOGTAG, response);
                //progressBar.setVisibility(View.GONE);

                // Successfully Logged in
                /* if (response.equals("1")) {
                 *//* Toast.makeText(RequisitionApproveActivity4.this, "Successfully Logged in",
                            Toast.LENGTH_SHORT).show();*//*
                    //finish();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid email or password! please try again",
                            Toast.LENGTH_SHORT).show();
                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              /*  progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Something went wrong!  Please try again later",
                        Toast.LENGTH_SHORT).show();*/
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                // String email = emailTextView.getText().toString();
                // String password = passwordEditText.getText().toString();

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

                return params;
            }
        };
        AppSingleton.getInstance(getApplicationContext())
                .addToRequestQueue(stringRequest, Constants.REQUEST_TAG);
    }
}
