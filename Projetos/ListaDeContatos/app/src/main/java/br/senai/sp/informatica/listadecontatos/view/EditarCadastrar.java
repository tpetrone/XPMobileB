package br.senai.sp.informatica.listadecontatos.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.senai.sp.informatica.listadecontatos.R;
import br.senai.sp.informatica.listadecontatos.model.Contato;
import br.senai.sp.informatica.listadecontatos.model.ContatoDao;

/**
 * Created by pena on 07/11/2017.
 *
 * Esta classe é utilizada na criação ou alteração de um Contato
 */

public class EditarCadastrar extends AppCompatActivity {
    // Referência do ContatoDao a ser utilizada na activity
    private ContatoDao dao = ContatoDao.manager;
    private EditText edNome;
    private EditText edEmail;
    // Referência do objeto Contato que está em edição no momento
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contato_layout);

        edNome = (EditText)findViewById(R.id.edNome);
        edEmail = (EditText)findViewById(R.id.edEmail);

        Intent intent = getIntent();
        if(intent != null) {
            Bundle dados = intent.getExtras();
            if(dados != null) {
                long id = dados.getLong("id");
                contato = dao.getContato(id);
                if(contato != null) {
                    edNome.setText(contato.getNome());
                    edEmail.setText(contato.getEmail());
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
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                setResult(Activity.RESULT_CANCELED);
                break;
            case R.id.salvar:
                if(contato == null) {
                    contato = new Contato();
                }

                contato.setNome(edNome.getText().toString());
                contato.setEmail(edEmail.getText().toString());

                dao.salvar(contato);

                setResult(Activity.RESULT_OK);
                break;
        }

        finish();

        return true;
    }
}
