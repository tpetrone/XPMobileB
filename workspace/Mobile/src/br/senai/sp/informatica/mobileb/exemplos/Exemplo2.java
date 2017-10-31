package br.senai.sp.informatica.mobileb.exemplos;

import javax.swing.JOptionPane;

public class Exemplo2 {
	public static void main(String[] args) {
		
		String nome = JOptionPane.showInputDialog("Informe seu nome");
		
		String temp = JOptionPane.showInputDialog("Informe sua idade");
		int idade = Integer.parseInt(temp);
		
		temp = JOptionPane.showInputDialog("Informe sua pretenção salarial: ");
		double pretSalarial = Double.parseDouble(temp);
		
		JOptionPane.showMessageDialog(null, nome + 
			" com a idade de " + idade +
			" sua preteção salarial de R$ " +
			pretSalarial + " é alta");
		
	}
}
