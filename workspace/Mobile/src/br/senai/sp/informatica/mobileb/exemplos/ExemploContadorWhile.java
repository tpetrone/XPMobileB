package br.senai.sp.informatica.mobileb.exemplos;

public class ExemploContadorWhile {
	public static void main(String[] args) {
		int i = 0;
		while (i < 8) {
			System.out.println(i);
			i = i + 2;
		}
		System.out.println("- - - - - - - -");
		int j = 8;
		while (j >= 0) {
			System.out.println(j);
			j = j - 2;
		}
	}
}
