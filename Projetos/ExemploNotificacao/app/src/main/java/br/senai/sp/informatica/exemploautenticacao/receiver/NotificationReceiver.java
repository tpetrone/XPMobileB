package br.senai.sp.informatica.exemploautenticacao.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import br.senai.sp.informatica.exemploautenticacao.R;
import br.senai.sp.informatica.exemploautenticacao.model.UsuarioDao;
import br.senai.sp.informatica.exemploautenticacao.view.LoginActivity;


 public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if(extras != null) {
            String chatUser = extras.getString("chatUser");
            String msg = extras.getString("msg");

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String status = preferences.getString("status", "offline");
            String chatCom = preferences.getString("chatCom", "");

            Log.e("NotificationReceiver", "status: " + status);
            Log.e("NotificationReceiver", "chat com: " + chatCom);

            if (status.equals("online")) {
                if (!chatCom.equals(chatUser)) {
                    UsuarioDao.dao.incrementaMensagens(chatUser);
                }
            }
        }
    }
 }

