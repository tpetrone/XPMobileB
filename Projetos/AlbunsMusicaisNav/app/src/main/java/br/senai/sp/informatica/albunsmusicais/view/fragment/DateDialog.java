package br.senai.sp.informatica.albunsmusicais.view.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;

/**
 * Created by pena on 22/11/2017.
 */

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private Calendar calendar;
    private int editTextResource;
    private static DateFormat fmt =  DateFormat.getDateInstance(DateFormat.LONG);

    public static DateDialog makeDialog(Calendar calendar, int editTextResource) {
        DateDialog dialog = new DateDialog();
        dialog.calendar = calendar;
        dialog.editTextResource = editTextResource;
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(calendar == null) {
            long cal = savedInstanceState.getLong("cal");
            editTextResource = savedInstanceState.getInt("edit");
            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(cal);
        }

        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, ano, mes, dia);
        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int ano, int mes, int dia) {
        calendar.set(ano, mes, dia);
        EditText editText = (EditText)getActivity().findViewById(editTextResource);
        editText.setText(fmt.format(calendar.getTime()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("edit", editTextResource);
        outState.putLong("cal", calendar.getTimeInMillis());
        super.onSaveInstanceState(outState);
    }
}

