package br.senai.sp.informatica.mobileb.exemplos.vetor;

import java.util.ArrayList;
import java.util.List;

import br.senai.sp.informatica.mobileb.lib.Util;

public class ExmploList {
	public static void main(String[] args) {
		int qtd = Util.leInteiro("Informe a quantidade de Nomes");
		List<String> lista = new ArrayList<>();
		
		for (int i = 0; i < qtd; i++) {
			lista.add(Util.leTexto("Informe o ",i+1,"º Nome"));
		}
		
		String pesquisa = Util.leTexto("Informe o nome à pesquisar");
		while(!pesquisa.equals("fim")) {
			
			boolean achei = false;
			for (int i = 0; i < qtd; i++) {
				if(pesquisa.equals(lista.get(i))) { 
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
