package br.senai.sp.informatica.mobileb.respostas.ex02;

import br.senai.sp.informatica.mobileb.lib.Util;

public class Ex0202a {
	public static void main(String[] args) {
		int num = Util.leInteiro("Informe um ºn");
		int inv = 0;

		while (num > 0) {
			inv = inv * 10 + num % 10;
			num = num / 10;
		}

		Util.escreva("inv: ", inv, " num: ", num);
	}

}
