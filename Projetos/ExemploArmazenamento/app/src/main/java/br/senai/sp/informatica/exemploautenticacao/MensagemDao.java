package br.senai.sp.informatica.exemploautenticacao;

import android.provider.ContactsContract;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MensagemDao {
    public static MensagemDao dao = new MensagemDao();

    private DatabaseReference base;
    private DatabaseReference reference;
    private FirebaseUser user;

    private MensagemDao() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        base = FirebaseDatabase.getInstance().getReference();
        reference = base.child("mensagens").child(user.getUid());
    }

    public DatabaseReference getReference() {
        return reference;
    }

    public void salvar(Mensagem obj, DatabaseReference.CompletionListener callback) {
        if(obj.getId() == null) {
            obj.setId(reference.push().getKey());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("id", obj.getId());
        map.put("mensagem", obj.getMensagem());

        Map<String, Object> updates = new HashMap<>();
        updates.put("/mensagens/" + user.getUid() + "/" + obj.getId(), map);

        base.updateChildren(updates, callback);
    }

    public void remover(String id, DatabaseReference.CompletionListener callback) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("/mensagens/" + user.getUid() + "/" + id, null);

        base.updateChildren(updates, callback);
    }

    public void verificaMensagens() {
        getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
