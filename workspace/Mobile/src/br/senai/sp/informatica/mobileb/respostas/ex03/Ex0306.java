package br.senai.sp.informatica.mobileb.respostas.ex03;

import br.senai.sp.informatica.mobileb.lib.Util;

public class Ex0306 {
	public static void main(String[] args) {
		int idade = Util.leInteiro("Informe a sua idade");

		String categoria;
		
		if(idade <= 10) {
			categoria = "Infantil";
		} else if(idade <= 15) { 
			categoria = "Infanto";
		} else if(idade <= 18) { 
			categoria = "Juvenil";
		} else { 
			categoria = "Adulto";
		}
		
		Util.escreva("Classificação é: ", categoria);
	}
}
