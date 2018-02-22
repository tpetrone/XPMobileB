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

public class MensagemDao {
    private DatabaseReference base;
    private DatabaseReference reference;
    private FirebaseUser user;

    private String destinatarioId;

    public MensagemDao(String destinatarioId) {
        this.destinatarioId = destinatarioId;
        user = FirebaseAuth.getInstance().getCurrentUser();
        base = FirebaseDatabase.getInstance().getReference();
        reference = base.child("mensagens").child(user.getUid()).child(destinatarioId);
    }

    public DatabaseReference getReference() {
        return reference;
    }

    private String makeReference1(String id) {
        return "/mensagens/" + user.getUid() + "/" + destinatarioId + "/" + id;
    }

    private String makeReference2(String id) {
        return "/mensagens/" + destinatarioId + "/" + user.getUid() + "/" + id;
    }

    public void salvar(Mensagem obj, DatabaseReference.CompletionListener callback) {
        if(obj.getId() == null) {
            obj.setId(reference.push().getKey());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("id", obj.getId());
        map.put("mensagem", obj.getMensagem());
        map.put("data", obj.getData());
        map.put("origem", user.getUid());

        Map<String, Object> updates = new HashMap<>();
        updates.put(makeReference1(obj.getId()), map);
        updates.put(makeReference2(obj.getId()), map);

        base.updateChildren(updates, callback);
    }

    public void remover(String id, DatabaseReference.CompletionListener callback) {
        Map<String, Object> updates = new HashMap<>();
        updates.put(makeReference1(id), null);
        updates.put(makeReference2(id), null);

        base.updateChildren(updates, callback);
    }

    public void verificaMensagens() {
        getReference().addListenerForSingleValueEvent(new DataCalback());
    }
}
