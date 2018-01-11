package fundamentos.lib;

import javax.swing.DefaultCellEditor;
import javax.swing.InputVerifier;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class VerificaCellEditor extends DefaultCellEditor {
	private InputVerifier verifier;
	
	public VerificaCellEditor(InputVerifier verifier) {
		super(new JTextField());
		this.verifier = verifier;
	}
	
	public VerificaCellEditor(JTextField tf, InputVerifier verifier) {
		super(tf);
		this.verifier = verifier;
	}

	@Override
	public boolean stopCellEditing() {
		return verifier.verify(editorComponent) && super.stopCellEditing();
	}
}
