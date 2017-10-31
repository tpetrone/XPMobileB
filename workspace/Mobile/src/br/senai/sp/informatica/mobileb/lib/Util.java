package br.senai.sp.informatica.mobileb.lib;

import javax.swing.JOptionPane;

public class Util {
	public static String leTexto(Object ... msg) {
		return JOptionPane.showInputDialog(formataTexto(msg));
	}
	
	public static int leInteiro(Object ... msg) {
		String temp = JOptionPane.showInputDialog(formataTexto(msg));
		
		int num = Integer.parseInt(temp);
		return num;
	}

	public static double leReal(Object ... msg) {
		String temp = JOptionPane.showInputDialog(formataTexto(msg));

		return Double.parseDouble(temp);
	}
	
	public static void escreva(Object ... msg) {
		JOptionPane.showMessageDialog(null, formataTexto(msg));
	}

	private static String formataTexto(Object ... texto) {
		String fmt = "";
		
		for (int i = 0; i < texto.length; i++) {
			Object obj = texto[i];
			
			if(obj instanceof String) {
				fmt += "%s";
			} else if(obj instanceof Integer) {
				fmt += "%d";
			} else if(obj instanceof Double) {
				fmt += "%,.2f";
			}
		}
		
		return String.format(fmt, texto);
	}
}
