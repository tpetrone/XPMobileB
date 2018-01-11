package br.senai.sp.informatica.mobileb.jdbc.respostas.ex02;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class Contrato {
	private Integer id;
	private Date data;
	private String numero;
	private String descricao;

	private static DateFormat dateFormat;
	
	static {
	  dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
	  dateFormat.setLenient(false);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public String getDataFormatada() {
		return dateFormat.format(data);
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setData(String data) throws Exception {
		try {
			Date dt = dateFormat.parse(data);
			this.data = dt;
		} catch (ParseException ex) {
			throw new Exception("Data Inválida");
		}
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "Data: " + getDataFormatada() + " Nº: " + numero + " Descr.: " + descricao;
	}
}
