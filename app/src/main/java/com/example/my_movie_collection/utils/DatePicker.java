package com.example.my_movie_collection.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.my_movie_collection.interfaces.DateListener;

import java.util.Calendar;

public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DateListener dateListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        return new DatePickerDialog(getContext(), this, year, month, day);
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        String d = String.valueOf(dayOfMonth);
        String m = String.valueOf(month + 1);
        String y = String.valueOf(year);

        String date = d.concat("/").concat(m).concat("/").concat(y);

        dateListener.getDate(date);
    }

    public void setDateListener(DateListener dateListener) {
        this.dateListener = dateListener;
    }
}
