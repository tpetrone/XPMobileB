package br.senai.sp.informatica.mobileb.respostas.vetor;

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
		
		boolean estaEmOrdem;
		do {
			estaEmOrdem = true;
			for (int i = 0; i < 8-1; i++) {
				if(lista[i].compareTo(lista[i+1]) > 0) {
					String temp = lista[i];
					lista[i] = lista[i+1];
					lista[i+1] = temp;
					estaEmOrdem = false;
				}
			}
		} while(!estaEmOrdem);

		
		
		String texto = "";
		for (int i = 0; i < indice; i++) {
			texto += lista[i] + "\n";
		}
		Util.escreva(texto);
	}
}
