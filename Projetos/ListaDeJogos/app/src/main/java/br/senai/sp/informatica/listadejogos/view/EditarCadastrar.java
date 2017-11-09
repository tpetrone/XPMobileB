package br.senai.sp.informatica.listadejogos.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.senai.sp.informatica.listadejogos.R;
import br.senai.sp.informatica.listadejogos.model.Jogo;
import br.senai.sp.informatica.listadejogos.model.JogoDao;

/**
 * Created by pena on 07/11/2017.
 *
 * Esta classe é utilizada na criação ou alteração de um Jogo
 */

public class EditarCadastrar extends AppCompatActivity {
    // Referência do JogoDao a ser utilizada na activity
    private JogoDao dao = JogoDao.manager;
    private EditText edJogo;
    private EditText edGenero;
    // Referência do objeto Jogo que está em edição no momento
    private Jogo jogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jogo_layout);

        edJogo = findViewById(R.id.edJogo);
        edGenero = findViewById(R.id.edGenero);

        Intent intent = getIntent();
        if(intent != null) {
            Bundle dados = intent.getExtras();
            if(dados != null) {
                long id = dados.getLong("id");
                jogo = dao.getJogo(id);
                if(jogo != null) {
                    edJogo.setText(jogo.getNome());
                    edGenero.setText(jogo.getGenero());
                }
            }
        }

        ActionBar actionBar = getActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                setResult(Activity.RESULT_CANCELED);
                break;
            case R.id.acao_salvar:
                if(jogo == null) {
                    jogo = new Jogo();
                }

                jogo.setNome(edJogo.getText().toString());
                jogo.setGenero(edGenero.getText().toString());

                dao.salvar(jogo);

                setResult(Activity.RESULT_OK);
                break;
        }

        finish();

        return true;
    }
}
