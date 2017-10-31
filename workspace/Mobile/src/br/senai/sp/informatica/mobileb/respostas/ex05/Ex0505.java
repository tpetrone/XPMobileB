package br.senai.sp.informatica.mobileb.respostas.ex05;

import br.senai.sp.informatica.mobileb.lib.Util;

public class Ex0505 {
	public static void main(String[] args) {
		double salario = Util.leReal("Informe o Salário");
		while(salario > 0) {
//			if(salario <= 500) {
//				salario = salario * 1.2;
//			} else {
//				salario = salario * 1.1;
//			}
			
			salario = salario * ( salario <= 500 ? 1.2 : 1.1 );
			
			salario = Util.leReal("Informe o Salário");
		}
	}
}
