package fundamentos.lib;

import java.awt.Color;
import java.text.DateFormat;
import java.text.ParseException;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class VerificaData extends InputVerifier implements Validador {
	private JLabel msg;
	private StatusValidador ok = StatusValidador.EM_BRANCO;
	private DateFormat dateFormat = DateFormat.getDateInstance();
	private Color fore = Color.white;
	private Color back = Color.red;
	private boolean usaCor;

	public VerificaData() {
		usaCor = true;
		dateFormat.setLenient(false);
	}

	public VerificaData(DateFormat format) {
		usaCor = true;
		this.dateFormat = format;
		dateFormat.setLenient(false);
	}

	public VerificaData(Color fore, Color back) {
		usaCor = true;
		this.fore = fore;
		this.back = back;
		dateFormat.setLenient(false);
	}

	public VerificaData(DateFormat format, Color fore, Color back) {
		usaCor = true;
		this.fore = fore;
		this.back = back;
		this.dateFormat = format;
		dateFormat.setLenient(false);
	}

	public VerificaData(JLabel err) {
		msg = err;
		usaCor = false;
		dateFormat.setLenient(false);
	}

	public VerificaData(JLabel err, DateFormat format) {
		msg = err;
		usaCor = false;
		this.dateFormat = format;
		dateFormat.setLenient(false);
	}

	public VerificaData(JLabel err, boolean cor) {
		msg = err;
		usaCor = cor;
		dateFormat.setLenient(false);
	}

	public VerificaData(JLabel err, DateFormat format, boolean cor) {
		msg = err;
		usaCor = cor;
		this.dateFormat = format;
		dateFormat.setLenient(false);
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
					dateFormat.parse(txt);
					ok =  StatusValidador.OK;
				}
				if(!usaCor)
					msg.setText("");
				else {
					input.setBackground(Color.white);
					input.setForeground(Color.black);
				}
			} catch (ParseException ex) {
				ok = StatusValidador.ERRO;
				if(msg != null)
					msg.setText("Data Inválida");
				if(usaCor) {
					input.setBackground(back);
					input.setForeground(fore);
				}
			}
		}

		return true;
	}
}