package br.senai.sp.informatica.mobileb.exemplos.vetor;

import br.senai.sp.informatica.mobileb.lib.Util;

public class ExemploTroca {
	public static void main(String[] args) {
		String[] nomes  = {"Joaquim", "Antonio"};
		
		if(nomes[0].compareTo(nomes[1]) > 0) {
			String temp = nomes[0];
			nomes[0] = nomes[1];
			nomes[1] = temp;
		}
		Util.escreva(nomes[0] , " ", nomes[1]);
	}
}
