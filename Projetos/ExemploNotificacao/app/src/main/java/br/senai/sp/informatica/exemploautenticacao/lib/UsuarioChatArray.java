package br.senai.sp.informatica.exemploautenticacao.lib;

import android.support.annotation.NonNull;

import com.firebase.ui.database.FirebaseArray;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;

import br.senai.sp.informatica.exemploautenticacao.model.Usuario;
import br.senai.sp.informatica.exemploautenticacao.model.UsuarioDao;


 public class UsuarioChatArray extends FirebaseArray<Usuario> {
    private UsuarioDao dao = UsuarioDao.dao;

    public UsuarioChatArray() {
        super(UsuarioDao.dao.getReference(),
                new SnapshotParser<Usuario>() {
                    public Usuario parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return snapshot.getValue(Usuario.class);
                    }
                }
        );
    }

    public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
        new EventManager() {
            @Override
            public void superEvent(DataSnapshot snapshot, String previousChildKey) {
                UsuarioChatArray.super.onChildAdded(snapshot, previousChildKey);
            }
        }.processEvent(snapshot, previousChildKey);
    }

    @Override
    public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {
        new EventManager() {
            @Override
            public void superEvent(DataSnapshot snapshot, String previousChildKey) {
                UsuarioChatArray.super.onChildChanged(snapshot, previousChildKey);
            }
        }.processEvent(snapshot, previousChildKey);
    }

    @Override
    public void onChildMoved(DataSnapshot snapshot, String previousChildKey) {
        new EventManager() {
            @Override
            public void superEvent(DataSnapshot snapshot, String previousChildKey) {
                UsuarioChatArray.super.onChildMoved(snapshot, previousChildKey);
            }
        }.processEvent(snapshot, previousChildKey);

    }

    private abstract class EventManager {
        private String chavePreviaAdicionada;

        protected void processEvent(DataSnapshot snapshot, String previousChildKey) {
            if (previousChildKey == null) chavePreviaAdicionada = null;

            if (!snapshot.getKey().equals(dao.getUserId())) {
                superEvent(snapshot, chavePreviaAdicionada);
                chavePreviaAdicionada = snapshot.getKey();
            }
        }

        public abstract void superEvent(DataSnapshot snapshot, String previousChildKey);
    }

 }



