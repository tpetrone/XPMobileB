package br.senai.sp.informatica.listadejogos.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import br.senai.sp.informatica.listadejogos.R;

public class MainActivity extends AppCompatActivity
    implements AdapterView.OnItemClickListener {
    private ListView listView;
    private BaseAdapter itemLista;
    private final int EDITA_JOGO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemLista = new JogoAdapter();

        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(itemLista);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int linha, long id) {
        Intent tela = new Intent(getBaseContext(), EditarCadastrar.class);
        tela.putExtra("id", id);
        startActivityForResult(tela, EDITA_JOGO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            itemLista.notifyDataSetChanged();
        }
    }
}
