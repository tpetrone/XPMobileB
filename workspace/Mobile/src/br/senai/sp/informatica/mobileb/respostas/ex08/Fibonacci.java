package br.senai.sp.informatica.mobileb.respostas.ex08;

import static br.senai.sp.informatica.mobileb.lib.Util.*;

public class Fibonacci {
	public static void main(String[] args) {
		int semanas = leInteiro("Informe o nº de casais");
		escreva("O Nº de casais será de: ", fib(semanas));
	}

	static int fib(int sem) {
		int total = 1;

		if (sem > 2)
			total = fib(sem - 2) + fib(sem - 1);

		return total;
	}
}
