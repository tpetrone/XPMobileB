package br.senai.sp.informatica.mobileb.respostas.ex08.respostas;

import static br.senai.sp.informatica.mobileb.lib.Util.*; 


public class Ex04 {
	public static void main(String[] args) {
		tabuada(leInteiro("Informe o nº"));
	}

	static void tabuada(int num) {
		for (int i = 1; i <= 10; i++)
			escreva(num, " x ", i, " = ", num * i);
	}
}
