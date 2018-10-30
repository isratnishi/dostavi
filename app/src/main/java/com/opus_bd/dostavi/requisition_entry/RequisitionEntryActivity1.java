package com.opus_bd.dostavi.requisition_entry;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.opus_bd.dostavi.AppSingleton;
import com.opus_bd.dostavi.Constants;
import com.opus_bd.dostavi.LoginActivity;
import com.opus_bd.dostavi.R;
import com.opus_bd.dostavi.Utils;
import com.opus_bd.dostavi.models.RequisitionMaster;
import com.opus_bd.dostavi.models.RequisitionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RequisitionEntryActivity1 extends AppCompatActivity {

    TextView requisitionNumberTextView;
    EditText requisitionDateEditText;
    EditText productTargetDateEditText;
    EditText subjectEditText;
    TextView prRaiserEditText;
    TextView departmentEditText;
    EditText justificationEditText;
    Spinner projectNameSpinner;
    CheckBox isPostPR;
    CheckBox compititionWaiver;
    Button next;
    EditText supplierNameEditText;
    ProgressDialog progress;

    String selectedProjectID = null;
    RequisitionModel requisitionModel = new RequisitionModel();
    Calendar mCurrentDate = Calendar.getInstance();
    Calendar fromDate = Calendar.getInstance();
    Calendar toDate = Calendar.getInstance();
    int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_requisition_entry_1);
        initializeVariables();
        getRequisitionJSON();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequisitionMaster requisitionMaster = new RequisitionMaster();
                requisitionMaster.setRequisitionNo(requisitionNumberTextView.getText().toString());
                requisitionMaster.setRequisitionDate(Utils.getFormattedDate(fromDate));
                requisitionMaster.setTargetDate(Utils.getFormattedDate(toDate));
                requisitionMaster.setProjectId(selectedProjectID);
                requisitionMaster.setEmpCode(requisitionModel.getEmpCode());
                requisitionMaster.setEmpName(requisitionModel.getEmpName());
                requisitionMaster.setDept(requisitionModel.getDept());
                requisitionMaster.setDraffOrFinal("1");
                requisitionMaster.setSubject(subjectEditText.getText().toString());
                requisitionMaster.setRemarks(justificationEditText.getText().toString());

                if (compititionWaiver.isChecked())
                    requisitionMaster.setCompititionWaivers(supplierNameEditText.getText().toString());
                if (isPostPR.isChecked())
                    requisitionMaster.setPostPR("1");

                Intent intent = new Intent(RequisitionEntryActivity1.this, RequisitionEntryActivity2.class);
                intent.putExtra(Constants.REQUISITION_MODEL, requisitionModel);
                intent.putExtra(Constants.REQUISITION_MASTER, requisitionMaster);
                startActivity(intent);
            }
        });

        requisitionDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(RequisitionEntryActivity1.this,
                        new DatePickerDialog.OnDateSetListener() {

                            public void onDateSet(DatePicker datepicker, int
                                    selectedYear, int selectedMonth, int selectedDay) {
                                mCurrentDate.set(Calendar.YEAR, selectedYear);
                                mCurrentDate.set(Calendar.MONTH, selectedMonth);
                                mCurrentDate.set(Calendar.DAY_OF_MONTH, selectedDay);

                                fromDate.set(Calendar.YEAR, selectedYear);
                                fromDate.set(Calendar.MONTH, selectedMonth);
                                fromDate.set(Calendar.DAY_OF_MONTH, selectedDay);

                                requisitionDateEditText.setText((selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear);
                            }
                        }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        productTargetDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(RequisitionEntryActivity1.this,
                        new DatePickerDialog.OnDateSetListener() {

                            public void onDateSet(DatePicker datepicker, int
                                    selectedYear, int selectedMonth, int selectedDay) {
                                mCurrentDate.set(Calendar.YEAR, selectedYear);
                                mCurrentDate.set(Calendar.MONTH, selectedMonth);
                                mCurrentDate.set(Calendar.DAY_OF_MONTH, selectedDay);

                                toDate.set(Calendar.YEAR, selectedYear);
                                toDate.set(Calendar.MONTH, selectedMonth);
                                toDate.set(Calendar.DAY_OF_MONTH, selectedDay);

                                requisitionDateEditText.setText((selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear);
                            }
                        }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        projectNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedProjectID = requisitionModel.getProjects().get(position).id;
                getAutoRequisitionNumber();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        compititionWaiver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    supplierNameEditText.setVisibility(View.VISIBLE);
                } else {
                    supplierNameEditText.setVisibility(View.GONE);
                }
            }
        });
    }

    private void getRequisitionJSON() {
        progress.show();
        String finalURL = Constants.GET_REQUISITION_INFO + "?username=" + Constants.USER_EMAIL;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                finalURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);

                            requisitionModel.setDept(obj.getString("dept"));
                            requisitionModel.setEmpCode(obj.getString("empCode"));
                            requisitionModel.setEmpName(obj.getString("empName"));
                            requisitionModel.setUserID(obj.getString("userID"));
                            requisitionModel.setUserName(obj.getString("usrName"));

                            JSONArray jsonArray = obj.getJSONArray("lstEntity");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String tempID = jsonObject.getString("ID");
                                String tempProjectName = jsonObject.getString("ProjectName");
                                requisitionModel.addProject(tempID, tempProjectName);
                            }

                            if (requisitionModel.getProjects().size() > 0)
                                selectedProjectID = requisitionModel.getProjects().get(0).id;

                            getAutoRequisitionNumber();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        setProjectDetails();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        Toast.makeText(RequisitionEntryActivity1.this, "Something went wrong! Please try again later",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        AppSingleton.getInstance(getApplicationContext())
                .addToRequestQueue(stringRequest, Constants.REQUEST_TAG);
    }

    public void getAutoRequisitionNumber() {
        String deptName = requisitionModel.getDept();
        String projectID = selectedProjectID;
        String requestLink = Constants.GET_REQUISITION_NUMBER + "?deptName=" + deptName + "&projectID=" + projectID;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                requestLink,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Replacing unwanted quotations
                        response = response.replaceAll("\"", "");
                        requisitionNumberTextView.setText(response);
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
        requisitionNumberTextView = findViewById(R.id.requisition_number_text_view);
        requisitionDateEditText = findViewById(R.id.requisition_date_edit_text);
        productTargetDateEditText = findViewById(R.id.product_delivery_date_edit_text);
        subjectEditText = findViewById(R.id.subject_edit_text);
        prRaiserEditText = findViewById(R.id.pr_raiser_edit_text);
        departmentEditText = findViewById(R.id.department_edit_text);
        justificationEditText = findViewById(R.id.justification_edit_text);
        projectNameSpinner = findViewById(R.id.project_name_spinner);
        isPostPR = findViewById(R.id.is_post_pr_checkbox);
        compititionWaiver = findViewById(R.id.compitition_waiver_checkbox);
        next = findViewById(R.id.next_button);
        supplierNameEditText = findViewById(R.id.supplier_name_text_view);

        mYear = mCurrentDate.get(Calendar.YEAR);
        mMonth = mCurrentDate.get(Calendar.MONTH);
        mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);

        requisitionDateEditText.setText((mMonth + 1) + "/" + mDay + "/" + mYear);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 10);

        String tempTargetDate = (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                +calendar.get(Calendar.YEAR);

        productTargetDateEditText.setText(tempTargetDate);
        progress = new ProgressDialog(this);
        progress.setMessage("Please Wait");

        toDate.add(Calendar.DAY_OF_MONTH, 10);

    }

    private void setProjectDetails() {
        prRaiserEditText.setText(requisitionModel.getEmpName());
        departmentEditText.setText(requisitionModel.getDept());

        // Adding project names to spinner
        List<String> projectSpinnerTexts = new ArrayList<>();
        for (int i = 0; i < requisitionModel.getProjects().size(); i++) {
            projectSpinnerTexts.add(requisitionModel.getProjects().get(i).projectName);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, projectSpinnerTexts);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        projectNameSpinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            Intent intent = new Intent(RequisitionEntryActivity1.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
