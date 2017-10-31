package br.senai.sp.informatica.mobileb.exemplos;

import java.util.Scanner;

public class Exemplo1 {
	public static void main(String[] args) {
		Scanner console  = new Scanner(System.in);
		
		System.out.print("Informe seu nome: ");
		String nome = console.nextLine();
		
		System.out.print("Informe sua idade: ");
		int idade = console.nextInt();
		
		System.out.print("Informe sua pretenção salarial: ");
		double pretSalarial = console.nextDouble();
		
		System.out.println(nome + 
			" com a idade de " + idade +
			" sua preteção salarial de R$ " +
			pretSalarial + " é alta");
		
		console.close();
	}
}
