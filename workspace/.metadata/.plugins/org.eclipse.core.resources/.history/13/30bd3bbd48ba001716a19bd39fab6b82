package fundamentos.lib;

import java.awt.Color;
import java.util.regex.Pattern;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class TableVerificaRegex extends InputVerifier {
	private boolean ok = true;
	private Pattern ptrn = null;
	private Color fore = Color.white;
	private Color back = Color.red;
	private boolean defaultColor = true;

	public TableVerificaRegex(String regex) {
		this(regex, null, null);
	}

	public TableVerificaRegex(String regex, Color fore, Color back) {
		ptrn = Pattern.compile(regex);
		if (fore != null && back != null) {
			this.fore = fore;
			this.back = back;
			defaultColor = false;
		}
	}

	@Override
	public boolean verify(JComponent input) {
		if (input instanceof JTextField) {
			JTextField textField = (JTextField) input;
			String txt = textField.getText();

			if (txt.matches(ptrn.pattern())) {
				ok = true;
				input.setBackground(Color.white);
				input.setForeground(Color.black);
			} else {
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
