package br.senai.sp.informatica.listadeamigos.view;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.senai.sp.informatica.listadeamigos.R;

/**
 * Created by pena on 13/02/17.
 */

public class AlarmeActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarme_activity);

        ImageButton btSair = (ImageButton)findViewById(R.id.btSair);
        btSair.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            criaAlarme();
            finish();
        } catch (AlarmeException ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void criaAlarme() throws AlarmeException {
        // Obter os valores das chaves das preferências
        String data_key = getResources().getString(R.string.data_alarme);
        String hora_key = getResources().getString(R.string.hora_alarme);
        String ativa_key = getResources().getString(R.string.ativa_alarme);

        // Obter os valores associados às chaves das preferências
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String data = preferences.getString(data_key, null);
        String hora = preferences.getString(hora_key, null);
        boolean ativar = preferences.getBoolean(ativa_key, false);

        // Configurar um Intent para ser chamado pelo Alarme
        Intent intent = new Intent(getBaseContext(), NotificationReceiver.class);
        intent.setAction("br.senai.sp.informatica.listadeamigos.NOTIFICA_ALARME");
        PendingIntent pi = PendingIntent.getBroadcast(getBaseContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Obter o Gerenciador de Alarmes do Android
        AlarmManager alarme = (AlarmManager)getBaseContext().getSystemService(Context.ALARM_SERVICE);

        // Cancela o Alarme anterior
        alarme.cancel(pi);

        // Determinar o dia e a hora para ativar o alarme
        if(ativar) {
            // Ativa Alarme

            if (data != null && hora != null) {
                // Data OK - Ativa o alarme
                Calendar hoje = Calendar.getInstance();
                Calendar dataDoAlarme = Calendar.getInstance();
                dataDoAlarme.setTimeInMillis(
                        new SimpleDateFormat("dd/MM/yyyyHH:mm")
                                .parse(data + hora, new ParsePosition(0))
                                .getTime());

                if (dataDoAlarme.after(hoje)) {
                    // Cria o Alarme
                    alarme.setRepeating(AlarmManager.RTC_WAKEUP, dataDoAlarme.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY, pi);

                    Log.e("Alarme", "data: " + new SimpleDateFormat("dd/MM/yyyy HH:mm")
                            .format(dataDoAlarme.getTime()));
                } else {
                    // Data anterior a atual não Ativa Alarme
                    throw new AlarmeException("A data informada é anterior a data atual");
                }
            } else {
                // Data Inválida não ativa Alarme
                throw new AlarmeException("A data é inválida");
            }
        }
    }
}
