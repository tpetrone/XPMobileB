package br.senai.sp.informatica.mobileb.exemplos;

import javax.swing.JOptionPane;

public class ExemploDoWhile4 {
	public static void main(String[] args) {
		String nome = "";
		do {
			nome = JOptionPane.showInputDialog("Informe o nome");
			if (nome.equals("fim")) {
				break;
			} else {
				String novoNome = "Bem vindo, " + nome.toUpperCase();
				JOptionPane.showMessageDialog(null, novoNome);
			}

		} while (!nome.equals("fim"));
	}
}
