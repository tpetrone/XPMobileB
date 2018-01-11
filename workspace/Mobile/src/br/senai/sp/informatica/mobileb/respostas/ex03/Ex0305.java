package br.senai.sp.informatica.mobileb.respostas.ex03;

import br.senai.sp.informatica.mobileb.lib.Util;

public class Ex0305 {
	public static void main(String[] args) {
		int num1 = Util.leInteiro("Informe o 1º nº");
		int num2 = Util.leInteiro("Informe o 2º nº");

		if(num1 > num2) {
			Util.escreva(num2 , " ", num1);
		} else {
			Util.escreva(num1 , " ", num2);
		}
	}
}
