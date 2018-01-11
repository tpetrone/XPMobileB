package br.senai.sp.informatica.mobileb.exemplos;

import javax.swing.JOptionPane;

public class ExemploWhile2 {
	public static void main(String[] args) {
		String nome = JOptionPane.showInputDialog("Informe o nome");
		
		while(!nome.isEmpty()) { // !nome.equals("")
			String novoNome = "Bem vindo, " + nome.toUpperCase();
			JOptionPane.showMessageDialog(null, novoNome);
			
			nome = JOptionPane.showInputDialog("Informe o nome");
		}
	}
}
