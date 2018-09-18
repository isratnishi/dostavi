package com.opus_bd.dostavi.custom_dialogs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.opus_bd.dostavi.AppSingleton;
import com.opus_bd.dostavi.Constants;
import com.opus_bd.dostavi.R;
import com.opus_bd.dostavi.iou_approval.IOUApprovalActivity;
import com.opus_bd.dostavi.models.POApproveModel;
import com.opus_bd.dostavi.po_approve.POApproveActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class POApproveDialog extends Dialog implements View.OnClickListener {
    Context context;
    Button saveButton;
    Button cancelButton;
    EditText remarkEditText;
    CheckBox returnRejectCheckBox;
    RadioGroup returnRejectRadioGroup;
    Button approveButton;
    int appType = 1;
    private ArrayList<POApproveModel> items = new ArrayList<>();
    POApproveModel poApproveModel;
    ProgressDialog progressDialog;

    public POApproveDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public POApproveModel getPoApproveModel() {
        return poApproveModel;
    }

    public void setPoApproveModel(POApproveModel poApproveModel) {
        this.poApproveModel = poApproveModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.iou_approval_dialog);
        saveButton = (Button) findViewById(R.id.save_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        saveButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        remarkEditText = findViewById(R.id.remark_edit_text);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait");

        returnRejectCheckBox = findViewById(R.id.return_reject_checkbox);
        returnRejectRadioGroup = findViewById(R.id.return_reject_radio_group);
        approveButton = findViewById(R.id.approve_button);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_button:
                if (returnRejectCheckBox.isChecked()) {
                    if (returnRejectRadioGroup.getCheckedRadioButtonId() == R.id.return_radio_button)
                        appType = 4;
                    else if (returnRejectRadioGroup.getCheckedRadioButtonId() == R.id.reject_radio_button)
                        appType = -1;
                    else
                        appType = 1;
                } else
                    appType = 1;

                savePOApprove();
                break;
            case R.id.cancel_button:
                dismiss();
                break;
            default:
                break;
        }
    }

    private JSONObject getPOApprovalJsonObject() {
        JSONObject POApprovalJsonObject = new JSONObject();
        try {

            POApprovalJsonObject.put("Email", Constants.USER_EMAIL);
            POApprovalJsonObject.put("csID", poApproveModel.getID());
            POApprovalJsonObject.put("csNo", poApproveModel.getCSNo());
            POApprovalJsonObject.put("ReqID", poApproveModel.getReqID());
            POApprovalJsonObject.put("ReqNo", poApproveModel.getReqNo());
            POApprovalJsonObject.put("ReqDate", poApproveModel.getReqDate());
            POApprovalJsonObject.put("Remarks", remarkEditText.getText().toString());
            POApprovalJsonObject.put("AppType", appType + "");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return POApprovalJsonObject;
    }

    private void savePOApprove() {
        dismiss();
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.POST_PO_APPROVE_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if (response.equalsIgnoreCase("true")) {
                            Intent intent = new Intent(context, POApproveActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            Toasty.success(context, "Data saved to server!", Toast.LENGTH_SHORT, true).show();
                        }
                        dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {

                return getPOApprovalJsonObject().toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        AppSingleton.getInstance(context)
                .addToRequestQueue(stringRequest, Constants.REQUEST_TAG);
    }
}
