package br.senai.sp.informatica.mobileb.respostas.ex03;

import br.senai.sp.informatica.mobileb.lib.Util;

public class Ex0303 {
	public static void main(String[] args) {
		int num1 = Util.leInteiro("Informe o 1º nº");
		int num2 = Util.leInteiro("Informe o 2º nº");
		
		if(num1 % num2 == 0) {
			Util.escreva("é divisível");
		} else {
			Util.escreva("não é divisível");
		}
	}
}
