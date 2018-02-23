package br.senai.sp.informatica.exemploautenticacao.service;

import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import br.senai.sp.informatica.exemploautenticacao.receiver.NotificationReceiver;


 public class MessageService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        final String TAG = "MessageService";

        // Aciona o Receiver de Notificação
        Intent intent;

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.O) {
            intent = new Intent(getBaseContext(), NotificationReceiver.class);
            intent.setAction("br.senai.sp.informatica.exemploautenticacao.NOTIFICA_MENSAGEM");
        } else {
            intent = new Intent("br.senai.sp.informatica.exemploautenticacao.NOTIFICA_MENSAGEM");
        }

        intent.putExtra("chatUser", remoteMessage.getData().get("usuarioId"));
        intent.putExtra("msg", remoteMessage.getNotification().getBody());
        sendBroadcast(intent);

        Map<String, String> data = remoteMessage.getData();
        if (data.size() > 0) {
            for (String key : data.keySet()) {
                Log.e(TAG, key + " : " + data.get(key));
            }
        }

        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }
 }

