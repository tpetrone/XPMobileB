package br.senai.sp.informatica.albunsmusicais.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import br.senai.sp.informatica.albunsmusicais.R;
import br.senai.sp.informatica.albunsmusicais.lib.Utilitarios;
import br.senai.sp.informatica.albunsmusicais.model.AlbumDao;
import br.senai.sp.informatica.albunsmusicais.view.adapter.AdapterInterface;
import br.senai.sp.informatica.albunsmusicais.view.adapter.AlbumAdapter;
import br.senai.sp.informatica.albunsmusicais.view.adapter.AlbumRecycledAdapter;
import br.senai.sp.informatica.albunsmusicais.view.adapter.OnItemClickListener;

 public class      MainActivity
        extends    AppCompatActivity
        implements AdapterView.OnItemClickListener, View.OnClickListener,
                   NavigationView.OnNavigationItemSelectedListener,
                   OnItemClickListener {

    private MenuItem itEdit;
    private MenuItem itDelete;
    // Atributos utilizados no Navigation Drawer
    private TextView tvNome;
    private TextView tvEmail;
    private ImageView ivFoto;

    private static int EDIT_ACTION = 0;
    private static int NEW_ACTION = 1;
    private static int PREF_ACTION = 2;

    private ListView listView;
    private RecyclerView recyclerView;

    private AdapterInterface adapter;
    private AlbumAdapter albumAdapter;
    private AlbumRecycledAdapter albumRecycledAdapter;

    private RecyclerView.LayoutManager layoutManager3;
    private RecyclerView.LayoutManager layoutManager5;

    private AlbumDao dao = AlbumDao.instance;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Inicializa o ListView
        albumAdapter = new AlbumAdapter(this);

        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        // Inicializa o RecyclerView
        albumRecycledAdapter = new AlbumRecycledAdapter(this, this);

        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        layoutManager3 = new GridLayoutManager(this, 3);
        layoutManager5 = new GridLayoutManager(this, 5);

        recyclerView.setHasFixedSize(true);
        setGridOrientation(recyclerView);

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

        // Inicializa os campos do Navigation Drawer
        View cabecalho = navigationView.getHeaderView(0);
        tvNome = (TextView)cabecalho.findViewById(R.id.tvNome);
        tvEmail = (TextView)cabecalho.findViewById(R.id.tvEmail);
        ivFoto = (ImageView)cabecalho.findViewById(R.id.ivFoto);
    }

    private void setGridOrientation(RecyclerView recyclerView) {
        int orientacao = getResources().getConfiguration().orientation;
        if(orientacao == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(layoutManager3);
        } else {
            recyclerView.setLayoutManager(layoutManager5);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(adapter instanceof RecyclerView)
            setGridOrientation(recyclerView);
    }

    @Override
    protected void onStart()  {
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

        // Obtém a identificação da preferência para Ordenação
        String listaPreference = getResources().getString(R.string.lista_key);
        // Obtém o valor padrão para a Ordenação
        String listaDefault = getResources().getString(R.string.lista_default);
        // Localiza a configuração selecionada para Ordenação de Albuns
        String tipo = preferences.getString(listaPreference, listaDefault);

        if(adapter != null)
            resetEditar();

        if(tipo.equals(listaDefault)) {
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(albumAdapter);

            recyclerView.setVisibility(View.INVISIBLE);
            recyclerView.setAdapter(null);

            adapter = albumAdapter;
        } else {
            listView.setVisibility(View.INVISIBLE);
            listView.setAdapter(null);

            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(albumRecycledAdapter);

            adapter = albumRecycledAdapter;
        }
        adapter.notificaAtualizacao();

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
                adapter.setEditar(true);
                itEdit.setVisible(false);
                itDelete.setVisible(true);
                break;
            case R.id.action_delete:
                if(dao.existeAlbunsADeletar()) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                    alerta.setMessage("Confirma a exclusão deste Amigo?");
                    alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dao.limpaMarcados();
                            adapter.notificaAtualizacao();
                        }
                    });
                    alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dao.removerMarcados();
                            adapter.notificaAtualizacao();
                        }
                    });
                    alerta.create();
                    alerta.show();
                }

                resetEditar();
                break;
        }

        return true;
    }

    private void resetEditar() {
        adapter.setEditar(false);
        itEdit.setVisible(true);
        itDelete.setVisible(false);
    }

    @Override
    public void onClick(View view) {
        Intent tela = new Intent(getBaseContext(), EditActivity.class);
        startActivityForResult(tela, NEW_ACTION);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int linha, long id) {
        editAlbum(id);
    }

    @Override
    public void onItemClick(long id) {
        editAlbum(id);
    }

    private void editAlbum(long id) {
        Intent tela = new Intent(getBaseContext(), EditActivity.class);
        tela.putExtra("id", id);
        startActivityForResult(tela, EDIT_ACTION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("MainActivity", "" + adapter.getClass().getName());
        if(resultCode == RESULT_OK)
           adapter.notificaAtualizacao();
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
