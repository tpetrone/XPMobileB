package br.senai.sp.informatica.mobileb.respostas.ex08.respostas;

import static br.senai.sp.informatica.mobileb.lib.Util.*; 

public class ExPunk {
	public static void main(String[] args) {
		int limite = leInteiro("Informe o limite");
		int num = 3;
		int total = 0;
		for (int i = 0; num <= limite; i++) {
			if(num == 3)
				escreva(num);
			else
				escreva(", ", num);
			
			total += num;
			num += i % 2 == 0 ? 2 : 5;
		}
		escreva("\nTotal: ", total);
	}
}
