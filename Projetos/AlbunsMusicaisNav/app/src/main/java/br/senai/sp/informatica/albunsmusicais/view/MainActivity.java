package br.senai.sp.informatica.albunsmusicais.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import br.senai.sp.informatica.albunsmusicais.R;
import br.senai.sp.informatica.albunsmusicais.lib.Utilitarios;
import br.senai.sp.informatica.albunsmusicais.model.AlbumDao;
import br.senai.sp.informatica.albunsmusicais.view.adapter.AlbumAdapter;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener, View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {
    private MenuItem itEdit;
    private MenuItem itDelete;
    // Atributos utilizados no Navigation Drawer
    private TextView tvNome;
    private TextView tvEmail;
    private ImageView ivFoto;

    private static int EDIT_ACTION = 0;
    private static int NEW_ACTION = 1;
    private static int PREF_ACTION = 2;

    private AlbumAdapter albumAdapter;

    private AlbumDao dao = AlbumDao.instance;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Inicializa o ListView
        albumAdapter = new AlbumAdapter(this);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(albumAdapter);
        listView.setOnItemClickListener(this);

        // Configura o Float Action Button
        FloatingActionButton fab = findViewById(R.id.btAdd);
        fab.setOnClickListener(this);

        // Configuração do ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configuração no Menu de Navegação
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Registro dos menu para tratar as ações do menu de navegação
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Inicializa oa atributor do Navigation Drawer
        View cabecalho = navigationView.getHeaderView(0);
        tvNome = (TextView)cabecalho.findViewById(R.id.tvNome);
        tvEmail = (TextView)cabecalho.findViewById(R.id.tvEmail);
        ivFoto = (ImageView)cabecalho.findViewById(R.id.ivFoto);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Carrega as preferências nos atributos no Navigation Drawer
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        tvNome.setText(preferences.getString(UserActivity.NOME_USUARIO, ""));
        tvEmail.setText(preferences.getString(UserActivity.EMAIL_USUARIO, ""));

        String fotoString = preferences.getString(UserActivity.FOTO_USUARIO, null);
        if(fotoString != null) {
            Bitmap bitmap = Utilitarios.bitmapFromBase64(fotoString.getBytes());
            ivFoto.setImageBitmap(Utilitarios.toCircularBitmap(bitmap));
        }
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

    // Método que oculta o Menu de Navegação ao ser selecionada a ação de "Voltar (Back)" do Android
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    // Método que trata as ações do menu de navegação
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_pref:
                // Inicializa a Activity de Preferencias
                Intent tela = new Intent(getBaseContext(), PreferenciasActivity.class);
                startActivityForResult(tela, PREF_ACTION);
                break;
            case R.id.nav_perfil:
                Intent intent = new Intent(this, UserActivity.class);
                startActivity(intent);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
