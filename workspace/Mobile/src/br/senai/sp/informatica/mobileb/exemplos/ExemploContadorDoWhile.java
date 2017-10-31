package br.senai.sp.informatica.mobileb.exemplos;

public class ExemploContadorDoWhile {
	public static void main(String[] args) {
		int i = 0;
		do {
			System.out.println(i);
			i = i + 2;
		} while (i < 8);
		System.out.println("- - - - - - - -");
		int j = 8;
		do {
			System.out.println(j);
			j = j - 2;
		} while (j >= 0);
	}
}
