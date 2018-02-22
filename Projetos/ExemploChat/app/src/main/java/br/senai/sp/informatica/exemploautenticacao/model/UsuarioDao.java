package br.senai.sp.informatica.exemploautenticacao.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import br.senai.sp.informatica.exemploautenticacao.lib.DataCalback;

public class UsuarioDao {
    public static UsuarioDao dao = new UsuarioDao();

    private DatabaseReference base;
    private DatabaseReference reference;

    private UsuarioDao() {
        base = FirebaseDatabase.getInstance().getReference();
        reference = base.child("usuarios");
    }

    public DatabaseReference getReference() {
        return reference;
    }

    public String getUserId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public void salvar(Usuario obj, DatabaseReference.CompletionListener callback) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", obj.getId());
        map.put("email", obj.getEmail());
        map.put("logado", obj.isLogado());

        Map<String, Object> updates = new HashMap<>();
        updates.put("/usuarios/" + obj.getId(), map);

        base.updateChildren(updates, callback);
    }

    public void atualizaLogon(boolean logado) {
        getReference().child(getUserId()).child("logado").setValue(logado);
    }

    public void jaEstaLogado(String id, DataCalback callback) {
        getReference().child(id).child("logado").addListenerForSingleValueEvent(callback);
    }

    public void localizaEmail(String id, DataCalback callback) {
        getReference().child(id).child("email").addListenerForSingleValueEvent(callback);
    }

    public void verificaMensagens() {
        getReference().addListenerForSingleValueEvent(new DataCalback());
    }
}
