package br.senai.sp.informatica.mobileb.respostas.ex02;

import br.senai.sp.informatica.mobileb.lib.Util;

public class Ex0203 {
	public static void main(String[] args) {
		int valor = Util.leInteiro("Informe o valor do Saque");
		
		//545 ==> 5.45
				
		String msg = "Notas de 100: " + valor / 100 + "\n";
		valor = valor % 100;
		msg += "Notas de 50: "+ valor / 50 + "\n";
		valor = valor % 50;
		msg += "Notas de 20: "+ valor / 20 + "\n";
		valor = valor % 20;
		msg += "Notas de 10: "+ valor / 10 + "\n";
		valor = valor % 10;
		msg += "Notas de 5: "+ valor / 5 + "\n";
		valor = valor % 5;
		msg += "Notas de 2: "+ valor / 2 + "\n";
		valor = valor % 2;
		msg += "Notas de 1: "+ valor ;

		Util.escreva(msg);
		
	}
}
