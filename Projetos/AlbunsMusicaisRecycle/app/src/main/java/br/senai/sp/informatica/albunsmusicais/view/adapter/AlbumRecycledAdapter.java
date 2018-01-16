package br.senai.sp.informatica.albunsmusicais.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseLongArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.senai.sp.informatica.albunsmusicais.R;
import br.senai.sp.informatica.albunsmusicais.lib.Utilitarios;
import br.senai.sp.informatica.albunsmusicais.model.Album;
import br.senai.sp.informatica.albunsmusicais.model.AlbumDao;
import br.senai.sp.informatica.albunsmusicais.view.EditActivity;
import br.senai.sp.informatica.albunsmusicais.view.MainActivity;

/**
 * Created by pena on 22/12/2017.
 */

 public class      AlbumRecycledAdapter
        extends    RecyclerView.Adapter<AlbumRecycledAdapter.AlbumViewHolder>
        implements AdapterInterface {

    private AlbumDao dao = AlbumDao.instance;
    private SparseLongArray mapa;
    private Activity activity;
    private OnItemClickListener listener;
    private boolean editar = false;

    public AlbumRecycledAdapter(Activity activity, OnItemClickListener listener) {
        this.activity = activity;
        this.listener = listener;
        criarMapa();
    }

    @Override
    public void setEditar(boolean value) {
        editar = value;
        notificaAtualizacao();
    }

    @Override
    public void notificaAtualizacao() {
        criarMapa();
        notifyDataSetChanged();
    }

    private void criarMapa() {
        // Obtém a identificação da preferência para Ordenação
        String ordemPreference = activity.getResources().getString(R.string.ordem_key);
        // Obtém o valor padrão para a Ordenação
        String ordemDefault = activity.getResources().getString(R.string.ordem_default);
        // Obtém o recurso de leitura de preferências
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        // Localiza a configuração selecionada para Ordenação de Albuns
        String ordem = preferences.getString(ordemPreference, ordemDefault);

        mapa = new SparseLongArray();
        List<Long> ids = dao.listarIds(ordem);
        for (int linha = 0; linha < ids.size(); linha++) {
            mapa.put(linha, ids.get(linha));
        }
    }

    @Override
    public int getItemCount() {
        return mapa.size();
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater svc = LayoutInflater.from(parent.getContext());
        View layout = svc.inflate(R.layout.adapter_card, parent, false);
        return new AlbumViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder viewHolder, int linha) {
        Album obj = dao.localizar(mapa.get(linha));
        viewHolder.setView(obj);
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView capa;
        private TextView banda;
        private TextView album;
        private CheckBox apagar;
        private View view;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            banda = itemView.findViewById(R.id.tvBanda);
            album = itemView.findViewById(R.id.tvAlbum);
            capa = itemView.findViewById(R.id.ivFoto);
            apagar = itemView.findViewById(R.id.checkBox);
        }

        public void setView(final Album obj) {
            banda.setText(obj.getBanda());
            album.setText(obj.getAlbum());

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
                Bitmap bitmap = Utilitarios.circularBitmapAndText(
                        Color.parseColor("#936A4D"), 200, 200, letra);
                // atribui à foto
                capa.setBackgroundColor(Color.TRANSPARENT);
                capa.setImageBitmap(bitmap);
            }

            apagar.setTag(obj.getId());
            apagar.setChecked(obj.isDel());
            apagar.setOnClickListener(this);

            if(editar) {
                apagar.setVisibility(View.VISIBLE);
            } else {
                apagar.setVisibility(View.INVISIBLE);
            }

            Log.d("AlbumViewHolder", "album: " + obj);

            view.setTag(obj.getId());
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Long id = (Long)view.getTag();
            if(view instanceof CheckBox) {
                Album album = dao.localizar(id);
                album.setDel(!album.isDel());
                dao.salvar(album);
            } else {
                listener.onItemClick(id);
            }
        }
    }

 }

