package br.senai.sp.informatica.mobileb.respostas.ex04;

import br.senai.sp.informatica.mobileb.lib.Util;

public class Ex0401 {
	public static void main(String[] args) {
		double media = Util.leReal("Informe a 1ª nota");
		media = media + Util.leReal("Informe a 2ª nota");
		media += Util.leReal("Informe a 3ª nota");
		media += Util.leReal("Informe a 4ª nota");
		media /= 4;
		
		String situacao = "";
		if(media >= 7) {
			situacao = "Aprovado";
		} else {
			situacao = "Reprovado";
		}
		
		Util.escreva(situacao);
	}
}
