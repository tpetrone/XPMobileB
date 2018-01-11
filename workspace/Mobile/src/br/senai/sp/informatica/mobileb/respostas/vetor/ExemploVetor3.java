package br.senai.sp.informatica.mobileb.respostas.vetor;

import br.senai.sp.informatica.mobileb.lib.Util;

import java.util.Arrays;

public class ExemploVetor3 {
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
		
		Arrays.sort(lista);
		
		String texto = "";
		for (int i = 0; i < indice; i++) {
			texto += lista[i] + "\n";
		}
		Util.escreva(texto);
	}
}
