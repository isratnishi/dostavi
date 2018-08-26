package com.example.opus.RequisitionEntry;

import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.opus.Adapters.ItemAdapter;
import com.example.opus.AppSingleton;
import com.example.opus.Constants;
import com.example.opus.Models.ItemModel;
import com.example.opus.Models.RequisitionMaster;
import com.example.opus.Models.RequisitionModel;
import com.example.opus.Models.ShowItemModel;
import com.example.opus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RequisitionEntryActivity2 extends AppCompatActivity {

    RequisitionMaster requisitionMaster;
    RequisitionModel requisitionModel;
    String requisitionID;

    private ArrayList<ItemModel> items = new ArrayList<>();
    private List<ShowItemModel> JsonItems = new ArrayList<>();
    RecyclerView recyclerView;
    ItemAdapter itemAdapter;
    String draffOrFinal = "0";

    public EditText itemCodeEditText;
    public EditText mainCategoryEditText;
    public EditText descriptionEditText;
    public EditText specificationEditText;
    public EditText itemCategoryEditText;
    public EditText subCategoryEditText;
    public EditText unitEditText;
    public EditText lastPriceEditText;
    public EditText quantityEditText;
    public EditText approxPriceEditText;
    Spinner itemNameSpinner;
    Spinner requisitionStatusSpinner;
    Button addButton;
    Button saveButton;
    ProgressBar loadStatusProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requistion_entry_2);
        initialize();
        getJSON();
        recyclerView = findViewById(R.id.requisition_recycler_view);
        itemAdapter = new ItemAdapter(items, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(itemAdapter);

        itemNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                changeTextsAccordingToSpinner(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        requisitionStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 1)
                    draffOrFinal = "1";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton.setVisibility(View.VISIBLE);

                int total = 0;
                if (!TextUtils.isEmpty(quantityEditText.getText().toString()) &&
                        !TextUtils.isEmpty(approxPriceEditText.getText().toString())) {
                    try {
                        total = Integer.parseInt(quantityEditText.getText().toString())
                                * Integer.parseInt(approxPriceEditText.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                ItemModel itemModel = new ItemModel();
                itemModel.setItemName(itemNameSpinner.getSelectedItem().toString());
                itemModel.setItemCode(itemCodeEditText.getText().toString());
                itemModel.setUnit(unitEditText.getText().toString());
                itemModel.setApproxPrice(approxPriceEditText.getText().toString());
                itemModel.setSpecification(specificationEditText.getText().toString());
                itemModel.setQuantity(Integer.parseInt(quantityEditText.getText().toString()));
                itemModel.setTotal(String.valueOf(total));

                items.add(itemModel);
                itemAdapter.notifyDataSetChanged();

                Toast.makeText(RequisitionEntryActivity2.this, "Item Added!", Toast.LENGTH_SHORT).show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showAlertDialog();
                clearScreen();
            }
        });
    }

    private void saveWholeDataToServer() {
        sendStringRequestRequisitionJson();

        // Start sending requisition details from item no 0
        // postRequisitionDetails(0);
        //postRequisitionStatusLog();
    }

    private void clearScreen() {
        specificationEditText.setText("");
        quantityEditText.setText("");
        approxPriceEditText.setText("");
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(RequisitionEntryActivity2.this,
                    android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(RequisitionEntryActivity2.this);
        }
        builder.setTitle("Save Entry")
                .setMessage("Are you sure you want to save the entries?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        saveWholeDataToServer();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void postRequisitionDetails(final int id) {
        if (id == items.size()) {
            //After saving all data, Requisition Status will be saved
            postRequisitionStatusLog();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.POST_REQUISITION_DETAIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("true")) {
                           // Log.d(Constants.LOGTAG, "saved Item : " + id);
                            postRequisitionDetails(id + 1);
                        } else
                            postRequisitionDetails(id);
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
        AppSingleton.getInstance(getApplicationContext())
                .addToRequestQueue(stringRequest, Constants.REQUEST_TAG);
    }

    private JSONObject getRequisitionStatusJsonObject() {
        JSONObject requisitionApprovalJsonObject = new JSONObject();
        try {
            requisitionApprovalJsonObject.put("MaxMasterId", requisitionID);
            requisitionApprovalJsonObject.put("UserTypeID", requisitionModel.getUserID());
            requisitionApprovalJsonObject.put("empCode", requisitionMaster.getEmpCode());
            requisitionApprovalJsonObject.put("_empCode", requisitionMaster.getEmpCode());
            requisitionApprovalJsonObject.put("empName", requisitionMaster.getEmpName());
            requisitionApprovalJsonObject.put("NextEmpName", requisitionMaster.getEmpName());
            requisitionApprovalJsonObject.put("RequisitionNos", requisitionMaster.getRequisitionNo());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requisitionApprovalJsonObject;
    }

    public void postRequisitionStatusLog() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.POST_REQUISITION_STATUS_LOG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d(Constants.LOGTAG, response);
                        Toast.makeText(RequisitionEntryActivity2.this, "Data saved to server", Toast.LENGTH_SHORT)
                                .show();
                        items.clear();
                        itemAdapter.notifyDataSetChanged();
                        saveButton.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
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

    public JSONObject getRequisitionDetailsJsonObject(ItemModel itemModel) {
        int itemID = Integer.parseInt(itemModel.getItemCode());
        String itemSpac = itemModel.getSpecification();
        double itemQuantity = itemModel.getQuantity();
        String unitName = itemModel.getUnit();
        int approxCost = Integer.parseInt(itemModel.getApproxPrice());

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ReqID", requisitionID);
            jsonObject.put("ItemId", itemID);
            jsonObject.put("ItemSpac", itemSpac);
            jsonObject.put("ReqQTY", itemQuantity);
            jsonObject.put("UnitName", unitName);
            jsonObject.put("ApproxCost", approxCost);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    private void initialize() {
        itemCodeEditText = findViewById(R.id.item_code_edit_text);
        mainCategoryEditText = findViewById(R.id.main_category_edit_text);
        descriptionEditText = findViewById(R.id.description_edit_text);
        itemCategoryEditText = findViewById(R.id.item_category_edit_text);
        subCategoryEditText = findViewById(R.id.sub_category_edit_text);
        lastPriceEditText = findViewById(R.id.last_price_edit_text);
        unitEditText = findViewById(R.id.unit_edit_text);
        approxPriceEditText = findViewById(R.id.approx_price_edit_text);
        specificationEditText = findViewById(R.id.specification_edit_text);
        quantityEditText = findViewById(R.id.quantity_edit_text);
        itemNameSpinner = findViewById(R.id.item_name_spinner);
        requisitionStatusSpinner = findViewById(R.id.requisition_status_spinner);
        loadStatusProgressbar = findViewById(R.id.load_data_progress_bar);

        addButton = findViewById(R.id.add_button);
        saveButton = findViewById(R.id.saveButton);
    }

    private void getJSON() {
        loadStatusProgressbar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constants.GET_ALL_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loadStatusProgressbar.setVisibility(View.GONE);
                        try {
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

                                JsonItems.add(showItemModel);
                            }
                            addItemsNameToSpinner();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadStatusProgressbar.setVisibility(View.GONE);
                        Toast.makeText(RequisitionEntryActivity2.this, "Something went wrong! Please try again later",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        AppSingleton.getInstance(getApplicationContext())
                .addToRequestQueue(stringRequest, Constants.REQUEST_TAG);
    }

    private void addItemsNameToSpinner() {
        List<String> spinnerArray = new ArrayList<>();
        for (int i = 0; i < JsonItems.size(); i++) {
            spinnerArray.add(JsonItems.get(i).getCodeWithName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemNameSpinner.setAdapter(adapter);
    }

    private void changeTextsAccordingToSpinner(int position) {
        itemCodeEditText.setText(JsonItems.get(position).getItemCode());
        mainCategoryEditText.setText(JsonItems.get(position).getMainCatName());
        itemCategoryEditText.setText(JsonItems.get(position).getItemCatName());
        subCategoryEditText.setText(JsonItems.get(position).getSubCatName());
        unitEditText.setText(JsonItems.get(position).getUnitName());
        lastPriceEditText.setText(JsonItems.get(position).getReOrderLevel());
        subCategoryEditText.setText(JsonItems.get(position).getSubCatName());

        requisitionMaster = (RequisitionMaster) getIntent().getSerializableExtra(Constants.REQUISITION_MASTER);
        requisitionModel = (RequisitionModel) getIntent().getSerializableExtra(Constants.REQUISITION_MODEL);
    }


    // break point
    public void sendStringRequestRequisitionJson() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.POST_REQUISITION_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        requisitionID = response;
                        if (!TextUtils.isEmpty(response)) {
                            saveRequisitionLog(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return getRequisitionMasterEntryJsonObject().toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        AppSingleton.getInstance(getApplicationContext())
                .addToRequestQueue(stringRequest, Constants.REQUEST_TAG);
    }

    private void saveRequisitionLog(String maxMasterId) {
        final JSONObject requisitionApprovalJsonObject = getRequisitionApprovalJsonObject(maxMasterId);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.POST_REQUISITION_LOG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        postRequisitionDetails(0);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {

                return requisitionApprovalJsonObject.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        AppSingleton.getInstance(getApplicationContext())
                .addToRequestQueue(stringRequest, Constants.REQUEST_TAG);
    }


    private JSONObject getRequisitionMasterEntryJsonObject() {

        final String requisitionNo = requisitionMaster.getRequisitionNo();
        final String requisitionDate = requisitionMaster.getRequisitionDate();
        final String targetDate = requisitionMaster.getTargetDate();
        final String projectId = requisitionMaster.getProjectId();
        final String empCode = requisitionModel.getEmpCode();
        final String empName = requisitionModel.getEmpName();
        final String dept = requisitionModel.getDept();
        final String subject = requisitionMaster.getSubject();
        final String remarks = requisitionMaster.getRemarks();
        final String compititionWaivers = requisitionMaster.getCompititionWaivers();
        final String postPR = requisitionMaster.getPostPR();

        JSONObject requisitionMasterEntity = new JSONObject();
        try {
            requisitionMasterEntity.put("ReqNo", requisitionNo);
            requisitionMasterEntity.put("ReqDate", requisitionDate);
            requisitionMasterEntity.put("TargetDate", targetDate);
            requisitionMasterEntity.put("ProjectId", projectId);
            requisitionMasterEntity.put("EmpCode", empCode);
            requisitionMasterEntity.put("EmpName", empName);
            requisitionMasterEntity.put("ReqDept", dept);
            requisitionMasterEntity.put("RequistionStatus", draffOrFinal);
            requisitionMasterEntity.put("Subject", subject);
            requisitionMasterEntity.put("Remarks", remarks);
            requisitionMasterEntity.put("CompWaiver", compititionWaivers);
            requisitionMasterEntity.put("PostPR", postPR);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requisitionMasterEntity;
    }

    private JSONObject getRequisitionApprovalJsonObject(String maxMasterId) {
        JSONObject requisitionApprovalJsonObject = new JSONObject();
        try {
            requisitionApprovalJsonObject.put("MaxMasterId", maxMasterId);
            requisitionApprovalJsonObject.put("UserId", requisitionModel.getUserID());
            requisitionApprovalJsonObject.put("EmailID", requisitionModel.getUserName());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requisitionApprovalJsonObject;
    }
}
