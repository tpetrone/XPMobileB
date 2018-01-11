package br.senai.sp.informatica.mobileb.respostas.ex02;

import br.senai.sp.informatica.mobileb.lib.Util;

public class Ex0201a {
	public static void main(String[] args) {
		Util.escreva("O Preço final é de: R$ ", 
				Util.leReal("Informe a Largura") *
				Util.leReal("Informe o Comprimento") *
				Util.leReal("Informe a Profundidade") * 45);
	}
}
