package br.senai.sp.informatica.mobileb.respostas.ex08.respostas;

import static br.senai.sp.informatica.mobileb.lib.Util.*; 


public class Ex05 {
	public static void main(String[] args) {
		int num = leInteiro("Informe um nº para o cálculo do Fatorial");
		escreva("O valor calculado com a dunção fatorial para ", num
				, " é:", fat(num));
	}
	
	// fat(n) => fat(n-1) * n para n > 1
	static int fat(int n) {
		if(n > 1)
			return fat(n-1) * n;
		else
			return 1;
	}
}
