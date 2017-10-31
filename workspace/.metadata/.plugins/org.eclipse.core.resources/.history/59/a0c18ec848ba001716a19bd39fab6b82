package fundamentos.lib;

import java.awt.Color;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class VerificaInteiro extends InputVerifier implements Validador {
	// Label para apresentar a mensagem
	private JLabel msg;
	// Indicador que o campo não está OK
	private StatusValidador ok = StatusValidador.EM_BRANCO;
	private Color fore = Color.white;
	private Color back = Color.red;
	private boolean usaCor;

	public VerificaInteiro() {
		usaCor = true;
	}

	public VerificaInteiro(Color fore, Color back) {
		usaCor = true;
		this.fore = fore;
		this.back = back;
	}

	public VerificaInteiro(JLabel err) {
		msg = err;
		usaCor = false;
	}

	public VerificaInteiro(JLabel err, boolean cor) {
		msg = err;
		usaCor = cor;
	}

	public StatusValidador isOk() {
		return ok;
	}

	@Override
	public boolean verify(JComponent input) {
		if (input instanceof JTextField) {
			JTextField textField = (JTextField) input;
			String txt = textField.getText();

			try {
				if(txt.isEmpty()) {
					ok = StatusValidador.EM_BRANCO;
				} else {
					NumberFormat nf = NumberFormat.getIntegerInstance();
					nf.parse(txt);
					ok = StatusValidador.OK;
				}
				if(msg != null)
					msg.setText("");
				if(usaCor) {
					input.setBackground(Color.white);
					input.setForeground(Color.black);
				}
			} catch (ParseException ex) {
				ok = StatusValidador.ERRO;
				if(msg != null)
					msg.setText("Número Inválido");
				if(usaCor) {
					input.setBackground(back);
					input.setForeground(fore);
				}
			}
		}

		return true;
	}

}