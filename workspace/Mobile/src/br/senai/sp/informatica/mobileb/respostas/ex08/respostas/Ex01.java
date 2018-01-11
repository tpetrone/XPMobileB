package br.senai.sp.informatica.mobileb.respostas.ex08.respostas;

import static br.senai.sp.informatica.mobileb.lib.Util.*;

public class Ex01 {
	public static void main(String[] args) {
		int num = leInteiro("Informe um nº");
		escreva("Este nº é ", ehPar(num) ? "par" : "impar");
	}

	static boolean ehPar(int num) {
		return num % 2 == 0;
	}
}
