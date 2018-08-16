package com.example.opus;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.opus.Models.RequisitionStatusHomeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PrDetailsActivity1 extends AppCompatActivity {

    EditText requisitionNumberEditText;
    EditText requisitionDateEditText;
    EditText productTargetDateEditText;
    EditText subjectEditText;
    EditText requisitionByEditText;
    EditText departmentEditText;
    EditText justificationEditText;
    ProgressDialog progress;

    String requisitionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pr_details1);
        initializeVariables();
        getPrBasicInfoJson();
    }

    private void getPrBasicInfoJson() {
        progress.show();

        String finalURL = Constants.GET_PRA_BASIC_INFO + "?reqID=" + requisitionID;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                finalURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject tempObject = jsonArray.getJSONObject(0);

                            requisitionNumberEditText.setText(tempObject.getString("ReqNo"));
                            requisitionDateEditText.setText(tempObject.getString("ReqDate"));
                            productTargetDateEditText.setText(tempObject.getString("ReqDept"));
                            subjectEditText.setText(tempObject.getString("EmpName"));
                            requisitionByEditText.setText(tempObject.getString("TargetDate"));
                            departmentEditText.setText(tempObject.getString("Remarks"));
                            justificationEditText.setText(tempObject.getString("Subject"));

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

    private void initializeVariables() {
        requisitionNumberEditText = findViewById(R.id.requisition_no_edit_text);
        requisitionDateEditText = findViewById(R.id.pr_date_edit_text);
        productTargetDateEditText = findViewById(R.id.target_date_edit_text);
        subjectEditText = findViewById(R.id.subject_edit_text);
        requisitionByEditText = findViewById(R.id.requisition_by_edit_text);
        departmentEditText = findViewById(R.id.department_edit_text);
        justificationEditText = findViewById(R.id.justification_edit_text);

        progress = new ProgressDialog(this);
        progress.setMessage("Please Wait");

        requisitionID = getIntent().getExtras().getString(Constants.REQUISTION_ID);
    }
}
