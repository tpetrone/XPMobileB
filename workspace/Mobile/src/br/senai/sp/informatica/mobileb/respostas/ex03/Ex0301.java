package br.senai.sp.informatica.mobileb.respostas.ex03;

import br.senai.sp.informatica.mobileb.lib.Util;

public class Ex0301 {
	public static void main(String[] args) {
		int num1 = Util.leInteiro("Informe o 1º nº");
		int num2 = Util.leInteiro("Informe o 1º nº");
		
		if(num1 != num2) {
			Util.escreva("são diferentes");
		} else {
			Util.escreva("são iguais");
		}
	}
}
