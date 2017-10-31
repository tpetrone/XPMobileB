package br.senai.sp.informatica.mobileb.exemplos;

import javax.swing.JOptionPane;

public class TesteTexto2 {
	/*
	Operadores lógicos
	
	ou  ||
	e   &&
	não !
	
	*/
	public static void main(String[] args) {
		String resposta = 
				JOptionPane.showInputDialog("Você quer continuar?");
		
//		if(resposta.equals("S") || resposta.equals("s")) {
//		if(resposta.equalsIgnoreCase("s")) {
		if(resposta.toLowerCase().equals("s")) {
			JOptionPane.showMessageDialog(null, "ok");
		} else {
			JOptionPane.showMessageDialog(null, "tchau!");
		}
	}
}
