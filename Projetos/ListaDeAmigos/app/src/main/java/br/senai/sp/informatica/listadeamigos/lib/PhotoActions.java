package br.senai.sp.informatica.listadeamigos.lib;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.text.LocaleDisplayNames;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by pena on 05/11/16.
 */

public class PhotoActions {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_EDIT = 2;
    private static final int REQUEST_IMAGE_GALERY = 3;
    public static final int REQUEST_CAMERA_PERMISSION = 4;
    public static final int REQUEST_GALERY_PERMISSION = 5;

    private Activity activity;
    private Fragment fragment;
    private boolean isActivity;

    public PhotoActions(Fragment fragment) {
        activity = fragment.getActivity();
        this.fragment = fragment;
        isActivity = false;
    }

    public PhotoActions(Activity activity) {
        this.activity = activity;
        isActivity = true;
    }

    private Context getContext() {
        if(isActivity) {
            return activity.getBaseContext();
        } else {
            return fragment.getActivity().getBaseContext();
        }
    }

    private  void startActivityForResult(Intent intent, int requestCode) {
        if(isActivity) {
            activity.startActivityForResult(intent, requestCode);
        } else {
            fragment.startActivityForResult(intent, requestCode);
        }
    }

    public File fotoMenu(MenuItem menuItem, ImageView foto, int takePhoto, int galery, int exclude) {
        File fotoUrl = null;

        if (menuItem.getItemId() == takePhoto) {
            try {
                // Chama a câmera para obter uma foto
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (intent.resolveActivity(activity.getPackageManager()) != null) {
                    int MY_PERMISSIONS_REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE;

                    if ((ContextCompat.checkSelfPermission(getContext(), 
							Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                        (ContextCompat.checkSelfPermission(getContext(), 
							Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                        (ContextCompat.checkSelfPermission(getContext(), 
							Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                        ActivityCompat.requestPermissions(activity, new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA
                        }, REQUEST_CAMERA_PERMISSION);
                    } else {
                        fotoUrl = startCamera();
                    }
                }
            } catch (IOException ex) {
                Toast.makeText(activity, "Houve problemas em Salvar a Foto", Toast.LENGTH_LONG).show();
            }
        } else if (menuItem.getItemId() == galery) {
            openGalery();
        } else if (menuItem.getItemId() == exclude) {
            // Exclui a foto do Contato
            foto.invalidate();
            foto.setImageBitmap(null);
        }

        return fotoUrl;
    }

    private Intent getGaleryIntent() {
        // Chama a Galeria de Fotos
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        return intent;
    }

    public void openGalery() {
        if (getGaleryIntent().resolveActivity(activity.getPackageManager()) != null) {
            int MY_PERMISSIONS_REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE;

            if ((ContextCompat.checkSelfPermission(getContext(), 
					Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(getContext(), 
					Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(activity, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, REQUEST_GALERY_PERMISSION);
            } else {
                startActivityForResult(Intent.createChooser(getGaleryIntent(), "Selecione a Foto"), 
					REQUEST_IMAGE_GALERY);
            }
        }
    }

    public File startCamera() throws IOException {
        File fotoUrl = null;
        // Chama a câmera para obter uma foto
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fotoUrl = Utilitarios.createImageFile();
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion > Build.VERSION_CODES.KITKAT) {
            String pkgName = activity.getApplicationContext().getPackageName();
            Uri local = FileProvider.getUriForFile(activity, pkgName + ".provider", fotoUrl);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, local);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fotoUrl));
        }

        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

        return fotoUrl;
    }

    public  boolean fotoResult(int requestCode, int resultCode, Intent data, ImageView foto, File fotoUrl) {
        boolean newPhoto = false;

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) { // Recebe a foto da Camera
                // Salva a foto na Galeria de Fotos
                Utilitarios.galleryAddPic(activity, fotoUrl);
                Toast.makeText(activity, "Foto Salva na Galeria", Toast.LENGTH_LONG).show();

                // Chama o Editor de Fotos
                Intent edFoto = new Intent(getContext(), PhotoEditorActivity.class);
                edFoto.putExtra("PHOTO_URL", fotoUrl.toString());
                startActivityForResult(edFoto, REQUEST_IMAGE_EDIT);
            } else if (requestCode == REQUEST_IMAGE_EDIT) { // Recebe a Foto editada
                Bundle dados = data.getExtras();
                if (dados != null) {
                    try {
                        File bitmapUrl = new File(dados.getString("PHOTO_URL"));
                        // Carrega a foto num ImageView
                        Bitmap bitmap = Utilitarios.setPic(foto.getWidth(), foto.getHeight(), bitmapUrl, activity);
                        bitmapUrl.delete();
                        foto.setImageBitmap(Utilitarios.toCircularBitmap(bitmap));
                        foto.invalidate();
                        newPhoto = true;
                    } catch (IOException e) {
                        Toast.makeText(activity, "Falha ao abrir a Foto", Toast.LENGTH_LONG).show();
                    }
                }
            } else if (requestCode == REQUEST_IMAGE_GALERY) { // Recebe a Foto da Galeria de Fotos
                Uri currImageURI = data.getData();
                // Chama o Editor de Fotos
                Intent edFoto = new Intent(getContext(), PhotoEditorActivity.class);
                edFoto.putExtra("PHOTO_URL", currImageURI.toString());
                startActivityForResult(edFoto, REQUEST_IMAGE_EDIT);
            }
        } else {
            if (fotoUrl != null)
                fotoUrl.delete();
            fotoUrl = null;
        }

        return newPhoto;
    }

}
