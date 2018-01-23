package br.senai.sp.informatica.listadeamigos.view;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

import br.senai.sp.informatica.listadeamigos.R;
import br.senai.sp.informatica.listadeamigos.lib.PhotoActions;
import br.senai.sp.informatica.listadeamigos.lib.Utilitarios;
import br.senai.sp.informatica.listadeamigos.model.Amigo;
import br.senai.sp.informatica.listadeamigos.model.AmigoDao;

public class AmigoActivity extends AppCompatActivity
        implements View.OnClickListener, DatePicker.OnDateChangedListener, PopupMenu.OnMenuItemClickListener {
    private EditText edNome;
    private EditText edEmail;
    private Amigo amigoEditado;
    private ImageButton btSair;
    private Toolbar toolbar;

    private ImageButton btFoto;
    private ImageView foto;
    private File fotoUrl;
    private boolean novaFoto;

    private TextView tvDate;
    private DatePicker datePicker;
    private DateFormat fmt;
    private Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amigo_activity);

        edNome = (EditText) findViewById(R.id.edNome);
        edEmail = (EditText) findViewById(R.id.edEmail);
        tvDate = (TextView) findViewById(R.id.tvData);
        foto = (ImageView) findViewById(R.id.ivFoto);
        datePicker = (DatePicker) findViewById(R.id.datePicker);

        btSair = (ImageButton) findViewById(R.id.btSair);
        btSair.setOnClickListener(this);

        btFoto = (ImageButton) findViewById(R.id.btFoto);
        btFoto.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        fmt = DateFormat.getDateInstance(DateFormat.LONG);
        cal = Calendar.getInstance();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            long id = extras.getLong("id");
            amigoEditado = AmigoDao.dao.localizar(id);
            edNome.setText(amigoEditado.getNome());
            edEmail.setText(amigoEditado.getEmail());

            if (amigoEditado.getNascimento() == null) {
                tvDate.setText(fmt.format(cal.getTime()));
            } else {
                tvDate.setText(fmt.format(amigoEditado.getNascimento()));
                cal.setTime(amigoEditado.getNascimento());
            }

            byte[] fotobase64 = amigoEditado.getFoto();
            if (fotobase64 != null) {
                Bitmap bitmap = Utilitarios.bitmapFromBase64(fotobase64);
                foto.setImageBitmap(bitmap);
                novaFoto = false;
            }
        } else {
            tvDate.setText(fmt.format(cal.getTime()));
        }

        datePicker.init(
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH),
                this);

        ActionBar bar = getActionBar();
        if (bar != null) {
            bar.setHomeButtonEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
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
                if (amigoEditado == null) {
                    amigoEditado = new Amigo();
                }

                amigoEditado.setNome(edNome.getText().toString());
                amigoEditado.setEmail(edEmail.getText().toString());
                amigoEditado.setNascimento(cal.getTime());

                Bitmap bitmap = Utilitarios.bitmapFromImageView(foto);
                if(bitmap != null) {
                    amigoEditado.setFoto(Utilitarios.bitmapToBase64(bitmap));
                } else {
                    amigoEditado.setFoto(null);
                }

                AmigoDao.dao.salvar(amigoEditado);

                sendBroadcast(new Intent("br.senai.sp.informatica.listadeamigos.ATUALIZA_AMIGOS"));

                setResult(RESULT_OK);
                break;
        }

        finish();

        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.equals(btSair)) {
            setResult(RESULT_CANCELED);
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

        for(int resultato : grantResults) {
            if(resultato == PackageManager.PERMISSION_DENIED) {
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
                if(autorizado)
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

    @Override
    public void onDateChanged(DatePicker datePicker, int ano, int mes, int dia) {
        cal.set(ano, mes, dia);
        tvDate.setText(fmt.format(cal.getTime()));
    }
}
