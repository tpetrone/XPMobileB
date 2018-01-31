package br.senai.sp.informatica.mobileb.exemplos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Teste {
	public static void main(String[] args) {
//		String nome = JOptionPane.showInputDialog("Informe o seu Nome");
//		String temp = JOptionPane.showInputDialog("Informe o seu Salário");
//		double salario = Double.parseDouble(temp);
//		salario = salario * 1.05;
//		JOptionPane.showMessageDialog(null, nome + 
//			" seu novo salário é de R$ " + salario);
	 
	 
	 List<Xpto> lista = new ArrayList<Xpto>(Arrays.asList( new Xpto[] {
		 new Xpto("fulano", "Rua A, 33"),
		 new Xpto("beltrano", "Rua B, 44")
	 }));
	 
	 lista.add(new Xpto("ciclano", "Rua C, 55"));
	 
	 lista.stream()
	 	.map(t -> t.toString())
	 	.forEach(System.out::println);

	}
}

class Xpto {
	private String nome;
	private String endereco;

	public Xpto(String nome, String endereco) {
		super();
		this.nome = nome;
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return "nome: " + nome + " endereco: " + endereco;
	}

}