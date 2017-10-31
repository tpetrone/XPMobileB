package api.jdbc.exemplos.MVCcursoLista.controller;

import java.util.HashMap;
import java.util.Map;

import api.jdbc.exemplos.MVCcursoLista.model.Curso;
import api.jdbc.exemplos.MVCcursoLista.model.CursoDao;
import api.jdbc.exemplos.MVCcursoLista.model.generic.DaoException;
import api.jdbc.exemplos.MVCcursoLista.view.CadCurso;
import api.jdbc.exemplos.MVCcursoLista.view.CursoTableModel;

public class Controller {
	private Map<Integer, Curso> mapa;
	private CursoDao dao;
	private CursoTableModel tableModel;
	
	public Controller() throws DaoException {
		dao = new CursoDao();   // <<== inicializa o Dao
		tableModel = new CursoTableModel(this);  // <<== inicializa o TableModel
		criaMapa();  // <<== constroi o mapa de correlação entre as linhas do JTable e os Registros do Banco de Dados
		CadCurso view = new CadCurso(this, tableModel);  // <<== cria a Janela principal passando as referências do controller e do TableModel
		view.setVisible(true);  // <<== apresenta a Janela na Tela do computador
	}
		
	private void criaMapa() throws DaoException {
		int id = 0;
		mapa = new HashMap<>();
		for (Curso obj : dao.listaTodos()) {
			mapa.put(id++, obj);
		}
	}
	
	public void atualiza(Curso obj) {
		try {
			dao.salvar(obj);
		} catch (DaoException ex) {
			ex.printStackTrace();
		}
	}
	
	public void adiciona(Curso obj) throws DaoException {
		dao.salvar(obj);
		criaMapa();
		tableModel.fireTableDataChanged();
	}
	
	public void remove(int linha) throws DaoException {
		Curso obj = mapa.get(linha);
		dao.apaga(obj.getId());
		criaMapa();
		tableModel.fireTableDataChanged();
	}

	public Curso localiza(int linha) {
		return mapa.get(linha);
	}
	
	public int quantos() {
		return mapa.size();
	}
}
