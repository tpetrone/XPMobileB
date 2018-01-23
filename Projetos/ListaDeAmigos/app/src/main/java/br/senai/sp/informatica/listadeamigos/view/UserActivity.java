package br.senai.sp.informatica.listadeamigos.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import br.senai.sp.informatica.listadeamigos.R;
import br.senai.sp.informatica.listadeamigos.lib.PhotoActions;
import br.senai.sp.informatica.listadeamigos.lib.Utilitarios;
import br.senai.sp.informatica.listadeamigos.model.Amigo;
import br.senai.sp.informatica.listadeamigos.model.AmigoDao;

public class UserActivity extends AppCompatActivity
        implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    public static final String NOME_USUARIO = "nome";
    public static final String EMAIL_USUARIO = "email";
    public static final String FOTO_USUARIO = "foto";

    private EditText edNome;
    private EditText edEmail;
    private ImageButton btSair;
    private Toolbar toolbar;

    private ImageButton btFoto;
    private ImageView foto;
    private File fotoUrl;
    private boolean novaFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);

        edNome = (EditText) findViewById(R.id.edNome);
        edEmail = (EditText) findViewById(R.id.edEmail);
        foto = (ImageView) findViewById(R.id.ivFoto);

        btSair = (ImageButton) findViewById(R.id.btSair);
        btSair.setOnClickListener(this);

        btFoto = (ImageButton) findViewById(R.id.btFoto);
        btFoto.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        edNome.setText(preferences.getString(NOME_USUARIO, ""));
        edEmail.setText(preferences.getString(EMAIL_USUARIO, ""));

        String fotoString = preferences.getString(FOTO_USUARIO, null);
        if (fotoString != null) {
            Bitmap bitmap = Utilitarios.bitmapFromBase64(fotoString.getBytes());
            foto.setImageBitmap(bitmap);
            novaFoto = false;
        }

        Utilitarios.hideKeyboard(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salvar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.acao_salvar:

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putString(NOME_USUARIO, edNome.getText().toString());
                editor.putString(EMAIL_USUARIO, edEmail.getText().toString());

                Bitmap bitmap = Utilitarios.bitmapFromImageView(foto);
                if (bitmap != null) {
                    editor.putString(FOTO_USUARIO, new String(Utilitarios.bitmapToBase64(bitmap)));
                } else {
                    editor.putString(FOTO_USUARIO, null);
                }

                editor.apply();

                setResult(RESULT_OK);
                break;
        }

        finish();

        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.equals(btSair)) {
            finish();
        } else {
            // criar um popup menu para foto
            PopupMenu popupMenu = new PopupMenu(this, btFoto);
            popupMenu.getMenuInflater().inflate(R.menu.menu_foto, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        fotoUrl = new PhotoActions(this).fotoMenu(menuItem, foto,
                R.id.ac_tirar_foto, R.id.ac_galeria_foto, R.id.ac_excluir_foto);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean autorizado = true;

        for (int resultato : grantResults) {
            if (resultato == PackageManager.PERMISSION_DENIED) {
                autorizado = false;
                break;
            }
        }

        switch (requestCode) {
            case PhotoActions.REQUEST_CAMERA_PERMISSION:
                try {
                    if (autorizado)
                        fotoUrl = new PhotoActions(this).startCamera();
                    else
                        Toast.makeText(this, "O Acesso à Câmera foi negado!", Toast.LENGTH_LONG).show();
                } catch (IOException ex) {
                    Toast.makeText(this, "Houve problemas em Salvar a Foto", Toast.LENGTH_LONG).show();
                }
                break;
            case PhotoActions.REQUEST_GALERY_PERMISSION:
                if (autorizado)
                    new PhotoActions(this).openGalery();
                else
                    Toast.makeText(this, "O Acesso à Galeria de Fotos foi negado!", Toast.LENGTH_LONG).show();
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        novaFoto = new PhotoActions(this).fotoResult(requestCode, resultCode, data, foto, fotoUrl);
    }
}
