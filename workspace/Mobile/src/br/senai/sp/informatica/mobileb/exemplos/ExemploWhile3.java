package br.senai.sp.informatica.mobileb.exemplos;

import javax.swing.JOptionPane;

public class ExemploWhile3 {
	public static void main(String[] args) {
		String nome = JOptionPane.showInputDialog("Informe o nome");
		
		while(!nome.equals("fim")) {  
			String novoNome = "Bem vindo, " + nome.toUpperCase();
			JOptionPane.showMessageDialog(null, novoNome);
			
			nome = JOptionPane.showInputDialog("Informe o nome");
		}
	}
}
