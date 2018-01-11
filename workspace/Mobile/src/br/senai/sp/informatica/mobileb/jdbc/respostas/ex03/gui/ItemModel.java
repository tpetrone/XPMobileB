package br.senai.sp.informatica.mobileb.jdbc.respostas.ex03.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.senai.sp.informatica.mobileb.jdbc.respostas.ex03.model.Item;


@SuppressWarnings("serial")
public class ItemModel extends AbstractTableModel {
	private List<Item> lista ;
	
	public ItemModel(List<Item> lista) {
		this.lista = lista;
	}
	
	public void setLista(List<Item> lista) {
		this.lista = lista;
		fireTableDataChanged();
	}
	
	public void add(Item obj) {
		lista.add(obj);
		fireTableDataChanged();
	}
	
	public void del(int id) {
		lista.remove(id);
		fireTableDataChanged();
	}
	
	@Override
	public int getRowCount() {
		return lista.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnName(int col) {
		String nome = null;
		
		switch (col) {
		case 0:
			nome = "Cod. Prod.";
			break;
		case 1:
			nome = "Valor";
			break;
		case 2:
			nome = "Quant.";
			break;
		}
		return nome;
	}

	@Override
	public Object getValueAt(int row, int col) {
		Object obj = null;
		Item item = lista.get(row);
		
		switch (col) {
		case 0:
			obj = String.valueOf(item.getCodProd());
			break;
		case 1:
			obj = String.valueOf(item.getValor());
			break;
		case 2:
			obj = String.valueOf(item.getQuantidade());
			break;
		}
		
		return obj;
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		Item item = lista.get(row);
		
		switch (col) {
		case 0:
			item.setCodProd(Integer.parseInt(value.toString()));
			break;
		case 1:
			item.setValor(Double.parseDouble(value.toString()));
			break;
		case 2:
			item.setQuantidade(Integer.parseInt(value.toString()));
			break;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

}
