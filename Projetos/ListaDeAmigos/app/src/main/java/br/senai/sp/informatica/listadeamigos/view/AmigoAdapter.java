package br.senai.sp.informatica.listadeamigos.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;

import br.senai.sp.informatica.listadeamigos.R;
import br.senai.sp.informatica.listadeamigos.lib.Utilitarios;
import br.senai.sp.informatica.listadeamigos.model.Amigo;
import br.senai.sp.informatica.listadeamigos.model.AmigoDao;

/**
 * Created by pena on 26/01/17.
 */
public class AmigoAdapter extends BaseAdapter
        implements View.OnClickListener, DialogInterface.OnClickListener {
    private SparseLongArray mapa;
    private Activity activity;
    private Long idApagar;

    public AmigoAdapter(Activity activity) {
        this.activity = activity;
        criaMapa();
    }

    @Override
    public void notifyDataSetChanged() {
        criaMapa();
        super.notifyDataSetChanged();
    }

    private void criaMapa() {
        mapa = new SparseLongArray();
        int linha = 0;
        for(Long id : AmigoDao.dao.listar()) {
            mapa.put(linha++, id);
        }
    }

    @Override
    public int getCount() {
        return mapa.size();
    }

    @Override
    public Object getItem(int id) {
        return AmigoDao.dao.localizar(id);
    }

    @Override
    public long getItemId(int linha) {
        return mapa.get(linha);
    }

    @Override
    public View getView(int linha, View view, ViewGroup viewGroup) {
        // Utilizar LinearLayout ao invés de ConstraintLayout,
        // caso contrário nada será apresentado no ListView
        // Constroi/Localiza o Layout
        LinearLayout layout;

        if(view == null) {
            // Criar o layout
            Context ctx = viewGroup.getContext();
            layout = new LinearLayout(ctx);
            LayoutInflater svc = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            svc.inflate(R.layout.amigo_detalhe, layout, true);
        } else {
            // Usar o layout
            layout = (LinearLayout)view;
        }

        // Localiza o Objeto Amigo a partir da linha informada
        long id = mapa.get(linha);
        Amigo obj = AmigoDao.dao.localizar(id);

        // Carregando os dados do Objeto no Layout
        TextView tvNome = (TextView) layout.findViewById(R.id.tvNome);
        tvNome.setText(obj.getNome());

        TextView tvEmail = (TextView) layout.findViewById(R.id.tvEmail);
        tvEmail.setText(obj.getEmail());

        ImageButton btDel = (ImageButton)layout.findViewById(R.id.btDel);
        btDel.setOnClickListener(this);
        btDel.setTag(obj.getId()); // associa o ID do Amigo ao Botão Del

        ImageView ivFoto = (ImageView)layout.findViewById(R.id.ivFoto);
        byte[] foto = obj.getFoto();
        if(foto != null) {
            // Transforma o vetor de bytes de base64 para bitmap
            Bitmap bitmap = Utilitarios.bitmapFromBase64(foto);
            // Cria uma foto circular e atribui à foto
            ivFoto.setImageBitmap(Utilitarios.toCircularBitmap(bitmap));
        } else {
            // Obtem a 1ª letra do nome da pessoa e converte para Maiuscula
            String letra = obj.getNome().substring(0, 1).toUpperCase();
            // Cria um bitmap contendo a letra
            Bitmap bitmap = Utilitarios.circularBitmapAndText(Color.parseColor("#936A4D"), 200, 200, letra);
            // atribui à foto
            ivFoto.setImageBitmap(bitmap);
        }

        return layout;
    }

    @Override
    public void onClick(View view) {
        idApagar = (Long)view.getTag();
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setMessage("Confirma a exclusão deste Amigo?");
        alerta.setNegativeButton("Não", null);
        alerta.setPositiveButton("Sim", this);
        alerta.create();
        alerta.show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int botao) {
        AmigoDao.dao.remover(idApagar);
//-->>   activity.sendBroadcast(
//           new Intent("br.senai.sp.informatica.listadeamigos.ATUALIZA_AMIGOS"));
        notifyDataSetChanged();
    }
}
