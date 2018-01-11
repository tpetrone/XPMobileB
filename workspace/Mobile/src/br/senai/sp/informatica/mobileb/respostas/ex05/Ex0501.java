package br.senai.sp.informatica.mobileb.respostas.ex05;

import javax.swing.JOptionPane;

public class Ex0501 {
	public static void main(String[] args) {
		String texto = "";
		int col = 1;
		for (int i = 2; i <= 1000; i+=2) {
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
