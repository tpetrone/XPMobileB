package br.senai.sp.informatica.mobileb.respostas.ex01;

import br.senai.sp.informatica.mobileb.lib.Util;
//import static br.senai.sp.informatica.mobileb.lib.Util.*;

public class Ex0101 {
	public static void main(String[] args) {
		int num1 = Util.leInteiro("Informe o 1º nº");
		int num2 = Util.leInteiro("Informe o 2º nº");
		
		double resultado = Math.pow(num1, num2);
		
		Util.escreva("O Total é: " , resultado);
	}
	

}
