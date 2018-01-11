package br.senai.sp.informatica.mobileb.respostas.ex04;

import br.senai.sp.informatica.mobileb.lib.Util;

public class Ex0403 {
	public static void main(String[] args) {
		int a = Util.leInteiro("Informe o valor de A");
		int b = Util.leInteiro("Informe o valor de B");
		int c = Util.leInteiro("Informe o valor de C");
		
		double D = Math.pow(b, 2) - 4 * a * c;
		
		if(D >= 0) {
			double x1 = (-b + Math.sqrt(D)) / 2 * a;
			double x2 = (-b - Math.sqrt(D)) / 2 * a;
			
			Util.escreva("x' = ", (int)x1, "\nx'' = ", (int)x2);
		} else {
			Util.escreva("Valor Indeterminado");
		}
	}
}
