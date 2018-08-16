package com.example.android.supermester;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.supermester.Fragments.DatePickerFragent;
import com.example.android.supermester.ItemClasses.Job;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JobRegistration extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

@BindView(R.id.categories_spinner)
    Spinner categorySpinner;
@BindView(R.id.edit_location)
    EditText imput_location;
    @BindView(R.id.date_button)
    Button input_date;
    @BindView(R.id.maxDate)
    TextView maxDate_tv;
private String category;
private String currentDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_request);

        ButterKnife.bind(this);

        Calendar calendar = Calendar.getInstance();
        currentDate = java.text.DateFormat.getDateInstance().format(calendar.getTime());
        maxDate_tv.setText(currentDate);

        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this, R.array.categories_array, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinner_adapter);
        categorySpinner.setOnItemSelectedListener(this);


    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragent();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void db_registration (View v){

        String description = " ";
        String city = " ";
        String country = " ";
        String address=imput_location.getText().toString();
        String zip_code= " ";
        int validity_days = 0;
        boolean payment = true;
        String photo_URL = " ";
        String requester_id = " ";
        String tradesman_id= " ";

        Job job = new Job (category, description, city, country, address, zip_code, currentDate, validity_days, payment, photo_URL, requester_id, tradesman_id  );
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("jobs").add(job);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        Toast.makeText(this, "No category selected", Toast.LENGTH_SHORT).show();
    }
}
