package br.senai.sp.informatica.exemploautenticacao.lib;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class MessageCallBack implements DatabaseReference.CompletionListener {
    private String msg;
    private Context ctx;

    public MessageCallBack(Context ctx, String msg) {
        this.msg = msg;
        this.ctx = ctx;
    }

    @Override
    public void onComplete(DatabaseError error, DatabaseReference reference) {
        if (error != null) {
            Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
        }
    }
}
