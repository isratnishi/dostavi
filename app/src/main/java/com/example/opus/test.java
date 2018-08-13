package com.example.opus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class test extends AppCompatActivity {

    //private TextView signinButton;
    private CardView requisitionEntry;
    private LinearLayout requisitionEntryLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        requisitionEntry = findViewById(R.id.requisition_entry_card_view);
        requisitionEntryLinearLayout = findViewById(R.id.requisition_entry_linear_layout);

        requisitionEntryLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Linear Layout", Toast.LENGTH_SHORT).show();
            }
        });

        requisitionEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "CardView", Toast.LENGTH_SHORT).show();
            }
        });
        /*signinButton = findViewById(R.id.email_sign_in_button);

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Signin", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
