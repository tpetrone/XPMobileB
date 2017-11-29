package br.senai.sp.informatica.albunsmusicais.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import br.senai.sp.informatica.albunsmusicais.R;
import br.senai.sp.informatica.albunsmusicais.model.AlbumDao;
import br.senai.sp.informatica.albunsmusicais.view.adapter.AlbumAdapter;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener, View.OnClickListener {
    private MenuItem itEdit;
    private MenuItem itDelete;
    private static int EDIT_ACTION = 0;
    private static int NEW_ACTION = 1;
    private AlbumAdapter albumAdapter;
    private AlbumDao dao = AlbumDao.instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        FloatingActionButton fab = findViewById(R.id.btAdd);
        fab.setOnClickListener(this);

        albumAdapter = new AlbumAdapter(this);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(albumAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        itEdit = menu.findItem(R.id.action_edit);
        itDelete = menu.findItem(R.id.action_delete);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_edit:
                albumAdapter.setEditar(true);
                itEdit.setVisible(false);
                itDelete.setVisible(true);
                break;
            case R.id.action_delete:
                if(dao.existeAlbunsADeletar()) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                    alerta.setMessage("Confirma a exclusão deste Amigo?");
                    alerta.setNegativeButton("Não", null);
                    alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dao.removerMarcados();
                            albumAdapter.notifyDataSetChanged();
                        }
                    });
                    alerta.create();
                    alerta.show();
                }

                albumAdapter.setEditar(false);
                itEdit.setVisible(true);
                itDelete.setVisible(false);
                break;
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        Intent tela = new Intent(getBaseContext(), EditActivity.class);
        startActivityForResult(tela, NEW_ACTION);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int linha, long id) {
        Intent tela = new Intent(getBaseContext(), EditActivity.class);
        tela.putExtra("id", id);
        startActivityForResult(tela, EDIT_ACTION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
           albumAdapter.notifyDataSetChanged();
    }
}
