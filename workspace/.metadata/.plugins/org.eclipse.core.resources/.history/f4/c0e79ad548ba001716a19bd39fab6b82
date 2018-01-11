package api.jdbc.exemplos.cursoLista;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import fundamentos.lib.SwUtil;

@SuppressWarnings("serial")
public class CursoModel2 extends AbstractTableModel {
	private GerenteDeDados dados;  // <==
	private Map<Integer, Integer> ids;  // <==
	private DateTimeFormatter dateFormat = SwUtil.getDateTimeFormatter();

	public CursoModel2(GerenteDeDados dados) { // <==
		this.dados = dados;
		atualizaIds(); 
	}

	private void atualizaIds() {  // <==
		ids = new HashMap<Integer, Integer>();
		try {
			List<Curso> lista = dados.listaTodos();

			for (int i = 0; i < lista.size(); i++) {
				ids.put(i, lista.get(i).getId());
			}
		} catch (GerenteException ex) {
			ex.printStackTrace();
		}
	}

	public void adiciona(Curso obj) throws GerenteException {
		dados.salvar(obj);
		atualizaIds(); // <==
		fireTableDataChanged();
	}

	public void remove(int row) throws GerenteException {
		dados.remover(ids.get(row));  // <==
		atualizaIds();  // <==
		fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		return ids.size(); // <==
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnName(int col) {
		String nome = "";
		switch (col) {
		case 0:
			nome = "Nome";
			break;
		case 1:
			nome = "Matrícula";
			break;
		case 2:
			nome = "Descrição";
			break;
		}

		return nome;
	}

	@Override
	public Object getValueAt(int row, int col) {
		Object valor = null;
		try {
			Curso obj = dados.localiza(ids.get(row)); // <==

			switch (col) {
			case 0:
				valor = obj.getNome();
				break;
			case 1:
				valor = obj.getMatricula().format(dateFormat);
//				valor = dateFormat.format(obj.getMatricula());
				break;
			case 2:
				valor = obj.getDescricao();
				break;
			}
		} catch (GerenteException ex) {
			ex.printStackTrace();
		}
		return valor;
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		try {
			Curso obj = dados.localiza(ids.get(row));  // <==

			switch (col) {
			case 0:
				obj.setNome((String) value);
				break;
			case 1:
				obj.setMatricula(LocalDate.parse((String)value, dateFormat));
//				obj.setMatricula(dateFormat.parse((String) value));
				break;
			case 2:
				obj.setDescricao((String) value);
				break;
			}
			dados.salvar(obj);  // <====
		} catch (DateTimeParseException | GerenteException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}


}
