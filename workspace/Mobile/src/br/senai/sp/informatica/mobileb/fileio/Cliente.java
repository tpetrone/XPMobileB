package br.senai.sp.informatica.mobileb.fileio;

import java.io.Serializable;

public class Cliente implements Serializable {
	private static final long serialVersionUID = 3488369410536736741L;
	private String nomeFantasia;
	private String razaoSocial;
	private String cnpj;

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}


	@Override
	public String toString() {
		return "nomeFantasia: " + nomeFantasia + " razaoSocial: " + razaoSocial + " cnpj: " + cnpj;
	}

	
}
