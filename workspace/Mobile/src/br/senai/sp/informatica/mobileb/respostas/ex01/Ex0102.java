package br.senai.sp.informatica.mobileb.respostas.ex01;

import br.senai.sp.informatica.mobileb.lib.Util;

public class Ex0102 {
	public static void main(String[] args) {
		double media = Util.leInteiro("Informe o 1ª nota");
		media += Util.leInteiro("Informe o 2ª nota");
		media += Util.leInteiro("Informe o 3ª nota");
		media += Util.leInteiro("Informe o 4ª nota");
		
		media /= 4;
		
		Util.escreva("a média é: " , media);
	}
	

}
