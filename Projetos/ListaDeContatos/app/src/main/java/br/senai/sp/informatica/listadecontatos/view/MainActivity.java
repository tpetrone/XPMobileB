package br.senai.sp.informatica.listadecontatos.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import br.senai.sp.informatica.listadecontatos.R;

public class MainActivity extends AppCompatActivity
    implements AdapterView.OnItemClickListener {
    private ListView listView;
    private BaseAdapter itemLista;
    // Identificação para a chamada a Activity EditarCadastrar
    private final int EDITA_CONTATO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cria o Adapter para que ele forneça os dados para o ListView
        itemLista = new ContatoAdapter();

        listView = (ListView)findViewById(R.id.listView);
        // Registra o adapter no ListView
        listView.setAdapter(itemLista);
        // Solicita ao listView que qualquer click em um item do listView
        // será redirecionado ao médoto "onItemClick" desta classe (MainActivity)
        listView.setOnItemClickListener(this);
    }

    /*
        Este método trata da ação do click nos itens da lista
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int linha, long id) {
        // é criado um Intent para definir ao Android qual Activity será chamada
        Intent tela = new Intent(getBaseContext(), EditarCadastrar.class);
        // é passado o ID do objeto para a nova Activity a fim de informar
        // qual Contato deverá ser editado
        tela.putExtra("id", id);
        // é solicitado ao Android que seja iniciada a execução de uma nova Activity
        // porém também é solicitado informar quando esta activity retornar
        startActivityForResult(tela, EDITA_CONTATO);
    }

    /*
        Este método trata da ação do retorno de uma Activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Se a activity informar que houve sucesso em sua execução
        if(resultCode == RESULT_OK) {
            // é solicitado ao Adapter que os itens da lista sejam atualizados
            // no listView
            itemLista.notifyDataSetChanged();
        }
    }
}
