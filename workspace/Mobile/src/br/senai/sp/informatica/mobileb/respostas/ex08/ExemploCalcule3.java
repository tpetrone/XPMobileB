package br.senai.sp.informatica.mobileb.respostas.ex08;

import static br.senai.sp.informatica.mobileb.lib.Util.*;

public class ExemploCalcule3 {
	public static void main(String[] args) {
		escreva("O produto ", leTexto("Informe o Nome do Produto"), " terá um custo de R$",
				calcula(leReal("Informe o Preço")));
	}

	static double calcula(double valor) {
		double taxa = 1.15;
		return valor * taxa;
	}
}
