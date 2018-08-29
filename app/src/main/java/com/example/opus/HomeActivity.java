package com.example.opus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.opus.models.User;
import com.example.opus.requisition_approval.RequisitionApprovalActivity1;
import com.example.opus.requisition_entry.RequisitionEntryActivity1;
import com.example.opus.requisition_status.RequisitionStatusHome;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout requisitionEntryLinearLayout;
    private LinearLayout requisitionApproveLinearLayout;
    private ImageView requisitionEntryImageView;
    private ImageView requisitionApproveImageView;
    private LinearLayout requisitionStatusLinearLayout;
    private ImageView requisitionStatusImageView;
    private ProgressDialog progress;
    private TextView emailTextView;
    private TextView nameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeVariables();
        getUserInformationFromServer();

        requisitionEntryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, RequisitionEntryActivity1.class));
            }
        });

        requisitionEntryLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, RequisitionEntryActivity1.class));
            }
        });

        requisitionApproveImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, RequisitionApprovalActivity1.class));
            }
        });

        requisitionApproveLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, RequisitionApprovalActivity1.class));
            }
        });

        requisitionStatusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, RequisitionStatusHome.class));
            }
        });

        requisitionStatusLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, RequisitionStatusHome.class));
            }
        });
    }

    private void getUserInformationFromServer() {
        progress.show();
        String finalURL = Constants.GET_USER_INFORMATION + "?email=" + Constants.USER_EMAIL;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                finalURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            User.getInstance().setUserCode(obj.getString("EmpCode"));
                            User.getInstance().setUserName(obj.getString("EmpName"));
                            User.getInstance().setUserEmail(obj.getString("UserName"));

                            emailTextView.setText(User.getInstance().getUserEmail());
                            nameTextView.setText(User.getInstance().getUserName());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        Toasty.error(getApplicationContext(), "Something went wrong!  Please try again later",
                                Toast.LENGTH_SHORT, true).show();
                        finish();
                    }
                });
        AppSingleton.getInstance(getApplicationContext())
                .addToRequestQueue(stringRequest, Constants.REQUEST_TAG);
    }

    private void initializeVariables() {
        emailTextView = findViewById(R.id.person_email_text_view);
        nameTextView = findViewById(R.id.person_name_text_view);
        requisitionEntryLinearLayout = findViewById(R.id.requisition_entry_linear_layout);
        requisitionEntryImageView = findViewById(R.id.requisition_entry_image_button);
        requisitionApproveLinearLayout = findViewById(R.id.requisition_approve_linear_layout);
        requisitionApproveImageView = findViewById(R.id.requisition_approve_image_button);
        requisitionStatusLinearLayout = findViewById(R.id.requisition_status_layout);
        requisitionStatusImageView = findViewById(R.id.requisition_status_image_view);

        progress = new ProgressDialog(this);
        progress.setMessage("Please Wait");


    }
}
