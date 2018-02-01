package br.senai.sp.informatica.cliente;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText edNome;
    private EditText edRazao;
    private EditText edCnpj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edNome = findViewById(R.id.EdNome);
        edRazao = findViewById(R.id.EdRazao);
        edCnpj = findViewById(R.id.EdCnpj);
    }

    public void okClicked(View view) {
        Cliente cliente = new Cliente();

        cliente.setNomeFantasia(edNome.getText().toString());
        cliente.setRazaoSocial(edRazao.getText().toString());
        cliente.setCnpj(edCnpj.getText().toString());

        edNome.setText("");
        edRazao.setText("");
        edCnpj.setText("");

        Intent intent = new Intent(this, ShowActivity.class);
        intent.putExtra("cliente", cliente);
        startActivity(intent);
    }

}
