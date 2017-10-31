package br.senai.sp.informatica.mobileb.jdbc.respostas.ex03.model;

import java.util.List;

public class Pedido {
	private Integer id;
	private int numero;
	private int codCliente;
	private String endEntrega;
	private List<Item> itens;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(int codCliente) {
		this.codCliente = codCliente;
	}

	public String getEndEntrega() {
		return endEntrega;
	}

	public void setEndEntrega(String endEntrega) {
		this.endEntrega = endEntrega;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	@Override
	public String toString() {
		return "id: " + id + " numero: " + numero + " codCliente: "
				+ codCliente + " endEntrega: " + endEntrega + " itens: "
				+ itens;
	}

}
