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

 public class DateDialog extends DialogFragment {
    private View view;
    private Calendar calendar;
    private EditText editText;
    private static DateFormat fmt =  DateFormat.getDateInstance(DateFormat.LONG);

    public static DateDialog makeDialog(View view, Calendar calendar, EditText editText) {
        DateDialog dialog = new DateDialog();
        dialog.setView(view);
        dialog.setCalendar(calendar);
        dialog.setEditText(editText);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int ano, int mes, int dia) {
                calendar.set(ano, mes, dia);
                editText.setText(fmt.format(calendar.getTime()));
            }
        };

        try {
            calendar.setTime(fmt.parse(editText.getText().toString()));
        } catch (ParseException ex) {
        }

        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(view.getContext(), listener, ano, mes, dia);
        return dialog;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }
 }

