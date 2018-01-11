package br.senai.sp.informatica.mobileb.respostas.ex03;

import br.senai.sp.informatica.mobileb.lib.Util;

public class Ex0302 {
	public static void main(String[] args) {
		int num1 = Util.leInteiro("Informe o 1º nº");
		
		if(num1 % 2 == 0) {
			Util.escreva("é par");
		} else {
			Util.escreva("é impar");
		}
	}
}
