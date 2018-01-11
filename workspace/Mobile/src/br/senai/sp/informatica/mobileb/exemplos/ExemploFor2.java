package br.senai.sp.informatica.mobileb.exemplos;

public class ExemploFor2 {
	public static void main(String[] args) {
		for (int i = 0; i < 8; i++) {
			if(i == 4) {
				continue;
			}
			System.out.println(i);
		}
	}
}
