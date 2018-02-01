package br.senai.sp.informatica.listadeamigos.view;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.List;

import br.senai.sp.informatica.listadeamigos.R;
import br.senai.sp.informatica.listadeamigos.model.Amigo;
import br.senai.sp.informatica.listadeamigos.model.AmigoDao;

/**
 * Created by pena on 13/02/17.
 */

public class NotificationReceiver extends BroadcastReceiver {
    public static final int NOTIFICATION_ID = 0xBCA3ED;

    @Override
    public void onReceive(Context context, Intent intentEnvia) {
        // Obtem os aniversariantes do dia
        List<Amigo> lista = AmigoDao.dao.aniversariantesDoDia();

        Log.e("Notificação", "nº de aniversariantes: " + lista.size());

        // Testa se existem aniversariantes no dia de hoje
        if(lista.size() > 0) {
            // Criar um intente para chamar a Tela Pincipal quando o
            // alarme for clicado na tela de notificação
            Intent intent = new Intent(context, ListaActivity.class);

            PendingIntent pi = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            // Configuração da Notificação
            NotificationCompat.Builder nota = new NotificationCompat.Builder(context);
            nota.setContentIntent(pi);
            nota.addAction(R.drawable.ic_action_expand, "Ver", pi);
            nota.setContentInfo(String.valueOf(lista.size()));
            nota.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.meus_amigos_gd));
            nota.setSmallIcon(R.drawable.meus_amigos_pq);

            if(lista.size() == 1) {
                nota.setContentTitle("1 Aniversariante");
                nota.setContentText(lista.get(0).getNome());
            } else {
                nota.setContentTitle(lista.size() + " Aniversariantes");

                NotificationCompat.BigTextStyle texto = new NotificationCompat.BigTextStyle();

                String txt = "";
                for (Amigo obj :lista) {
                    txt += obj.getNome() + "\n";
                }
                texto.bigText(txt);
                nota.setStyle(texto);
            }

            nota.setWhen(System.currentTimeMillis());
            nota.setOnlyAlertOnce(true);
            nota.setOngoing(true);
            nota.setAutoCancel(true);
            nota.setVibrate(new long[] {0, 100, 200, 300});
            nota.setDefaults(Notification.DEFAULT_ALL);
            nota.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

            // Obtem o serviço de Notificação do Android
            NotificationManager manager =
                    (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

            // Envia a notificação
            manager.notify(NOTIFICATION_ID, nota.build());
        }
    }
}
