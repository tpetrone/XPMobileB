package br.senai.sp.informatica.exemploautenticacao.lib;

import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.ui.database.FirebaseArray;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;

import br.senai.sp.informatica.exemploautenticacao.model.Usuario;
import br.senai.sp.informatica.exemploautenticacao.model.UsuarioDao;


 public class UsuarioChatArray extends FirebaseArray<Usuario> {
    private UsuarioDao dao = UsuarioDao.dao;
    private String chavePreviaAdicionada;

    public UsuarioChatArray() {
        super(UsuarioDao.dao.getReference().orderByChild("email"),
                new SnapshotParser<Usuario>() {
                    public Usuario parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return snapshot.getValue(Usuario.class);
                    }
                }
            );
    }

    public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
        if(previousChildKey == null) chavePreviaAdicionada = null;

        if(!snapshot.getKey().equals(dao.getUserId())) {
            super.onChildAdded(snapshot, chavePreviaAdicionada);
            chavePreviaAdicionada = snapshot.getKey();
        }
    }
 }


