package br.senai.sp.informatica.listadejogos.view;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import br.senai.sp.informatica.listadejogos.model.Jogo;
import br.senai.sp.informatica.listadejogos.model.JogoDao;

/**
 * Created by pena on 31/10/2017.
 */

class JogoAdapter extends BaseAdapter {
    private JogoDao dao = JogoDao.manager;

    @Override
    public int getCount() {
        return dao.getLista().size();
    }

    @Override
    public Object getItem(int id) {
        return dao.getJogo((long)id);
    }

    @Override
    public long getItemId(int pos) {
        // Como obter os dados por etapas
        List<Jogo> lista = dao.getLista();
        Jogo obj = lista.get(pos);
        long id = obj.getId();

        // como obter os dados com encadeamento
        return dao.getLista().get(pos).getId();
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {

        ConstraintLayout layout;
        if(view != null) {
            // localizar o serviço de construção do layout
            // informar o layout xml a ser carregado
            // iniciliazar o ConstraintLayout a partir da carga
        } else {
            layout = (ConstraintLayout)view;
        }

        // o registro da posição solicitada e encontrar o objeto
        // atribuir o objeto ao layout


        return layout;
    }
}
