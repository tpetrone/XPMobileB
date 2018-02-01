package br.senai.sp.informatica.listadeamigos.view;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import br.senai.sp.informatica.listadeamigos.R;
import br.senai.sp.informatica.listadeamigos.lib.Utilitarios;

/**
 * Created by pena on 26/01/17.
 */

 public class ListaActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener, View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    private static final int EDITA_AMIGO = 0;
    private static final int INCLUI_AMIGO = 1;
    private ListView listView;
    private BaseAdapter adapter;
    private FloatingActionButton btAdd;

    private ImageView ivFoto;
    private TextView tvNome;
    private TextView tvEmail;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        adapter = new AmigoAdapter(this);

        listView = (ListView)findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);

        btAdd = (FloatingActionButton)findViewById(R.id.btAdd);
        btAdd.setOnClickListener(this);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout)findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawer, toolbar,
                        R.string.abreNavegador, R.string.fechaNavegador);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        View cabecalho = navigationView.getHeaderView(0);
        ivFoto = (ImageView)cabecalho.findViewById(R.id.ivFoto);
        tvNome = (TextView)cabecalho.findViewById(R.id.tvNome);
        tvEmail = (TextView)cabecalho.findViewById(R.id.tvEmail);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Ler as preferências
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        tvNome.setText(preferences.getString(UserActivity.NOME_USUARIO, ""));
        tvEmail.setText(preferences.getString(UserActivity.EMAIL_USUARIO, ""));

        String fotoString = preferences.getString(UserActivity.FOTO_USUARIO, null);
        if(fotoString != null) {
            Bitmap bitmap = Utilitarios.bitmapFromBase64(fotoString.getBytes());
            ivFoto.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int linha, long id) {
        // Chama a Activity de Edição
        Intent intent = new Intent(this, AmigoActivity.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, EDITA_AMIGO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        // Chama a Activity de Inclusão
        Intent intent = new Intent(this, AmigoActivity.class);
        startActivityForResult(intent, INCLUI_AMIGO);
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            // Fecha o Drawer
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_user:
                Intent intent = new Intent(this, UserActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_alarme:
                Intent alarme = new Intent(this, AlarmeActivity.class);
                startActivity(alarme);
                break;
            default:
                finish();
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
