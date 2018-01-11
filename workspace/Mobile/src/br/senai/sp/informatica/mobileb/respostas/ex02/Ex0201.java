package br.senai.sp.informatica.mobileb.respostas.ex02;

import br.senai.sp.informatica.mobileb.lib.Util;

public class Ex0201 {
	public static void main(String[] args) {
		double largura = Util.leReal("Informe a Largura");
		double comprimento = Util.leReal("Informe o Comprimento");
		double profundidade = Util.leReal("Informe a Profundidade");
		
		double volume = largura * comprimento * profundidade;
		
		double preco = volume * 45;
		
		Util.escreva("O Preço final é de: R$ ", preco);
	}
}
