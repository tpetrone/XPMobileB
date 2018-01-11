package br.senai.sp.informatica.mobileb.respostas.ex08;

import static br.senai.sp.informatica.mobileb.lib.Util.*;

public class Hanoi {
	public static void main(String[] args) {
		int discos = leInteiro("Informe o nº de discos");
		escreva("Nº movimentos será de: ", hanoi(discos));
	}

	static int hanoi(int discos) {
		int movimentos = 1;

		if (discos > 1)
			movimentos = 2 * hanoi(discos - 1) + 1;

		return movimentos;
	}
}
