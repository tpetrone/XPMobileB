package br.senai.sp.informatica.mobileb.respostas.ex08.respostas;

import static br.senai.sp.informatica.mobileb.lib.Util.*; 

public class Ex03 {
	public static void main(String[] args) {
		int conta = leInteiro("Informe o nº da Conta");
		escreva("O dígito verificador para a conta ", conta, 
				" é ", digito(conta));
	}
	
	static int digito(int num) {
		int soma = num + Ex02.inverso(num);
		
		int dig = 0;

		for (int i = 6; i > 0; i--) {
			dig = dig + i * soma % 10;
			soma = soma / 10;
		}
		
		return dig % 10;
	}
}

//{
//	soma => 113330
//	soma % 10 => 0
//	i => 6
//	dig => 0
//	dig = 0 + 6 * 0
//	
//	soma => 11333
//	i => 5
//	soma % 10 => 3
//	dig => 0
//	dig = 0 + 5 * 3 => 15
//	
//	
//	soma => 1133
//	i => 4
//	soma % 10 => 3
//	dig => 15
//	dig = 15 + 4 * 3
//
//}



