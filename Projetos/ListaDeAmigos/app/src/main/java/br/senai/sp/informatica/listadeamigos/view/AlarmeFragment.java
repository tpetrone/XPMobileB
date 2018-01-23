package br.senai.sp.informatica.listadeamigos.view;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import br.senai.sp.informatica.listadeamigos.R;

/**
 * Created by pena on 13/02/17.
 */

public class AlarmeFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.alarme_preferences);
    }
}
