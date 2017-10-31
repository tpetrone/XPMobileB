package br.senai.sp.informatica.mobileb.respostas.ex01;

import br.senai.sp.informatica.mobileb.lib.Util;

public class Ex0103 {
	public static void main(String[] args) {
		double area = Util.leInteiro("Informe a base");
		area *= Util.leInteiro("Informe a altura");
		area /= 2;
		
		Util.escreva("A Área é de: " , area);
	}
	

}
