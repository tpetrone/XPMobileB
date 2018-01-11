package br.senai.sp.informatica.mobileb.exemplos;

import br.senai.sp.informatica.mobileb.lib.Util;

public class ExemploSwitch {
	public static void main(String[] args) {
		int codigo = Util.leInteiro("Informe o código");
		String msg = "";
		
		switch (codigo) {
		case 1:
			msg = "Vá para os quinto";
			break;
		case 3:
			msg = "Vá para o nono";
			break;
		case 2:
			msg = "Passe na lanchonete\n";
		case 4:
			msg += "depois vá para o quarto";
			break;
		default:
			msg = "Vá para o 1º";
		}
		Util.escreva(msg);
	}
}
