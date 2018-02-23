package br.senai.sp.informatica.exemploautenticacao.view;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.senai.sp.informatica.exemploautenticacao.R;
import br.senai.sp.informatica.exemploautenticacao.lib.DataCalback;
import br.senai.sp.informatica.exemploautenticacao.lib.MessageCallBack;
import br.senai.sp.informatica.exemploautenticacao.model.Mensagem;
import br.senai.sp.informatica.exemploautenticacao.model.MensagemDao;
import br.senai.sp.informatica.exemploautenticacao.model.UsuarioDao;

public class ChatActivity extends BaseActivity {
    private MensagemDao dao;

    private TextView tvMsg;
    private ListView listView;
    private MensagemAdapter adapter;
    private String destinatarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        tvMsg = findViewById(R.id.tvUsuario);
        listView = findViewById(R.id.listView);

        destinatarioId = getIntent().getStringExtra("destinatarioId");

        UsuarioDao.dao.localizaEmail(destinatarioId, new DataCalback(new DataCalback.OnDataChange() {
            @Override
            public void dataChange(DataSnapshot dataSnapshot) {
                String email = dataSnapshot.getValue(String.class);
                if (email != null) {
                    ChatActivity.this.setTitle(email);
                } else {
                    Toast.makeText(ChatActivity.this, "Falha ao obter o e-mail do contato",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }));

        dao = new MensagemDao(destinatarioId);

        ActionBar bar = getActionBar();
        if (bar != null) {
            bar.setHomeButtonEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (adapter == null) {
            FirebaseListOptions<Mensagem> options = new FirebaseListOptions.Builder<Mensagem>()
                    .setQuery(dao.getReference(), Mensagem.class)
                    .setLayout(R.layout.layout_mensagem)
                    .setLifecycleOwner(this)
                    .build();

            adapter = new MensagemAdapter(options);

            listView.setAdapter(adapter);
        }
    }

    public void mensagemClick(View view) {
        // Adicionar a mensagem ao firebase database
        showProgressDialog();

        Mensagem msg = new Mensagem();
        msg.setMensagem(tvMsg.getText().toString());
        msg.setData(new Date().getTime());

        dao.salvar(msg, new MessageCallBack(this, "Falha ao salvar a mensagem!"));

        tvMsg.setText("");
        view.requestFocus();
    }

    public class MensagemAdapter extends FirebaseListAdapter<Mensagem> {
        @SuppressLint("SimpleDateFormat")
        private SimpleDateFormat fmt = new SimpleDateFormat("'dia' dd 'as' HH:mm");

        public MensagemAdapter(@NonNull FirebaseListOptions<Mensagem> options) {
            super(options);
            dao.verificaMensagens();
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            Mensagem model = getItem(position);

            if (destinatarioId.equals(model.getOrigem())) {
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.layout_mensagem2, viewGroup, false);
            } else {
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(mLayout, viewGroup, false);
            }

            return super.getView(position, view, viewGroup);
        }

        @Override
        protected void populateView(View view, Mensagem model, int position) {
            hideProgressDialog();

            TextView tvMsg = view.findViewById(R.id.tvUsuario);
            TextView tvData = view.findViewById(R.id.tvData);

            tvMsg.setText(model.getMensagem());
            tvData.setText(fmt.format(new Date(model.getData())));
        }
    }

}
