package br.senai.sp.informatica.mobileb.exemplos;

import javax.swing.JOptionPane;

public class TesteTexto {
	public static void main(String[] args) {
		String nome1 = JOptionPane.showInputDialog("Informe o 1º nome");
		String nome2 = JOptionPane.showInputDialog("Informe o 1º nome");
		
		if(!nome1.equals(nome2)) { // São difentes ?
			JOptionPane.showMessageDialog(null, "São diferentes");
		} else {
			JOptionPane.showMessageDialog(null, "São iguais");
		}
	}
}
