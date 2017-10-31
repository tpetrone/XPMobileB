package br.senai.sp.informatica.mobileb.respostas.ex04;

import javax.swing.JOptionPane;

import br.senai.sp.informatica.mobileb.lib.Util;

public class Ex0404 {
	public static void main(String[] args) {
		String nome = JOptionPane.showInputDialog("Informe o seu Nome");
		double media = Util.leReal("Informe a 1ª nota");
		media = media + Util.leReal("Informe a 2ª nota");
		media += Util.leReal("Informe a 3ª nota");
		media /= 3;
		int faltas = Util.leInteiro("Informe o nº de faltas");
		
		String situacao = "";
		if(faltas > 15) {
			situacao = "Reprovado por faltas";
		} else if(media < 7) {
			situacao = "Reprovado por média";
		} else {
			situacao = "Aprovado";
		}
		
		Util.escreva(nome, ", você foi: ", situacao);
	}
}
