package br.senai.sp.informatica.jurossimples2;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.ParseException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // Declaração dos componentes de tela
    private EditText edCapital;
    private EditText edMeses;
    private EditText edTaxa;
    private EditText edResultado;
    private Button btCalcular;
    private Button btLimpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Registro do Layout
        setContentView(R.layout.activity_main);

        // Registro dos componentes
        edCapital = (EditText)findViewById(R.id.edCapital);
        edMeses = (EditText)findViewById(R.id.edMeses);
        edTaxa = (EditText)findViewById(R.id.edTaxa);
        edResultado = (EditText)findViewById(R.id.edResultado);

        btCalcular = (Button)findViewById(R.id.btCalcular);
        btLimpar = (Button)findViewById(R.id.btLimpar);

        // Registra esta classe como a resonsável por tratar
        // os eventos dos botões
        btCalcular.setOnClickListener(this);
        btLimpar.setOnClickListener(this);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onClick(View view) {
        // Identifica o botão que foi selecionado
        if(view.getId() == R.id.btCalcular) {
            try {
                // Configurar o formatador de Números
                NumberFormat fmt = NumberFormat.getNumberInstance();
                fmt.setMaximumFractionDigits(2);
                fmt.setMinimumFractionDigits(2);

                // lê e converte os valores informados na tela
                double capital = fmt.parse(edCapital.getText().toString()).doubleValue();
                int meses = Integer.parseInt(edMeses.getText().toString());
                double taxa = fmt.parse(edTaxa.getText().toString()).doubleValue();
                // efetua o calculo
                double resultato = capital * taxa / 100 * meses + capital;
                // apresenta o resultado
                edResultado.setText(String.format("%,.2f", resultato));
            } catch (Exception ex) {
                Toast.makeText(this, "Número(s) inválido(s)!", Toast.LENGTH_LONG).show();
            }
        } else {
            edCapital.setText(getResources().getText(R.string.num_decimal));
            edMeses.setText(getResources().getText(R.string.num_inteiro));
            edTaxa.setText(getResources().getText(R.string.num_decimal));
            edResultado.setText(getResources().getText(R.string.num_decimal));
        }
        edCapital.requestFocus();
    }
}
