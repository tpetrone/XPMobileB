package api.jdbc.exemplos.MVCcursoLista.view;

import java.time.LocalDate;

import javax.swing.table.AbstractTableModel;

import api.jdbc.exemplos.MVCcursoLista.controller.Controller;
import api.jdbc.exemplos.MVCcursoLista.model.Curso;
import fundamentos.lib.SwUtil;

@SuppressWarnings("serial")
public class CursoTableModel extends AbstractTableModel {
	private String[] colunas = { "Nome", "Matrícula", "Descrição" };
	private Controller ctrl;
	
	public CursoTableModel(Controller instancia) {
		ctrl = instancia;
	}
	
	@Override
	public int getRowCount() {
		return ctrl.quantos();
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public String getColumnName(int col) {
		return colunas[col];
	}

	@Override
	public Object getValueAt(int lin, int col) {
		Object valor = null;
		
		// utilizar o Dao para localizar o objeto Curso por id
		Curso obj = ctrl.localiza(lin);
		switch (col) {
		case 0:
			valor = obj.getNome();
			break;
		case 1:
			valor = obj.getMatricula().format(SwUtil.getDateTimeFormatter());
			break;
		case 2:
			valor = obj.getDescricao();
			break;
		}
		
		return valor;
	}

	@Override
	public void setValueAt(Object valor, int lin, int col) {
		Curso obj = ctrl.localiza(lin);
		
		switch (col) {
		case 0:
			obj.setNome((String)valor);
			break;
		case 1:
			obj.setMatricula(LocalDate.parse((String)valor, SwUtil.getDateTimeFormatter()));
			break;
		case 2:
			obj.setDescricao((String)valor);
			break;
		}
		// utilizar o Dao para salvar o objeto Curso modificado
		ctrl.atualiza(obj);

	}

	@Override
	public boolean isCellEditable(int lin, int col) {
		return true;
	}
}
