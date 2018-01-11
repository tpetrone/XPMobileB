package fundamentos.lib;

import java.awt.Color;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class TableVerificaInteiro extends InputVerifier {
	private boolean ok = true;
	private Color fore = Color.white;
	private Color back = Color.red;
	private boolean defaultColor = true;

	public TableVerificaInteiro() {
		this(null, null);
	}

	public TableVerificaInteiro(Color fore, Color back) {
		if (fore != null && back != null) {
			this.fore = fore;
			this.back = back;
			defaultColor = false;
		}
	}

	public boolean isOk() {
		return ok;
	}

	@Override
	public boolean verify(JComponent input) {
		if (input instanceof JTextField) {
			JTextField textField = (JTextField) input;

			try {
				NumberFormat fmt = NumberFormat.getInstance();
				
				fmt.parse(textField.getText());
				ok = true;
				input.setBackground(Color.white);
				input.setForeground(Color.black);
			} catch (ParseException ex) {
				ok = false;
				if (defaultColor) {
					input.setBackground(Color.red);
					input.setForeground(Color.white);
				} else {
					input.setBackground(back);
					input.setForeground(fore);
				}
			}
		}

		return ok;
	}

}