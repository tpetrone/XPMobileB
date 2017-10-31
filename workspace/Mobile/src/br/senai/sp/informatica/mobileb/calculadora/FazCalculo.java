package br.senai.sp.informatica.mobileb.calculadora;

import javax.swing.JOptionPane;

public class FazCalculo {
	public static void main(String[] args) {
		try {
			Calculadora calc = new Calculadora();

			calc.entra(Double.parseDouble(JOptionPane.showInputDialog("Informe o 1º nº")));
			calc.setOperacao(Calculadora.Operacao.SOMA);
			calc.entra(Double.parseDouble(JOptionPane.showInputDialog("Informe o 2º nº")));
			JOptionPane.showMessageDialog(null, calc.resultado());

			calc.entra(10);
			calc.setOperacao(Calculadora.Operacao.SUBTRACAO);
			calc.entra(Double.parseDouble(JOptionPane.showInputDialog("Informe o próximo nº")));
			JOptionPane.showMessageDialog(null, calc.resultado());
		} catch (CalculadoraException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
}

