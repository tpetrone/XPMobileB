package br.senai.sp.informatica.exemploautenticacao.lib;

import android.provider.ContactsContract;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


public class DataCalback implements ValueEventListener {

    private OnDataChange onDataChange;

    public interface OnDataChange {
        public void dataChange(DataSnapshot dataSnapshot);
    }

    public DataCalback() {
    }

    public DataCalback(OnDataChange onDataChange) {
        this.onDataChange = onDataChange;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if(this.onDataChange != null)
            this.onDataChange.dataChange(dataSnapshot);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
