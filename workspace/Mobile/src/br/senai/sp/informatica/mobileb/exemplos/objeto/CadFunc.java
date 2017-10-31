package br.senai.sp.informatica.mobileb.exemplos.objeto;

import java.util.ArrayList;
import java.util.List;

import br.senai.sp.informatica.mobileb.lib.Util;

public class CadFunc {
	public static void main(String[] args) {
		List<Funcionario> lista = new ArrayList<>();
		
		String nome = Util.leTexto("Informe o Nome");	
		while(!nome.equals("fim")) {
			double salario = Util.leReal("Informe o Salário");
			int matricula = Util.leInteiro("Informe a Matrícula");
			
			Funcionario func = new Funcionario();
			func.setNome(nome);
			func.setMatricula(matricula);
			func.setSalario(salario);
			
			lista.add(func);
			
			nome = Util.leTexto("Informe o Nome");	
		}
		
		String texto = "";
		for (Funcionario func : lista) {
			texto += func + "\n";
		}
		Util.escreva(texto);
	}
}
