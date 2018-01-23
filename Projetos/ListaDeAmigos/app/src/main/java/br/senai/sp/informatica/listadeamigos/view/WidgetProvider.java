package br.senai.sp.informatica.listadeamigos.view;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import br.senai.sp.informatica.listadeamigos.R;

/**
 * Created by pena on 14/02/17.
 */

public class WidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        Intent service = new Intent(context, WidgetService.class);
        service.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        service.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        views.setRemoteAdapter(R.id.widget_lista, service);

        Intent intent = new Intent(context, AmigoActivity.class);
        intent.setAction("br.senai.sp.informatica.listadeamigos.CHAMA_WIDGET");
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        views.setPendingIntentTemplate(R.id.widget_lista, pi);

        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("br.senai.sp.informatica.listadeamigos.ATUALIZA_AMIGOS")) {
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            ComponentName name = new ComponentName(context, WidgetProvider.class);
            manager.notifyAppWidgetViewDataChanged(manager.getAppWidgetIds(name), R.id.widget_lista);
        }

        super.onReceive(context, intent);
    }
}
