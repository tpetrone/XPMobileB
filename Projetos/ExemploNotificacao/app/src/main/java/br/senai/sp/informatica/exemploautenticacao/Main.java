package br.senai.sp.informatica.exemploautenticacao;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


 public class Main extends Application {
    private void offline() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("status", "offline");
        editor.apply();
    }

    @Override
    public void onTerminate() {
        offline();
        super.onTerminate();
    }


    @Override
    public void onLowMemory() {
        offline();
        super.onLowMemory();
    }
 }

