package br.senai.sp.informatica.listadeamigos.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by pena on 29/11/16.
 */

@SuppressLint({"SimpleDateFormat","DefaultLocale"})
public class TimePreference extends DialogPreference {
    private int hora = 0;
    private int minuto = 0;
    private String time;
    private CharSequence mSummary;
    private TimePicker picker = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    public static int getHora(String data) {
        String[] pieces = data.split(":");
        return (Integer.parseInt(pieces[0]));
    }

    public static int getMinuto(String data) {
        String[] pieces = data.split(":");
        return (Integer.parseInt(pieces[1]));
    }

    public TimePreference(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);

        setPositiveButtonText("Ok");
        setNegativeButtonText("Cancela");

        if(getSummary() == null) {
            setSummary(sdf.format(new Date()));
        }
    }

    @Override
    protected View onCreateDialogView() {
        picker = new TimePicker(getContext());
        picker.setIs24HourView(true);

        return (picker);
    }

    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);

        if(mSummary != null) {
            hora = getHora(mSummary.toString());
            minuto = getMinuto(mSummary.toString());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            picker.setHour(hora);
            picker.setMinute(minuto);
        } else {
            picker.setCurrentHour(hora);
            picker.setCurrentMinute(minuto);
        }
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hora = picker.getHour();
                minuto = picker.getMinute();
            } else {
                hora = picker.getCurrentHour();
                minuto = picker.getCurrentMinute();
            }

            String timeval = String.format("%02d:%02d", hora, minuto);

            setSummary(timeval);

            if (callChangeListener(timeval)) {
                persistString(timeval);
            }
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return (a.getString(index));
    }


    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        time = null;

        if (restoreValue) {
            if (defaultValue == null) {
                Calendar cal = Calendar.getInstance();
                String formatted = sdf.format(cal.getTime());
                time = getPersistedString(formatted);
            } else {
                time = getPersistedString(defaultValue.toString());
            }
        } else {
            time = defaultValue.toString();
        }

        hora = getHora(time);
        minuto = getMinuto(time);

        setSummary(time);
    }

    public void setText(String text) {
        final boolean wasBlocking = shouldDisableDependents();

        time = text;

        persistString(text);

        final boolean isBlocking = shouldDisableDependents();
        if (isBlocking != wasBlocking) {
            notifyDependencyChange(isBlocking);
        }
    }

    public String getText() {
        return time;
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
