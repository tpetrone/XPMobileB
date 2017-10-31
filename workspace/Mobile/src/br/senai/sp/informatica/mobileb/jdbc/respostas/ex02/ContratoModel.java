package br.senai.sp.informatica.mobileb.jdbc.respostas.ex02;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import br.senai.sp.informatica.mobileb.jdbc.respostas.lib.GerenteException;


@SuppressWarnings("serial")
public class ContratoModel extends AbstractTableModel {
	private String[] colunas = {  "Número", "Data", "Descrição" };
	private GerenteDeDados dados;
	private Map<Integer, Integer> ids;

	public ContratoModel(GerenteDeDados dados) {
		this.dados = dados;
		atualizaIds();
	}

	private void atualizaIds() {
		ids = new HashMap<Integer, Integer>();
		try {
			List<Contrato> lista = dados.listaTodos();

			for (int i = 0; i < lista.size(); i++) {
				ids.put(i, lista.get(i).getId());
			}
		} catch (GerenteException ex) {
			ex.printStackTrace();
		}
	}

	public void adiciona(Contrato obj) throws GerenteException {
		dados.salvar(obj);
		atualizaIds();
		fireTableDataChanged();
	}

	public void remove(int row) throws GerenteException {
		dados.remover(ids.get(row));
		atualizaIds();
		fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		return ids.size();
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
	public Object getValueAt(int row, int col) {
		Object valor = null;
		try {
			Contrato obj = dados.localiza(ids.get(row));

			switch (col) {
			case 0:
				valor = obj.getNumero();
				break;
			case 1:
				valor = obj.getDataFormatada();
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
			Contrato obj = dados.localiza(ids.get(row));

			switch (col) {
			case 0:
				obj.setNumero((String) value);
				break;
			case 1:
				try {
					obj.setData((String) value);
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				break;
			case 2:
				obj.setDescricao((String) value);
				break;
			}
			dados.salvar(obj);
		} catch (GerenteException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}
}
