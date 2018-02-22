package br.senai.sp.informatica.exemploautenticacao;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private MensagemDao dao = MensagemDao.dao;

    private TextView tvMsg;
    private ListView listView;
    private MensagemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMsg = findViewById(R.id.tvMsg);
        listView = findViewById(R.id.listView);

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

        if(adapter == null) {
            FirebaseListOptions<Mensagem> options = new FirebaseListOptions.Builder<Mensagem>()
                    .setQuery(dao.getReference(), Mensagem.class)
                    .setLayout(R.layout.layout_mensagem)
                    .setLifecycleOwner(this)
                    .build();

            adapter = new MensagemAdapter(options);

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

    public void mensagemClick(View view) {
        // Adicionar a mensagem ao firebase database
        showProgressDialog();

        Mensagem msg = new Mensagem();
        msg.setMensagem(tvMsg.getText().toString());

        dao.salvar(msg, new CallBackMessage("Falha ao salvar a mensagem!"));

        tvMsg.setText("");
        view.requestFocus();
    }

    public class CallBackMessage implements DatabaseReference.CompletionListener {
        private String msg;

        public CallBackMessage(String msg) {
            this.msg = msg;
        }

        @Override
        public void onComplete(DatabaseError error, DatabaseReference reference) {
            if (error != null) {
                Toast.makeText(MainActivity.this, msg,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public class MensagemAdapter extends FirebaseListAdapter<Mensagem> {
        public MensagemAdapter(@NonNull FirebaseListOptions<Mensagem> options) {
            super(options);
            dao.verificaMensagens();
        }

        @Override
        protected void populateView(View view, Mensagem model, int position) {
            hideProgressDialog();

            TextView tvMsg = view.findViewById(R.id.tvMsg);
            ImageView imgDel = view.findViewById(R.id.imgDel);

            tvMsg.setText(model.getMensagem());
            imgDel.setTag(model.getId());
            imgDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                    alerta.setMessage("Confirma a exclusão desta Mensagem?");
                    alerta.setNegativeButton("Não", null);
                    alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dao.remover((String)view.getTag(),
                                    new CallBackMessage("Falha ao excluir a mensagem!"));
                        }
                    });
                    alerta.create();
                    alerta.show();

                }
            });
        }
    }
}
