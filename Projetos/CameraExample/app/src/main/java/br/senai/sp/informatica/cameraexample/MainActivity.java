package br.senai.sp.informatica.cameraexample;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import br.senai.sp.informatica.cameraexample.lib.PhotoEditorActivity;
import br.senai.sp.informatica.cameraexample.lib.Utilitarios;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_EDIT = 2;

    private static final String FOTO_USUARIO = "ivFoto";
    private static final String FOTO_URL = "fotourl";

    private ImageView ivFoto;
    private File fotoUrl;
    private boolean novaFoto = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivFoto = (ImageView)findViewById(R.id.imageView);
    }

    // TODO: Aborta quando a Imagem selecionada tem tamanho acima de 100Kb por limitação do Android (Parcelable)
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(FOTO_URL, fotoUrl.getAbsolutePath());
        Bitmap bitmap = Utilitarios.bitmapFromImageView(ivFoto);
        if(bitmap != null && novaFoto)
            outState.putByteArray(FOTO_USUARIO, Utilitarios.bitmapToBase64(bitmap));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        fotoUrl = new File(savedInstanceState.getString(FOTO_URL));
        byte[] bytes = savedInstanceState.getByteArray(FOTO_USUARIO);
        if(bytes != null && novaFoto) {
            Bitmap bitmap = Utilitarios.bitmapFromBase64(bytes);
            ivFoto.setImageBitmap(bitmap);
        }
    }

    public void abreCamera(View view)  {
        // Chama a câmera para obter uma ivFoto
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            if ((ContextCompat.checkSelfPermission(getBaseContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(getBaseContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(getBaseContext(),
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                }, REQUEST_CAMERA_PERMISSION);
            } else {
                try {
                    fotoUrl = Utilitarios.createImageFile();
                    int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                    if (currentapiVersion > Build.VERSION_CODES.KITKAT) {
                        String pkgName = getApplicationContext().getPackageName();
                        Uri local = FileProvider.getUriForFile(this, pkgName + ".provider", fotoUrl);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, local);
                    } else {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fotoUrl));
                    }

                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                } catch (IOException ex) {
                    Toast.makeText(this, "Falha ao criar arquivo temporário", Toast.LENGTH_LONG).show();
                }
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
            case REQUEST_CAMERA_PERMISSION:
                if(autorizado)
                    abreCamera(null);
                else
                    Toast.makeText(this, "O Acesso à Câmera foi negado!", Toast.LENGTH_LONG).show();
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Carrega a foto após a captura da imagem pela câmera
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) { // Recebe a foto da Camera
                // Chama o Editor de Fotos
                Intent edFoto = new Intent(this, PhotoEditorActivity.class);
                edFoto.putExtra("PHOTO_URL", fotoUrl.toString());
                startActivityForResult(edFoto, REQUEST_IMAGE_EDIT);
            } else if(requestCode == REQUEST_IMAGE_EDIT) {
                Bundle dados = data.getExtras();
                if (dados != null) {
                    try {
                        File bitmapUrl = new File(dados.getString("PHOTO_URL"));
                        Bitmap bitmap = Utilitarios.setPic(ivFoto.getWidth(), ivFoto.getHeight(), bitmapUrl, this);
                        ivFoto.setImageBitmap(bitmap);
                        ivFoto.invalidate();
                        novaFoto = true;
                    } catch (IOException ex) {
                        Toast.makeText(this, "Falha ao capturar a Foto", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}
