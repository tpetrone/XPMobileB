package br.senai.sp.informatica.mobileb.exemplos.vetor;

import br.senai.sp.informatica.mobileb.lib.Util;

public class ExmploVetor {
	public static void main(String[] args) {
		int qtd = Util.leInteiro("Informe a quantidade de Nomes");
		String[] lista = new String[qtd];
		
		for (int i = 0; i < lista.length; i++) {
			lista[i] = Util.leTexto("Informe o ",i+1,"º Nome");
		}
		
		String pesquisa = Util.leTexto("Informe o nome à pesquisar");
		while(!pesquisa.equals("fim")) {
			
			boolean achei = false;
			for (int i = 0; i < lista.length; i++) {
				if(pesquisa.equals(lista[i])) { 
					achei = true;
					break;
				}
			}
			if(achei) {
				Util.escreva("O nome ", pesquisa, " foi encontrado");
			} else {
				Util.escreva("O nome ", pesquisa, " não foi encontrado");
			}
			
			pesquisa = Util.leTexto("Informe o nome à pesquisar");
		}
	}
}
