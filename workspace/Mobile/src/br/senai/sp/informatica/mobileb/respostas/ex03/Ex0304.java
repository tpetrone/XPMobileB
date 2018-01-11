package br.senai.sp.informatica.mobileb.respostas.ex03;

import br.senai.sp.informatica.mobileb.lib.Util;

public class Ex0304 {
	/*
	 *      0 ate  299 taxa 5%
	 *    300 ate 1200 taxa 10%
	 *  acima  de 1200 taxa 15%
	 */
	
	public static void main(String[] args) {
		double sal = Util.leReal("Informe o salário");
		
		double salLiq;
		
		if(sal < 300) {
			salLiq = sal * 0.95;
		} else if(sal <= 1200) {
			salLiq = sal * 0.9;
		} else {
			salLiq = sal * 0.85;
		}
		
		Util.escreva("O salário líquido é de R$ ", salLiq);
	}
}
