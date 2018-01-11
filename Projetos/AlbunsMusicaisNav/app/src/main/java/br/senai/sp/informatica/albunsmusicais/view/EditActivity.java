package br.senai.sp.informatica.albunsmusicais.view;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.util.Calendar;

import br.senai.sp.informatica.albunsmusicais.R;
import br.senai.sp.informatica.albunsmusicais.lib.Utilitarios;
import br.senai.sp.informatica.albunsmusicais.model.Album;
import br.senai.sp.informatica.albunsmusicais.model.AlbumDao;
import br.senai.sp.informatica.albunsmusicais.view.fragment.DateDialog;


/**
 * Created by pena on 21/11/2017.
 */

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_IMAGE_GALERY = 0;
    private static final int REQUEST_GALERY_PERMISSION = 1;

    private AlbumDao dao = AlbumDao.instance;
    private EditText edBanda;
    private EditText edAlbum;
    private EditText edGenero;
    private EditText edLancamento;
    private Button btLancamento;
    private ImageView ivCapa;
    private Album album;
    private DateFormat fmt;
    private Calendar calendar;
    private Uri imageURI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edBanda = findViewById(R.id.edBanda);
        edAlbum = findViewById(R.id.edAlbum);
        edGenero = findViewById(R.id.edGenero);
        edLancamento = findViewById(R.id.edLancamento);
        ivCapa = findViewById(R.id.ivFoto);
        ivCapa.setOnClickListener(this);
        btLancamento = findViewById(R.id.btLancamento);
        btLancamento.setOnClickListener(this);

        calendar = Calendar.getInstance();
        fmt = DateFormat.getDateInstance(DateFormat.LONG);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            long id = extras.getLong("id");
            album = dao.localizar(id);
            edBanda.setText(album.getBanda());
            edAlbum.setText(album.getAlbum());
            edGenero.setText(album.getGenero());

            if (album.getLancamento() == null) {
                edLancamento.setText(fmt.format(calendar.getTime()));
            } else {
                edLancamento.setText(album.getDataDeLancamento());
                calendar.setTime(album.getLancamento());
            }

            byte[] capabase64 = album.getCapa();
            if (capabase64 != null) {
                Bitmap bitmap = Utilitarios.bitmapFromBase64(capabase64);
                ivCapa.setImageBitmap(bitmap);
            }
        } else {
            edLancamento.setText(fmt.format(calendar.getTime()));
        }

        ActionBar bar = getActionBar();
        if (bar != null) {
            bar.setHomeButtonEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
        }

        Utilitarios.hideKeyboard(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                setResult(Activity.RESULT_CANCELED);
                break;
            case R.id.action_save:
                if(album == null) {
                    album = new Album();
                }

                album.setBanda(edBanda.getText().toString());
                album.setAlbum(edAlbum.getText().toString());
                album.setGenero(edGenero.getText().toString());
                album.setLancamento(calendar.getTime());

                Bitmap bitmap = Utilitarios.bitmapFromImageView(ivCapa);
                if(bitmap != null) {
                    album.setCapa(Utilitarios.bitmapToBase64(bitmap));
                } else {
                    album.setCapa(null);
                }

                dao.salvar(album);

                setResult(Activity.RESULT_OK);
                break;
        }
        finish();

        return true;
    }

    @Override
    public void onClick(View view) {
        if(view.equals(btLancamento)) {
            // Abrir o Date PickerDialog
            selecionaData(view);
        } else {
            // Abrir a Galeria de Fotos
            abrirGalery();
        }
    }

    public void selecionaData(View view) {
        DateDialog.makeDialog(calendar,R.id.edLancamento)
                .show(getFragmentManager(), "Data de Lançamento");
    }

    private void abrirGalery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        if (intent.resolveActivity(getPackageManager()) != null) {
            if ((ContextCompat.checkSelfPermission(getBaseContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_GALERY_PERMISSION);
            } else {
                startActivityForResult(Intent.createChooser(intent, "Selecione a Foto"),
                        REQUEST_IMAGE_GALERY);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean autorizado = true;

        for(int resultato : grantResults) {
            if(resultato == PackageManager.PERMISSION_DENIED) {
                autorizado = false;
                break;
            }
        }

        switch (requestCode) {
            case REQUEST_GALERY_PERMISSION:
                if(autorizado)
                    abrirGalery();
                else
                    Toast.makeText(this, "O Acesso à Galeria de Fotos foi negado!", Toast.LENGTH_LONG).show();
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_GALERY) { // Recebe a Foto da Galeria de Fotos
            if(data != null) {
                try {
                    imageURI = data.getData();

                    Bitmap bitmap = Utilitarios.setPic(ivCapa.getWidth(), ivCapa.getHeight(), imageURI, this);
                    ivCapa.setImageBitmap(bitmap);
                    ivCapa.invalidate();
                } catch (IOException ex) {
                    Toast.makeText(this, "Falha ao abrir a Foto", Toast.LENGTH_LONG).show();
                }
            }
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

    private void carregaCapa(Uri imageURI) {
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
