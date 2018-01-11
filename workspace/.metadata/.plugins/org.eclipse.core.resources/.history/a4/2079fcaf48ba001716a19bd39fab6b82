package api.jdbc.exemplos.cursoLista;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import fundamentos.lib.SwUtil;

@SuppressWarnings("serial")
public class CursoModel extends AbstractTableModel {
	private String[] colunas = { "Nome", "Matrícula", "Descrição" };
	private Map<Integer, Curso> mapa;
	
	public CursoModel() throws GerenteException {
		criaMapa();
	}
	
	private void criaMapa() throws GerenteException {
		int id = 0;
		mapa = new HashMap<>();
		for (Curso obj : CursoDao.manager.listaTodos()) {
			mapa.put(id++, obj);
		}
	}
	
	public void adiciona(Curso obj) throws GerenteException {
		CursoDao.manager.salvar(obj);
		criaMapa();
		fireTableDataChanged();
	}
	
	public void remove(int linha) throws GerenteException {
		Curso obj = mapa.get(linha);
		CursoDao.manager.apaga(obj.getId());
		criaMapa();
		fireTableDataChanged();
	}
	
	@Override
	public int getRowCount() {
		return mapa.size();
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
		Curso obj = mapa.get(lin);
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
		Curso obj = mapa.get(lin);
		
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
		try {
			CursoDao.manager.salvar(obj);
		} catch (GerenteException ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public boolean isCellEditable(int lin, int col) {
		return true;
	}
}
