package com.opus_bd.dostavi.custom_dialogs;

import android.app.Dialog;
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
import android.widget.RadioButton;
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
import com.opus_bd.dostavi.R;
import com.opus_bd.dostavi.Utils;
import com.opus_bd.dostavi.iou_approval.IOUApprovalActivity;
import com.opus_bd.dostavi.iou_approval.IOUApprovalActivity2;
import com.opus_bd.dostavi.models.IOUApprovalModel;
import com.opus_bd.dostavi.models.IOUItemModel;
import com.opus_bd.dostavi.models.ItemModel;
import com.opus_bd.dostavi.models.RequisitionApprovalListModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class IOUApproveDialog extends Dialog implements View.OnClickListener {


    Context context;
    Button saveButton;
    Button cancelButton;
    EditText remarkEditText;
    CheckBox returnRejectCheckBox;
    RadioGroup returnRejectRadioGroup;
    Button approveButton;
    int appType = 1;
    private ArrayList<IOUItemModel> items = new ArrayList<>();
    IOUApprovalModel iouApprovalModel;

    public IOUApproveDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<IOUItemModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<IOUItemModel> items) {
        this.items = items;
    }

    public IOUApprovalModel getIouApprovalModel() {
        return iouApprovalModel;
    }

    public void setIouApprovalModel(IOUApprovalModel iouApprovalModel) {
        this.iouApprovalModel = iouApprovalModel;
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
                //Utils.showLogcatMessage("save button clicked, appType " + appType);
                if (returnRejectCheckBox.isChecked()) {
                    if (returnRejectRadioGroup.getCheckedRadioButtonId() == R.id.return_radio_button)
                        appType = 4;
                    else if (returnRejectRadioGroup.getCheckedRadioButtonId() == R.id.reject_radio_button)
                        appType = -1;
                    else
                        appType = 1;
                } else
                    appType = 1;

                if (appType == 1) {
                    saveIouItems(0);
                } else
                    saveIouApprove();
                break;
            case R.id.cancel_button:
                dismiss();
                break;
            default:
                break;
        }
    }

    private JSONObject getIouApprovalJsonObject() {
        JSONObject requisitionApprovalJsonObject = new JSONObject();
        try {
            requisitionApprovalJsonObject.put("ID", iouApprovalModel.getID());
            requisitionApprovalJsonObject.put("IOUNo", iouApprovalModel.getIOUNo());
            requisitionApprovalJsonObject.put("IOUDate", iouApprovalModel.getIOUDate());
            requisitionApprovalJsonObject.put("ReqID", iouApprovalModel.getReqID());
            requisitionApprovalJsonObject.put("UserID", Constants.USER_EMAIL);
            requisitionApprovalJsonObject.put("ProjectID", iouApprovalModel.getProjectID());
            requisitionApprovalJsonObject.put("IOUStatus", iouApprovalModel.getIOUStatus());
            requisitionApprovalJsonObject.put("AttentionTo", iouApprovalModel.getAttentionTo());
            requisitionApprovalJsonObject.put("EntryTime", iouApprovalModel.getEntryTime());
            requisitionApprovalJsonObject.put("StatusDesc", iouApprovalModel.getStatusDesc());
            requisitionApprovalJsonObject.put("IOUValue", iouApprovalModel.getIOUValue());
            requisitionApprovalJsonObject.put("ReqNo", iouApprovalModel.getReqNo());
            requisitionApprovalJsonObject.put("TargetDate", iouApprovalModel.getTargetDate());
            requisitionApprovalJsonObject.put("ReqDate", iouApprovalModel.getReqDate());
            requisitionApprovalJsonObject.put("PrIOUNo", iouApprovalModel.getPrIOUNo());
            requisitionApprovalJsonObject.put("ProjectName", iouApprovalModel.getProjectName());
            requisitionApprovalJsonObject.put("Remarks", remarkEditText.getText().toString());
            requisitionApprovalJsonObject.put("AppType", appType + "");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requisitionApprovalJsonObject;
    }

    private void saveIouItems(final int id) {

        if (id == items.size()) {
            saveIouApprove();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.POST_SAVE_ITEMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Utils.showLogcatMessage(response);
                        if (response.equals("true")) {
                            saveIouItems(id + 1);
                        } else
                            saveIouItems(id);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {

                return getRequisitionDetailsJsonObject(items.get(id)).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        AppSingleton.getInstance(context)
                .addToRequestQueue(stringRequest, Constants.REQUEST_TAG);

    }

    public JSONObject getRequisitionDetailsJsonObject(IOUItemModel itemModel) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ID", iouApprovalModel.getID());
            jsonObject.put("Rate", itemModel.getRate());
            jsonObject.put("Qty", itemModel.getQty());
            jsonObject.put("reqID", itemModel.getReqID());
            jsonObject.put("EmailID", Constants.USER_EMAIL);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void saveIouApprove() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.POST_IOU_APPROVE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("true")) {
                            Intent intent = new Intent(context, IOUApprovalActivity.class);
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
                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {

                return getIouApprovalJsonObject().toString().getBytes();
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
