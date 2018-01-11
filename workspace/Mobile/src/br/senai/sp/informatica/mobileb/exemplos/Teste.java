package br.senai.sp.informatica.mobileb.exemplos;

import javax.swing.JOptionPane;

public class Teste {
 public static void main(String[] args) {
		String nome = JOptionPane.showInputDialog("Informe o seu Nome");
		String temp = JOptionPane.showInputDialog("Informe o seu Salário");
		double salario = Double.parseDouble(temp);
		salario = salario * 1.05;
		JOptionPane.showMessageDialog(null, nome + 
			" seu novo salário é de R$ " + salario);
  }
}