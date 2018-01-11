package br.senai.sp.informatica.mobileb.respostas.ex08.respostas;

import static br.senai.sp.informatica.mobileb.lib.Util.*; 


public class Ex02 {
	public static void main(String[] args) {
		int num = leInteiro("Informe o nº");

		escreva("O inverso do nº : ", num, " é " , inverso(num));
	}
	
	static int inverso(int num) {
		int inv = 0;

		while (num > 0) {
			inv = inv * 10 + num % 10;
			num = num / 10;
		}

		return inv;
	}
}
