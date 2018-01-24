package br.senai.sp.informatica.cliente;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {
    private TextView tvNome;
    private TextView tvRazao;
    private TextView tvCnpj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        tvNome = findViewById(R.id.TvNome);
        tvRazao = findViewById(R.id.TvRazao);
        tvCnpj = findViewById(R.id.TvCnpj);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Cliente cliente = (Cliente)extras.getParcelable("cliente");
            tvNome.setText(cliente.getNomeFantasia());
            tvRazao.setText(cliente.getRazaoSocial());
            tvCnpj.setText(cliente.getCnpj());
        }
    }
}
