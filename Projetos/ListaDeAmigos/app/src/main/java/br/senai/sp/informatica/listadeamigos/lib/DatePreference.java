package br.senai.sp.informatica.listadeamigos.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by pena on 29/11/16.
 */

@SuppressLint({"SimpleDateFormat","DefaultLocale"})
public class DatePreference extends DialogPreference {
    private int dia = 0;
    private int mes = 0;
    private int ano = 0;
    private String data;
    private CharSequence mSummary;
    private DatePicker picker = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static int getDia(String data) {
        String[] pieces = data.split("/");
        return (Integer.parseInt(pieces[0]));
    }

    public static int getMes(String data) {
        String[] pieces = data.split("/");
        return (Integer.parseInt(pieces[1]));
    }

    public static int getAno(String data) {
        String[] pieces = data.split("/");
        return (Integer.parseInt(pieces[2]));
    }

    public DatePreference(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);

        setPositiveButtonText("Ok");
        setNegativeButtonText("Cancela");

        if(getSummary() == null) {
            setSummary(sdf.format(new Date()));
        }
    }

    @Override
    protected View onCreateDialogView() {
        picker = new DatePicker(getContext());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            picker.setCalendarViewShown(false);
        }

        return (picker);
    }

    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);

        if(mSummary != null) {
            ano = getAno(mSummary.toString());
            mes = getMes(mSummary.toString());
            dia = getDia(mSummary.toString());
        }

        picker.updateDate(ano, mes-1, dia);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            ano = picker.getYear();
            mes = picker.getMonth()+1;
            dia = picker.getDayOfMonth();

            String dateval = String.format("%02d/%02d/%04d", dia, mes, ano);

            setSummary(dateval);

            if (callChangeListener(dateval)) {
                persistString(dateval);
            }
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return (a.getString(index));
    }


    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        data = null;

        if (restoreValue) {
            if (defaultValue == null) {
                Calendar cal = Calendar.getInstance();
                String formatted = sdf.format(cal.getTime());
                data = getPersistedString(formatted);
            } else {
                data = getPersistedString(defaultValue.toString());
            }
        } else {
            data = defaultValue.toString();
        }
        ano = getAno(data);
        mes = getMes(data);
        dia = getDia(data);

        setSummary(data);
    }

    public void setText(String text) {
        final boolean wasBlocking = shouldDisableDependents();

        data = text;

        persistString(text);

        final boolean isBlocking = shouldDisableDependents();
        if (isBlocking != wasBlocking) {
            notifyDependencyChange(isBlocking);
        }
    }

    public String getText() {
        return data;
    }

    public CharSequence getSummary() {
        return mSummary;
    }

    public void setSummary(CharSequence summary) {
        if (summary == null && mSummary != null || summary != null
                && !summary.equals(mSummary)) {
            mSummary = summary;
            notifyChanged();
        }
    }
}
