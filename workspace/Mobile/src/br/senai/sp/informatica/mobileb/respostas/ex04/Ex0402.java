package br.senai.sp.informatica.mobileb.respostas.ex04;

import br.senai.sp.informatica.mobileb.lib.Util;

public class Ex0402 {
	public static void main(String[] args) {
		int a = Util.leInteiro("Informe o tamanho do lado A");
		int b = Util.leInteiro("Informe o tamanho do lado B");
		int c = Util.leInteiro("Informe o tamanho do lado C");
		
		boolean bcMenorQa = Math.abs(b - c) < a;
		boolean acMenorQb = Math.abs(a - c) < b;
		boolean abMenorQc = Math.abs(a - b) < c;
		
		if(bcMenorQa && a < b + c &&
	       acMenorQb && b < a + c &&
	       abMenorQc && c < a + b) {
			String tipoDeTriangulo = "";
			if(a == b && a == c) {
				tipoDeTriangulo = "Eqüilátero";
			} else if(a != b && a != c && b != c) {
				tipoDeTriangulo = "Escaleno";
			} else if(a == b || a == c || b == c) {
				tipoDeTriangulo = "Isósceles";
			}
			
			Util.escreva(tipoDeTriangulo);
		} else {
			Util.escreva("Estas retas não formam um Triângulo");
		}
	}
}
