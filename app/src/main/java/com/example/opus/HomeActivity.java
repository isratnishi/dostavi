package com.example.opus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.opus.RequisitionApproval.RequisitionApprovalListActivity;
import com.example.opus.RequisitionEntry.RequisitionEntryFormActivity;
import com.example.opus.RequisitionStatus.RequisitionStatusHome;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout requisitionEntryLinearLayout;
    private LinearLayout requisitionApproveLinearLayout;
    private ImageView requisitionEntryImageView;
    private ImageView requisitionApproveImageView;
    private LinearLayout requisitionStatusLinearLayout;
    private ImageView requisitionStatusImageView;

    private TextView emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        emailTextView = findViewById(R.id.person_email_textview);
        requisitionEntryLinearLayout = findViewById(R.id.requisition_entry_linear_layout);
        requisitionEntryImageView = findViewById(R.id.requisition_entry_image_button);
        requisitionApproveLinearLayout = findViewById(R.id.requisition_approve_linear_layout);
        requisitionApproveImageView = findViewById(R.id.requisition_approve_image_button);
        requisitionStatusLinearLayout = findViewById(R.id.requisition_status_layout);
        requisitionStatusImageView = findViewById(R.id.requisition_status_image_view);

        emailTextView.setText(Constants.USER_EMAIL);


        requisitionEntryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, RequisitionEntryFormActivity.class));
            }
        });

        requisitionEntryLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, RequisitionEntryFormActivity.class));
            }
        });

        requisitionApproveImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, RequisitionApprovalListActivity.class));
            }
        });

        requisitionApproveLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, RequisitionApprovalListActivity.class));
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
}
