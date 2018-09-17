package com.opus_bd.dostavi.iou_approval;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opus_bd.dostavi.AppSingleton;
import com.opus_bd.dostavi.Constants;
import com.opus_bd.dostavi.R;
import com.opus_bd.dostavi.Utils;
import com.opus_bd.dostavi.adapters.IOUItemAdapter;
import com.opus_bd.dostavi.custom_dialogs.IOUApproveDialog;
import com.opus_bd.dostavi.models.IOUApprovalModel;
import com.opus_bd.dostavi.models.IOUItemModel;
import com.opus_bd.dostavi.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IOUApprovalActivity2 extends AppCompatActivity {

    public ArrayList<IOUItemModel> items = new ArrayList<>();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    public IOUItemAdapter iouItemAdapter;
    private Gson gson;
    private int masterId;
    private IOUApprovalModel iouItemModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iouapproval2);
        ButterKnife.bind(this);
        initializeVariables();
        getIouApprovalData();
    }

    private void initializeVariables() {
        iouItemAdapter = new IOUItemAdapter(items, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(iouItemAdapter);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            masterId = bundle.getInt(Constants.MASTER_ID);
            iouItemModel = (IOUApprovalModel) bundle.getSerializable(Constants.IOU_APPROVAL_MODEL);
        }
    }

    @OnClick(R.id.next_button)
    public void showDialog()
    {
        IOUApproveDialog dialog = new IOUApproveDialog(this);
        dialog.setItems(items);
        dialog.setIouApprovalModel(iouItemModel);
        dialog.show();
    }

    private JSONObject getIouApprovalJsonObject() {
        JSONObject requisitionApprovalJsonObject = new JSONObject();
        /*try {
            requisitionApprovalJsonObject.put("MaxMasterId", maxMasterId);
            requisitionApprovalJsonObject.put("UserId", requisitionModel.getUserID());
            requisitionApprovalJsonObject.put("EmailID", requisitionModel.getUserName());
            requisitionApprovalJsonObject.put("EmpCode", requisitionModel.getEmpCode());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return requisitionApprovalJsonObject;
    }

    private void getIouApprovalData() {
        String finalURL = Constants.GET_IOU_ITEMS + "?MasterId=" + masterId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                finalURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);
                            if (jsonArray.length() > 0) {
                                List<IOUItemModel> leaveApprovalModels = Arrays.asList(gson.fromJson(jsonArray.toString(),
                                        IOUItemModel[].class));

                                items.addAll(leaveApprovalModels);
                                iouItemAdapter.notifyDataSetChanged();
                                iouItemAdapter.notifyDataSetChanged();
                            }
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

    private void saveIouItems()
    {

    }

    private void saveIouApprove()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.POST_REQUISITION_LOG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Utils.showLogcatMessage(response);
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
        AppSingleton.getInstance(getApplicationContext())
                .addToRequestQueue(stringRequest, Constants.REQUEST_TAG);
    }
}
