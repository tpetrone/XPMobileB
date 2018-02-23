package br.senai.sp.informatica.exemploautenticacao.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;

import br.senai.sp.informatica.exemploautenticacao.R;
import br.senai.sp.informatica.exemploautenticacao.lib.DataCalback;
import br.senai.sp.informatica.exemploautenticacao.lib.UsuarioChatArray;
import br.senai.sp.informatica.exemploautenticacao.model.Usuario;
import br.senai.sp.informatica.exemploautenticacao.model.UsuarioDao;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AdapterView.OnItemClickListener {
    private UsuarioDao dao = UsuarioDao.dao;

    private ListView listView;
    private UsuarioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("chatCom", "");
        editor.apply();

        if (adapter == null) {
            FirebaseListOptions<Usuario> options = new FirebaseListOptions.Builder<Usuario>()
                    .setSnapshotArray(new UsuarioChatArray())
                    .setLayout(R.layout.layout_usuario)
                    .setLifecycleOwner(this)
                    .build();

            adapter = new UsuarioAdapter(options);

            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            dao.atualizaLogon(false);
            adapter.stopListening();
            mAuth.signOut();

            // redirecionar para LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String destinatarioId = adapter.getItem(position).getId();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("chatCom", destinatarioId);
        editor.apply();

        dao.zeraMensagens(destinatarioId);

        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("destinatarioId", destinatarioId);

        startActivity(intent);
    }

    public class UsuarioAdapter extends FirebaseListAdapter<Usuario> {
        public UsuarioAdapter(@NonNull FirebaseListOptions<Usuario> options) {
            super(options);
            dao.verificaMensagens();
        }

        @Override
        protected void populateView(View view, Usuario model, int position) {
            hideProgressDialog();

            if (model.getId() != dao.getUserId()) {
                TextView tvUsuario = view.findViewById(R.id.tvUsuario);
                ImageView imgLogado = view.findViewById(R.id.imgLogado);
                final TextView tvContador = view.findViewById(R.id.tvContador);

                tvUsuario.setText(model.getEmail());
                if (model.isLogado()) {
                    imgLogado.setImageDrawable(getResources().getDrawable(R.drawable.verde));
                } else {
                    imgLogado.setImageDrawable(getResources().getDrawable(R.drawable.vermelho));
                }

                dao.getChatCount(model.getId(), new DataCalback(new DataCalback.OnDataChange() {
                    @Override
                    public void dataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            int total = dataSnapshot.getValue(Integer.class);

                            if(total > 0) {
                                tvContador.setText(String.valueOf(total));
                                tvContador.setVisibility(View.VISIBLE);
                            } else {
                                tvContador.setVisibility(View.GONE);
                            }
                        }
                    }
                }));
            }
        }
    }
}
