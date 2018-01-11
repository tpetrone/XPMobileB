package br.senai.sp.informatica.mobileb.exemplos;

import br.senai.sp.informatica.mobileb.lib.Util;

public class ExemploSwitch2 {
	public static void main(String[] args) {
		int mes = Util.leInteiro("Informe o mês");

		String estacao;
		
		switch (mes) {
		case 12: case 1: case 2:
			estacao = "Verão";
			break;
		case 3: case 4: case 5:
			estacao = "Outono";
			break;
		case 6: case 7: case 8:
			estacao = "Inverno";
			break;
		case 9: case 10: case 11:
			estacao = "Primavera";
			break;
		default:
			estacao = "Inválido";
			break;
		}

		Util.escreva("Estação do Ano: ", estacao);
	}
}
