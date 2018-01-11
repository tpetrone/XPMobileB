package br.senai.sp.informatica.mobileb.respostas.ex05;

import br.senai.sp.informatica.mobileb.lib.Util;

public class Ex0504 {
	public static void main(String[] args) {
		int qtd = Util.leInteiro("Informe a quantidade");
		int oMaior = Integer.MIN_VALUE;
		String texto = "";
		for (int i = 0; i < qtd; i++) {
			int num = Util.leInteiro("Informe o ", i + 1, "º nº");
			
			texto += String.format("%dº nº: %d%n", i+1, num);
			
			if(oMaior < num) {
				oMaior = num;
			}
		}
		Util.escreva(texto, "\n\nO maior: " , oMaior);
	}
}
