package br.senai.sp.informatica.mobileb.jdbc.respostas.ex03.model;

public class Item {
	private Integer id;
	private int codProd;
	private double valor;
	private int quantidade;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getCodProd() {
		return codProd;
	}

	public void setCodProd(int codProd) {
		this.codProd = codProd;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public String toString() {
		return "id: " + id + " codProd: " + codProd + " valor: " + valor
				+ " quantidade: " + quantidade;
	}

}
