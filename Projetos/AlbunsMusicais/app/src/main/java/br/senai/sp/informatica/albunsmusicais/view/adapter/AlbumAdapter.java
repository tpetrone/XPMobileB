package br.senai.sp.informatica.albunsmusicais.view.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseLongArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.senai.sp.informatica.albunsmusicais.R;
import br.senai.sp.informatica.albunsmusicais.lib.Utilitarios;
import br.senai.sp.informatica.albunsmusicais.model.Album;
import br.senai.sp.informatica.albunsmusicais.model.AlbumDao;

/**
 * Created by pena on 18/11/2017.
 */

public class AlbumAdapter extends BaseAdapter implements View.OnClickListener {
    private AlbumDao dao = AlbumDao.instance;
    private SparseLongArray mapa;
    private boolean editar = false;

    public AlbumAdapter(Activity activity) {
        criarMapa();
    }

    public void setEditar(boolean value) {
        editar = value;
        notifyDataSetChanged();
    }

    private void criarMapa() {
        mapa = new SparseLongArray();
        List<Long> ids = dao.listarIds();
        for (int linha = 0; linha < ids.size(); linha++) {
            mapa.put(linha, ids.get(linha));
        }
    }

    @Override
    public void notifyDataSetChanged() {
        criarMapa();
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mapa.size();
    }

    @Override
    public Object getItem(int linha) {
        return dao.localizar(mapa.get(linha));
    }

    @Override
    public long getItemId(int linha) {
        return mapa.get(linha);
    }

    @Override
    public View getView(int linha, View view, ViewGroup viewGroup) {
        LinearLayout layout;

        if(view == null) {
            Context ctx = viewGroup.getContext();
            LayoutInflater svc = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = new LinearLayout(ctx);
            svc.inflate(R.layout.adapter_item, layout);
        } else {
            layout = (LinearLayout)view;
        }

        Album obj = dao.localizar(mapa.get(linha));

        TextView banda = layout.findViewById(R.id.tvBanda);
        banda.setText(obj.getBanda());

        TextView album = layout.findViewById(R.id.tvAlbum);
        album.setText(obj.getAlbum());

        TextView genero = layout.findViewById(R.id.tvGenero);
        genero.setText(obj.getGenero());

        TextView lancamento = layout.findViewById(R.id.tvLancamento);
        lancamento.setText(obj.getDataDeLancamento());

        Log.d("AlbumAdapter", "album: " + obj);

        ImageView capa = layout.findViewById(R.id.ivCapa);
        byte[] foto = obj.getCapa();
        if(foto != null) {
            // Transforma o vetor de bytes de base64 para bitmap
            Bitmap bitmap = Utilitarios.bitmapFromBase64(foto);
            // Cria uma foto circular e atribui à foto
            capa.setImageBitmap(bitmap);
        } else {
            // Obtem a 1ª letra do nome da pessoa e converte para Maiuscula
            String letra = obj.getBanda().substring(0, 1).toUpperCase();
            // Cria um bitmap contendo a letra
            Bitmap bitmap = Utilitarios.circularBitmapAndText(Color.parseColor("#936A4D"), 200, 200, letra);
            // atribui à foto
            capa.setBackgroundColor(Color.TRANSPARENT);
            capa.setImageBitmap(bitmap);
        }

        CheckBox apagar = layout.findViewById(R.id.checkBox);
        apagar.setTag(obj.getId());
        apagar.setChecked(obj.isDel());
        apagar.setOnClickListener(this);

        if(editar) {
            apagar.setVisibility(View.VISIBLE);
        } else {
            apagar.setVisibility(View.INVISIBLE);
        }

        return layout;
    }

    @Override
    public void onClick(View view) {
        Long id = (Long)view.getTag();
        Album album = dao.localizar(id);
        album.setDel(!album.isDel());
        dao.salvar(album);
    }
}
