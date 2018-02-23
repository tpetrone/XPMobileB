package br.senai.sp.informatica.exemploautenticacao.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        DatabaseReference ref = getReference().child(getUserId());
        ref.child("id").setValue(obj.getId());
        ref.child("email").setValue(obj.getEmail());
        ref.child("token").setValue(obj.getToken());
        ref.child("logado").setValue(obj.isLogado());
    }

    public void atualizaLogon(boolean logado) {
        DatabaseReference ref = getReference().child(getUserId());
        ref.child("logado").setValue(logado);

        if (!logado) {
            ref.child("token").setValue(null);
        }
    }

    public void incrementaMensagens(final String destinatarioId) {
        final DatabaseReference ref = getReference()
                .child(destinatarioId)
                .child("mensagens")
                .child(getUserId())
                .child("chat");
        ref.addListenerForSingleValueEvent(
                new DataCalback(new DataCalback.OnDataChange() {
                    @Override
                    public void dataChange(DataSnapshot dataSnapshot) {
                        int total = 1;
                        if(dataSnapshot.exists()) {
                            total += dataSnapshot.getValue(Integer.class);
                        }
                        ref.setValue(total);
                    }
                }));
    }

    public void zeraMensagens(String destinatarioId) {
        getReference()
                .child(destinatarioId)
                .child("mensagens")
                .child(getUserId())
                .child("chat").setValue(0);
    }

    public void getChatCount(String id, DataCalback calback) {
        final DatabaseReference ref = getReference()
                .child(id)
                .child("mensagens")
                .child(getUserId())
                .child("chat");
        ref.addListenerForSingleValueEvent(calback);
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
