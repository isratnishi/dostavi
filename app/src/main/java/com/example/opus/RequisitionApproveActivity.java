package com.example.opus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

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
    }


    private void getJSON() {
        loadStatusProgressbar.setVisibility(View.VISIBLE);
        String id = "146257";
        String finalURL = Constants.GET_REQUISITION_APPROVAL_LIST + "?id=" + id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                finalURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loadStatusProgressbar.setVisibility(View.GONE);
                        //Log.d(Constants.LOGTAG, response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String requisitionBy = jsonObject.getString("EmpCode") + "- " +
                                    jsonObject.getString("EmpName");
                            String compititionWaiver = jsonObject.getString("CompWaiver");
                            /*next = findViewById(R.id.next_button);
                            supplierNameEditText = findViewById(R.id.supplier_name_edit_text);*/

                            Log.d(Constants.LOGTAG, compititionWaiver);

                            requisitionNumberEditText.setText(jsonObject.getString("ReqNo"));
                            requisitionDateEditText.setText(jsonObject.getString("ReqDate"));
                            productTargetDateEditText.setText(jsonObject.getString("TargetDate"));
                            requisitionByEditText.setText(requisitionBy);
                            justificationEditText.setText(jsonObject.getString("Remarks"));
                            subjectEditText.setText(jsonObject.getString("Subject"));

                            if (!compititionWaiver.equals("null")) {
                                compititionWaiverCheckBox.setChecked(true);
                                //subjectEditText.setText(jsonObject.getString(""));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        /*try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject temp = jsonArray.getJSONObject(i);

                                String codeWithName = temp.getString(ShowItemModel.CODE_WITH_NAME);
                                String id = temp.getString(ShowItemModel.JSON_ID);
                                String itemCatID = temp.getString(ShowItemModel.ITEM_CAT_ID);
                                String itemCatName = temp.getString(ShowItemModel.ITEM_CAT_NAME);
                                String itemCode = temp.getString(ShowItemModel.ITEM_CODE);
                                String itemDescription = temp.getString(ShowItemModel.ITEM_DESCRIPTION);
                                String itemName = temp.getString(ShowItemModel.ITEM_NAME);
                                String itemSpac = temp.getString(ShowItemModel.ITEM_SPAC);
                                String mainCatName = temp.getString(ShowItemModel.MAIN_CAT_NAME);
                                String mCatID = temp.getString(ShowItemModel.M_CAT_ID);
                                String reorderLevel = temp.getString(ShowItemModel.REORDER_LEVEL);
                                String subCatID = temp.getString(ShowItemModel.SUB_CAT_ID);
                                String subCatName = temp.getString(ShowItemModel.SUB_CAT_NAME);
                                String unitName = temp.getString(ShowItemModel.UNIT_NAME);

                                ShowItemModel showItemModel = new ShowItemModel();
                                showItemModel.setCodeWithName(codeWithName);
                                showItemModel.setID(id);
                                showItemModel.setItemCatID(itemCatID);
                                showItemModel.setItemCatName(itemCatName);
                                showItemModel.setItemCode(itemCode);
                                showItemModel.setItemDescription(itemDescription);
                                showItemModel.setItemName(itemName);
                                showItemModel.setItemSpac(itemSpac);
                                showItemModel.setMainCatName(mainCatName);
                                showItemModel.setmCatID(mCatID);
                                showItemModel.setReOrderLevel(reorderLevel);
                                showItemModel.setSubCatID(subCatID);
                                showItemModel.setSubCatName(subCatName);
                                showItemModel.setUnitName(unitName);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
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
