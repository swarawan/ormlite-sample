package com.swarawan.ormlitesample.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.swarawan.ormlitesample.R;
import com.swarawan.ormlitesample.adapter.MainAdapter;
import com.swarawan.ormlitesample.database.controller.EmployeeController;
import com.swarawan.ormlitesample.database.entity.Employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private EmployeeController employeeController = new EmployeeController(this);
    private MainAdapter adapter;
    private List<Employee> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        employees = new ArrayList<>();
        adapter = new MainAdapter(this, employees);
        adapter.setOnItemClickListener(onItemClickListener);

        ((RecyclerView) findViewById(R.id.employee_list)).setLayoutManager(new LinearLayoutManager(this));
        ((RecyclerView) findViewById(R.id.employee_list)).setHasFixedSize(true);
        ((RecyclerView) findViewById(R.id.employee_list)).setAdapter(adapter);
        findViewById(R.id.buttonSubmit).setOnClickListener(onSubmitClicked);

        getAllEmployees();
    }

    private View.OnClickListener onSubmitClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            submitEmployee();
        }
    };

    private MainAdapter.OnItemClickListener onItemClickListener = new MainAdapter.OnItemClickListener() {
        @Override
        public void onItemClicked(String id) {
        }
    };

    private void submitEmployee() {
        if (!checkValidationForm()) {
            Toast.makeText(this, "Form must not be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = ((EditText) findViewById(R.id.editName)).getText().toString();
        String address = ((EditText) findViewById(R.id.editAddress)).getText().toString();
        String phone = ((EditText) findViewById(R.id.editPhone)).getText().toString();

        Employee employee = new Employee();
        employee.setId(UUID.randomUUID().toString());
        employee.setName(name);
        employee.setAddress(address);
        employee.setPhone(phone);
        employee.setCreated(new Date());
        employee.setUpdated(new Date());

        employeeController.insert(employee);

        resetForm();
        getAllEmployees();
    }

    private boolean checkValidationForm() {
        if (((EditText) findViewById(R.id.editName)).getText().toString().equals(""))
            return false;
        if (((EditText) findViewById(R.id.editAddress)).getText().toString().equals(""))
            return false;
        if (((EditText) findViewById(R.id.editPhone)).getText().toString().equals(""))
            return false;
        return true;
    }

    private void resetForm() {
        ((EditText) findViewById(R.id.editName)).setText("");
        ((EditText) findViewById(R.id.editAddress)).setText("");
        ((EditText) findViewById(R.id.editPhone)).setText("");
    }

    private void getAllEmployees() {
        List<Employee> data = employeeController.getEmployees();
        if (data.size() > 0) {
            employees.clear();
            employees.addAll(data);
            adapter.notifyDataSetChanged();
        }
    }
}
