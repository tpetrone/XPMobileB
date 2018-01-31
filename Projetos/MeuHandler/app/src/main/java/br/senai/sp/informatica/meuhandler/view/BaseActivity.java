package br.senai.sp.informatica.meuhandler.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

import br.senai.sp.informatica.meuhandler.R;
import br.senai.sp.informatica.meuhandler.lib.Utilitarios;
import br.senai.sp.informatica.meuhandler.model.Album;


public abstract class BaseActivity extends AppCompatActivity {
    protected EditText edBanda;
    protected EditText edAlbum;
    protected EditText edGenero;
    protected EditText edLancamento;
    protected Button btLancamento;
    protected ImageView ivCapa;
    protected Album album;
    protected DateFormat fmt;
    protected Calendar calendar;
    protected Uri imageURI;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edBanda = findViewById(R.id.edBanda);
        edAlbum = findViewById(R.id.edAlbum);
        edGenero = findViewById(R.id.edGenero);
        edLancamento = findViewById(R.id.edLancamento);
        ivCapa = findViewById(R.id.ivFoto);
        btLancamento = findViewById(R.id.btLancamento);

        calendar = Calendar.getInstance();
        fmt = DateFormat.getDateInstance(DateFormat.LONG);
    }

    protected void carregaAlbumNaTela(Album album) {
        if(album != null) {
            edBanda.setText(album.getBanda());
            edAlbum.setText(album.getAlbum());
            edGenero.setText(album.getGenero());

            if (album.getLancamento() == null) {
                edLancamento.setText(fmt.format(calendar.getTime()));
            } else {
                edLancamento.setText(album.getDataDeLancamento());
                calendar.setTime(album.getLancamento());
                Log.d("EditActivity", "Cal: " + calendar);
            }

            byte[] capabase64 = album.getCapa();
            if (capabase64 != null) {
                Bitmap bitmap = Utilitarios.bitmapFromBase64(capabase64);
                ivCapa.setImageBitmap(bitmap);
            }
        } else {
            Toast.makeText(this, "Este album foi apagado por outro usuário!",
                    Toast.LENGTH_LONG).show();
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(imageURI != null) {
            outState.putParcelable("uri", imageURI);
        }

        outState.putString("edBanda", edBanda.getText().toString());
        outState.putString("edAlbum", edAlbum.getText().toString());
        outState.putString("edGenero", edGenero.getText().toString());
        outState.putString("edLancamento", edLancamento.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        imageURI = savedInstanceState.getParcelable("uri");
        carregaCapa(imageURI);

        byte[] bytes = savedInstanceState.getByteArray("ivCapa");
        if(bytes != null)
            ivCapa.setImageBitmap(Utilitarios.bitmapFromBase64(bytes));

        edBanda.setText(savedInstanceState.getString("edBanda"));
        edAlbum.setText(savedInstanceState.getString("edAlbum"));
        edGenero.setText(savedInstanceState.getString("edGenero"));
        edLancamento.setText(savedInstanceState.getString("edLancamento"));
    }

    protected void carregaCapa(Uri imageURI) {
        try {
            if (imageURI != null) {
                Bitmap bitmap = Utilitarios.setPic(ivCapa.getWidth(), ivCapa.getHeight(), imageURI, this);
                ivCapa.setImageBitmap(bitmap);
                ivCapa.invalidate();
            } else {
                if(album != null) {
                    byte[] capabase64 = album.getCapa();
                    if (capabase64 != null) {
                        Bitmap bitmap = Utilitarios.bitmapFromBase64(capabase64);
                        ivCapa.setImageBitmap(bitmap);
                        ivCapa.invalidate();
                    }
                }
            }
        } catch (IOException ex) {
            Toast.makeText(this, "Falha ao abrir a Foto", Toast.LENGTH_LONG).show();
        }
    }

}
