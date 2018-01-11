package br.senai.sp.informatica.mobileb.respostas.ex05;

import javax.swing.JOptionPane;

import br.senai.sp.informatica.mobileb.lib.Util;

public class Ex0502 {
	public static void main(String[] args) {
		int num1 = Util.leInteiro("Informe o 1º nº");
		int num2 = Util.leInteiro("Informe o último nº");
		
		if(num1 > num2) {
			int temp = num1;
			num1 = num2;
			num2 = temp;
		}
		
		if(num1 % 2 == 0) {
			num1++;
		}
		
		String texto = "";
		int col = 1;
		for (int i = num1; i <= num2; i+=2) {
			if(col >= 22) { 
				texto += String.format("%04d\n", i);
				col = 1;
			} else {
				texto += String.format("%04d ", i);
				col++;
			}
		}
		JOptionPane.showMessageDialog(null, texto);
	}
}
