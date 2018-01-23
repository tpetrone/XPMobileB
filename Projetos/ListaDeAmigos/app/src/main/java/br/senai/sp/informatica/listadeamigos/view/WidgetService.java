package br.senai.sp.informatica.listadeamigos.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import br.senai.sp.informatica.listadeamigos.Main;
import br.senai.sp.informatica.listadeamigos.R;
import br.senai.sp.informatica.listadeamigos.lib.Utilitarios;
import br.senai.sp.informatica.listadeamigos.model.Amigo;
import br.senai.sp.informatica.listadeamigos.model.AmigoDao;

/**
 * Created by pena on 14/02/17.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsService.RemoteViewsFactory() {
            private List<Amigo> lista = new ArrayList<>();

            @Override
            public void onCreate() {
                lista = AmigoDao.dao.aniversariantesDoMes();
            }

            @Override
            public void onDataSetChanged() {
                lista = AmigoDao.dao.aniversariantesDoMes();
            }

            @Override
            public void onDestroy() {
                lista.clear();
            }

            @Override
            public int getCount() {
                return lista.size();
            }

            @Override
            public RemoteViews getLoadingView() {
                return null;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int pos) {
                return pos;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            @Override
            public RemoteViews getViewAt(int pos) {
                // Localiza o layout
                RemoteViews views = new RemoteViews(Main.getContext().getPackageName(), R.layout.amigo_widget);

                // Localiza o objeto referente a posição
                Amigo obj = lista.get(pos);

                // Carrega os campos da tela
                views.setTextViewText(R.id.tvNome, obj.getNome());
                views.setTextViewText(R.id.tvEmail, obj.getEmail());

                Bitmap bitmap;
                if (obj.getFoto() != null) {
                    bitmap = Utilitarios.bitmapFromBase64(obj.getFoto());
                    bitmap = Utilitarios.toCircularBitmap(bitmap);
                } else {
                    bitmap = Utilitarios.circularBitmapAndText(Color.GRAY, 200, 200,
                            obj.getNome().substring(0, 1).toUpperCase());
                }
                views.setImageViewBitmap(R.id.ivFoto, bitmap);

                // Cria o Intent para visualizar a chamada na Activity AmigoActivity
                Intent intent = new Intent();
                Bundle extras = new Bundle();
                extras.putLong("id", obj.getId());
                intent.putExtras(extras);

                // Vincular o intent no item da lista no widget
                views.setOnClickFillInIntent(R.id.detalhe_view, intent);

                return views;
            }
        };
    }
}