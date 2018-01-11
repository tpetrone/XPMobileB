package br.senai.sp.informatica.permissoesegaleriadefotos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;

import br.senai.sp.informatica.permissoesegaleriadefotos.lib.Utilitarios;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {
    private static final int REQUEST_IMAGE_GALERY = 0;
    private static final int REQUEST_GALERY_PERMISSION = 1;
    private ImageView ivFoto;
    private ImageView ivFotoPequena;
    private TextView tvNome;
    private EditText edNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNome = (TextView)findViewById(R.id.tvNome);
        edNome = (EditText)findViewById(R.id.edNome);
        ivFotoPequena = (ImageView)findViewById(R.id.ivFotoPequena);
        ivFoto = (ImageView)findViewById(R.id.ivFoto);
        ivFoto.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Bitmap bitmap = Utilitarios.bitmapFromImageView(ivFotoPequena);
        if(bitmap != null) {
            outState.putByteArray("fotoPequena", Utilitarios.bitmapToBase64(bitmap));
        } else {
            outState.putByteArray("fotoPequena", null);
        }

        bitmap = Utilitarios.bitmapFromImageView(ivFoto);
        if(bitmap != null) {
            outState.putByteArray("foto", Utilitarios.bitmapToBase64(bitmap));
        } else {
            outState.putByteArray("foto",null);
        }

        outState.putString("tvNome", tvNome.getText().toString());
        outState.putString("edNome", edNome.getText().toString());


        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        byte[] bytes = savedInstanceState.getByteArray("fotoPequena");
        if(bytes != null)
            ivFotoPequena.setImageBitmap(Utilitarios.bitmapFromBase64(bytes));

        bytes = savedInstanceState.getByteArray("foto");
        if(bytes != null)
            ivFoto.setImageBitmap(Utilitarios.bitmapFromBase64(bytes));

        tvNome.setText(savedInstanceState.getString("tvNome"));
        edNome.setText(savedInstanceState.getString("edNome"));

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        abrirGalery();
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
            case REQUEST_GALERY_PERMISSION: // Solicitou permissão para acesso à Galeria de Fotos
                if(autorizado)              // Foi autorizado
                    abrirGalery();
                else                        // Não foi autorizado
                    Toast.makeText(this, "O Acesso à Galeria de Fotos foi negado!", Toast.LENGTH_LONG).show();
                break;
            default:                        // Solicitou a abertura da Galeria de Fotos
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_GALERY) { // Recebe a Foto da Galeria de Fotos
            if(data != null) {
                try {
                    Uri imageURI = data.getData();
                    Bitmap bitmap = Utilitarios.setPic(ivFoto.getWidth(), ivFoto.getHeight(), imageURI, this);
                    ivFoto.setImageBitmap(bitmap);
                    ivFoto.invalidate();
                } catch (IOException ex) {
                    Toast.makeText(this, "Falha ao abrir a Foto", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void salvar(View view) {
        String nome = edNome.getText().toString();
        tvNome.setText(nome);

        // Demostra como obter um Bitmap de um ImageView
        Bitmap bitmap = Utilitarios.bitmapFromImageView(ivFoto);
        if(bitmap != null) {
            // Demonstra como converter um Bitmap para Base64
            byte[] bytes = Utilitarios.bitmapToBase64(bitmap);
            // Demonstra como converter um Base64 em Bitmap
            ivFotoPequena.setImageBitmap(Utilitarios.bitmapFromBase64(bytes));
        } else {
            // Demostra como obter uma cor padrão do Android
            int corDaLetra = ContextCompat
                    .getColor(this, android.R.color.holo_blue_dark);
            int corDoFundo = ContextCompat
                    .getColor(this, android.R.color.transparent);
            // Demonstra como criar um Bitmap contendo um Texto dentro de um
            // Circulo
            bitmap = Utilitarios.circularBitmapAndText(corDaLetra,
                    ivFotoPequena.getWidth(), ivFotoPequena.getHeight(),
                            nome.substring(0,1).toUpperCase());
            ivFotoPequena.setBackgroundColor(corDoFundo);
            ivFotoPequena.setImageBitmap(bitmap);
        }

    }
}
