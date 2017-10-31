package br.senai.sp.informatica.mobileb.exemplos;

import java.util.Locale;

import javax.swing.JOptionPane;

public class ExemploFormatacao {
	public static void main(String[] args) {
		int idade = 25;
		double salario = 2580.52;
		String cargo = "Atendente Comercial";
		String nome = "José Antonio da Silva";
		
		String texto = String.format(new Locale("pt", "BR"),
				  "Nome: %s"
				+ "\nCargo: %s"
				+ "\nIdade: %d"
				+ "\nSalário: R$ %,.2f", 
				nome, cargo, idade, salario);
		
		JOptionPane.showMessageDialog(null, texto);
	}
}
