package br.senai.sp.informatica.mobileb.respostas.ex08;

import static br.senai.sp.informatica.mobileb.lib.Util.*;

public class ExemploCalcule2 {
	public static void main(String[] args) {
		String produto = leTexto("Informe o Nome do Produto");
		double valor = leReal("Informe o Preço");
		double preco = calcula(valor);
		escreva("O produto ", produto, " terá um custo de R$", preco);
	}

	static double calcula(double valor) {
		double taxa = 1.15;
		return valor * taxa;
	}
}
