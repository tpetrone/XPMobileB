package br.senai.sp.informatica.mobileb.exemplos;

public class ExemploContadorFor {
	public static void main(String[] args) {
		for (int i = 0; i < 8; i = i + 2) {
			System.out.println(i);
		}
		System.out.println("- - - - - - - -");
		for (int i = 8; i >= 0; i = i - 2) {
			System.out.println(i);
		}
	}
}
