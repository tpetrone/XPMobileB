package br.senai.sp.informatica.meucalculo;

import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import br.senai.sp.informatica.meucalculo.calculadora.Calculadora;
import br.senai.sp.informatica.meucalculo.calculadora.CalculadoraException;

public class MainActivity extends AppCompatActivity {
    private EditText display;
    private Calculadora calc;
    private boolean novoNumero = true;
    private DecimalFormat fmt = new DecimalFormat("#,##0.00;-#,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = (EditText)findViewById(R.id.edNum);
        display.setKeyListener(null);

        calc = new Calculadora();
    }

    public void numeroClicked(View view) {
        Button botao = (Button)view;
        String num = botao.getText().toString();

        if(novoNumero) {
            display.setText(num);
            novoNumero = false;
        } else {
            String textoDaTela = display.getText().toString();

            if (textoDaTela.equals("0") && !textoDaTela.equals("0,")) {
                display.setText(num);
            } else {
                display.setText(textoDaTela + num);
            }
        }
    }

    public void opClicked(View view) {
        try {
            Button botao = (Button) view;
            String operacao = botao.getText().toString();
            String textoDaTela = display.getText().toString();

            if (operacao.equals("C")) {
                calc.reseta();
                display.setText(fmt.format(calc.resultado()));
            } else if (operacao.equals("+/-")) {
                if(textoDaTela.startsWith("-")) {
                    String[] partes = textoDaTela.split("-");
                    display.setText(partes[1]);
                } else {
                    display.setText("-" + textoDaTela);
                }
            } else if (operacao.equals(",")) {
                if(!textoDaTela.contains(",")) {
                    display.setText(textoDaTela + ",");
                    novoNumero = false;
                }
            } else {
                double num = fmt.parse(textoDaTela).doubleValue();
                calc.entra(num);

                if (operacao.equals("=")) {
                    display.setText(fmt.format(calc.resultado()));
                } else {
                    calc.setOperacao(operacao);
                }
                novoNumero = true;
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
