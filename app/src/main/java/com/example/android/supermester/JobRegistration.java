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

import com.example.android.supermester.Fragments.StartDatePickerFragent;
import com.example.android.supermester.Fragments.StopDatePickerFragent;
import com.example.android.supermester.ItemClasses.Job;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JobRegistration extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener,
        StartDatePickerFragent.FragmentCallbacks,
        StopDatePickerFragent.FragmentCallbacks{

    @BindView(R.id.categories_spinner)
    Spinner categorySpinner;
    @BindView(R.id.edit_location)
    EditText imput_location;
    @BindView(R.id.date_button)
    Button input_date;
    @BindView(R.id.startDate)
    TextView startDate_tv;
    @BindView(R.id.stopDate)
    TextView stopDate_tv;
    private String category;
    private Date mCurrentDate;
    private Date dChosenStartDate;
    private Date dChosenStopDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_request);

        ButterKnife.bind(this);

        Calendar calendar = Calendar.getInstance();
        mCurrentDate = calendar.getTime();
        String currentDate = java.text.DateFormat.getDateInstance().format(mCurrentDate);
        startDate_tv.setText(currentDate);
        stopDate_tv.setText(currentDate);


        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this, R.array.categories_array, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinner_adapter);
        categorySpinner.setOnItemSelectedListener(this);
    }


    public void startDatePickerDialog(View v) {
        DialogFragment newFragment = new StartDatePickerFragent();
        newFragment.show(getSupportFragmentManager(), "startDatePicker");
        }

    public void stopDatePickerDialog(View v) {
        DialogFragment newFragment = new StopDatePickerFragent();
        newFragment.show(getSupportFragmentManager(), "stopDatePicker");
    }

    public void db_registration (View v){

        String description = " ";
        String city = " ";
        String country = " ";
        String address=imput_location.getText().toString();
        String zip_code= " ";

        int requestValue = 0;
        int validity_days = 0;
        boolean payment = true;
        String photo_URL = " ";
        String requester_id = getIntent().getStringExtra("userUID");
        List<String> tradesman_id= new ArrayList<String>();
        tradesman_id.add("aaa");



        Job job = new Job (category, description, city, country, address, zip_code,dChosenStartDate, dChosenStopDate ,requestValue, validity_days, payment, photo_URL, requester_id, tradesman_id  );
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("jobs").add(job);
        Toast.makeText(this, "Request registred !", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        Toast.makeText(this, "No category selected", Toast.LENGTH_SHORT).show();
    }

  //  private void pickedStartDate (){

     //   SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
     //   chosenStartYear = sharedPref.getInt("pickedYear", calendar.get(calendar.YEAR));
     //   chosenStartMonth = sharedPref.getInt("pickedMonth", calendar.get(calendar.MONTH));
     //   chosenStartDay = sharedPref.getInt("pickedDay", calendar.get(calendar.DAY_OF_MONTH));
     //   Calendar chosenStartDate = Calendar.getInstance();
     //   chosenStartDate.set(chosenStartYear,chosenStartMonth,chosenStartDay);
     //   dChosenStartDate = chosenStartDate.getTime();
     //   String startDate = java.text.DateFormat.getDateInstance().format(dChosenStartDate);
     //   startDate_tv.setText(startDate);

    //}
  //  private Date pickedStopDate (){

//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
 //       int chosenStopYear = sharedPref.getInt("pickedYear", calendar.get(calendar.YEAR));
 //       int chosenStopMonth = sharedPref.getInt("pickedMonth", calendar.get(calendar.MONTH));
 //       int chosenStopDay = sharedPref.getInt("pickedDay", calendar.get(calendar.DAY_OF_MONTH));
 //       Calendar chosenStopDate = Calendar.getInstance();
 //       chosenStopDate.set(chosenStopYear,chosenStopMonth,chosenStopDay);
 //       Date dChosenStopDate = chosenStopDate.getTime();
 //       String stopDate = java.text.DateFormat.getDateInstance().format(dChosenStopDate);
 //       stopDate_tv.setText(stopDate);
 //       return  dChosenStopDate;
  //  }


    @Override
    public void startTimeUpdate(int start_year, int start_month, int start_day) {
        Calendar chosenStartDate = Calendar.getInstance();
        chosenStartDate.set(start_year,start_month,start_day);
        dChosenStartDate = chosenStartDate.getTime();
        String startDate = java.text.DateFormat.getDateInstance().format(dChosenStartDate);
        startDate_tv.setText(startDate);
        if(dChosenStartDate.compareTo(mCurrentDate)<0 ){
            Toast.makeText(this, "Start Date has to be equal or greater than Current Date !", Toast.LENGTH_LONG).show();
            dChosenStartDate = mCurrentDate;
            startDate_tv.setText(java.text.DateFormat.getDateInstance().format(mCurrentDate));
        }
    }

    @Override
    public void stopTimeUpdate(int stop_year, int stop_month, int stop_day) {
        Calendar chosenStopDate = Calendar.getInstance();
        chosenStopDate.set(stop_year,stop_month,stop_day);
        dChosenStopDate = chosenStopDate.getTime();
        String stopDate = java.text.DateFormat.getDateInstance().format(dChosenStopDate);
        stopDate_tv.setText(stopDate);
        if(dChosenStopDate.compareTo(dChosenStartDate)<0 ){
            Toast.makeText(this, "Stop Date has to be equal or greater than Start Date !", Toast.LENGTH_LONG).show();
            dChosenStopDate = dChosenStartDate;
            stopDate_tv.setText(java.text.DateFormat.getDateInstance().format(dChosenStartDate));
        }
    }
}
