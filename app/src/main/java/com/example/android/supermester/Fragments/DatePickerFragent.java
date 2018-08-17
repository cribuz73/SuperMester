package com.example.android.supermester.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;


public class DatePickerFragent extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    int year = 0;
    int month = 0;
    int day = 0;

    public DatePickerFragent() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("pickedYear", year);
        editor.putInt("pickedMonth", month);
        editor.putInt("pickedDay", day);
        editor.apply();
        fragmentCallbacks.timeUpdate(year, month, day);
    }

    private FragmentCallbacks fragmentCallbacks;

    public interface FragmentCallbacks {
        void timeUpdate (int year, int month, int day);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentCallbacks = (FragmentCallbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement Fragment Two.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentCallbacks = null;
    }

}
