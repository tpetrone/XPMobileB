package br.senai.sp.informatica.mobileb.respostas.ex05;

import br.senai.sp.informatica.mobileb.lib.Util;

public class Ex0503 {
	public static void main(String[] args) {
		int soma = 0;
		for (int i = 0; i < 5; i++) {
			soma += Util.leInteiro("Informe ", i + 1, "º nº");
		}
		Util.escreva("A Soma: ", soma, "\nA Média: ", soma / 5.0);
	}
}
