package br.senai.sp.informatica.mobileb.exemplos.vetor;

import br.senai.sp.informatica.mobileb.lib.Util;

import java.util.Arrays;

public class ExemploVetor2 {
	public static void main(String[] args) {
		String[] lista = new String[3];
		int indice = 0;
		
		String nome = Util.leTexto("Informe o ",indice+1,"º Nome");
		while(!nome.equals("fim")) {
			if(indice >= lista.length) {
				lista = Arrays.copyOf(lista, lista.length + 3);
			}
			lista[indice++] = nome;
			
			nome = Util.leTexto("Informe o ",indice+1,"º Nome");
		}
		
		// <<====
		
		
		String texto = "";
		for (int i = 0; i < indice; i++) {
			texto += lista[i] + "\n";
		}
		Util.escreva(texto);
	}
}
